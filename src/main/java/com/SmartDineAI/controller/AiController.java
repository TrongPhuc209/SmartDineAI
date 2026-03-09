package com.SmartDineAI.controller;

import com.SmartDineAI.dto.ai.AiRequest;
import com.SmartDineAI.service.AiService;

import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/ai")
@RequiredArgsConstructor
@CrossOrigin
public class AiController {

    private final AiService aiService;

    @PostMapping("/chat")
    public ResponseEntity<String> chat(@RequestBody AiRequest request){

        String response = aiService.chat(request);

        return ResponseEntity.ok(response);

    }

}