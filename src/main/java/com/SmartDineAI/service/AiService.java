package com.SmartDineAI.service;

import com.SmartDineAI.dto.ai.AiRequest;
import com.SmartDineAI.entity.DiningTable;
import com.SmartDineAI.repository.DiningTableRepository;
import com.SmartDineAI.repository.ReservationRepository;
import com.SmartDineAI.repository.RestaurantRepository;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;

import java.time.LocalDateTime;
import java.util.*;
import java.util.regex.*;

@Service
@RequiredArgsConstructor
public class AiService {

    private final DiningTableRepository diningTableRepository;
    private final ReservationRepository reservationRepository;
    private final RestaurantRepository restaurantRepository;

    @Value("${groq.apiKey}")
    private String API_KEY;



    public String chat(AiRequest request) {

        try {

            String message = request.getMessage();

            Integer guestCount = extractGuestCount(message);

            String restaurantName = extractRestaurantName(message);

            LocalDateTime startTime = LocalDateTime.now();
            LocalDateTime endTime = startTime.plusHours(2);

            List<DiningTable> tables =
                    diningTableRepository.findAvailableTablesForGuests(
                            guestCount,
                            startTime,
                            endTime
                    );


            /*
             chọn bàn tối ưu cho mỗi nhà hàng
             capacity >= guestCount
             và nhỏ nhất có thể
            */

            Map<String, DiningTable> bestTables = new HashMap<>();

            for (DiningTable table : tables) {

                String restaurant = table.getRestaurant().getName();

                DiningTable current = bestTables.get(restaurant);

                if (current == null ||
                        table.getCapacity() < current.getCapacity()) {

                    bestTables.put(restaurant, table);
                }
            }


            /*
             convert thành text gửi cho AI
            */

            List<String> restaurantInfo = new ArrayList<>();

            for (Map.Entry<String, DiningTable> entry : bestTables.entrySet()) {

                DiningTable table = entry.getValue();

                restaurantInfo.add(
                        entry.getKey()
                        + " - bàn phù hợp: "
                        + table.getTableCode()
                        + " ("
                        + table.getCapacity()
                        + " chỗ)"
                );
            }


            List<String> history = new ArrayList<>();

            if (request.getUserId() != null) {

                history =
                        reservationRepository
                                .findRestaurantHistory(request.getUserId());
            }


            String prompt = buildPrompt(
                    message,
                    guestCount,
                    restaurantName,
                    restaurantInfo,
                    history
            );


            RestTemplate restTemplate = new RestTemplate();

            HttpHeaders headers = new HttpHeaders();
            headers.setBearerAuth(API_KEY);
            headers.setContentType(MediaType.APPLICATION_JSON);

            Map<String, Object> body = new HashMap<>();

            body.put("model", "llama-3.1-8b-instant");

            List<Map<String, String>> messages = new ArrayList<>();

            messages.add(
                    Map.of(
                            "role", "user",
                            "content", prompt
                    )
            );

            body.put("messages", messages);

            HttpEntity<Map<String, Object>> entity =
                    new HttpEntity<>(body, headers);


            ResponseEntity<Map> response =
                    restTemplate.postForEntity(
                            "https://api.groq.com/openai/v1/chat/completions",
                            entity,
                            Map.class
                    );


            Map choice =
                    (Map) ((List) response.getBody().get("choices")).get(0);

            Map messageObj =
                    (Map) choice.get("message");

            return messageObj.get("content").toString();

        }
        catch (Exception e) {

            e.printStackTrace();

            return "AI error: " + e.getMessage();
        }

    }



    /*
     extract guest count
    */

    private Integer extractGuestCount(String message) {

        Pattern pattern = Pattern.compile("(\\d+)");

        Matcher matcher = pattern.matcher(message);

        if (matcher.find()) {
            return Integer.parseInt(matcher.group(1));
        }

        return 2;
    }



    /*
     detect restaurant name from message
    */

    private String extractRestaurantName(String message) {

        List<String> restaurants =
                restaurantRepository.findAll()
                        .stream()
                        .map(r -> r.getName())
                        .toList();

        message = message.toLowerCase();

        for (String r : restaurants) {

            if (message.contains(r.toLowerCase())) {
                return r;
            }
        }

        return null;
    }



    private String buildPrompt(
            String userMessage,
            Integer guestCount,
            String restaurantName,
            List<String> restaurantInfo,
            List<String> history
    ){

        return """
Bạn là trợ lý AI cho hệ thống đặt bàn nhà hàng.

Người dùng có thể nói tiếng Việt hoặc tiếng Anh.

Nhiệm vụ của bạn:
- Hiểu yêu cầu của người dùng
- Gợi ý nhà hàng phù hợp
- Chỉ sử dụng nhà hàng từ danh sách cung cấp
- Không tạo nhà hàng mới
- Không thay đổi mã bàn

Tin nhắn người dùng:
%s

Số khách:
%d

Nhà hàng người dùng nhắc tới:
%s

Danh sách nhà hàng và bàn phù hợp:
%s

Lịch sử đặt bàn của người dùng:
%s

Quy tắc:

- Gợi ý tối đa 3 nhà hàng
- Nếu người dùng nhắc tới nhà hàng nào → ưu tiên
- Trả lời bằng tiếng Việt
- Viết ngắn gọn

Định dạng:

1. Tên nhà hàng - lý do. Bàn phù hợp: MÃ_BÀN
2. Tên nhà hàng - lý do. Bàn phù hợp: MÃ_BÀN
3. Tên nhà hàng - lý do. Bàn phù hợp: MÃ_BÀN
""".formatted(
                userMessage,
                guestCount,
                restaurantName == null ? "không có" : restaurantName,
                restaurantInfo,
                history
        );
    }

}