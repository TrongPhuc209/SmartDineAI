package com.SmartDineAI.controller.Admin;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.SmartDineAI.dto.auth.ApiResponse;
import com.SmartDineAI.dto.diningTable.CreateDiningTableRequest;
import com.SmartDineAI.dto.diningTable.DiningTableResponse;
import com.SmartDineAI.dto.diningTable.UpdateDiningTableRequest;
import com.SmartDineAI.service.DiningTableService;

import jakarta.validation.Valid;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/admin/dining-tables")
public class DiningTableController {
    @Autowired
    private DiningTableService diningTableService;

    // @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ApiResponse<DiningTableResponse> createDiningTable(@RequestBody @Valid CreateDiningTableRequest request){
        ApiResponse<DiningTableResponse> response = new ApiResponse<>();
        response.setResult(diningTableService.createDiningTable(request));
        return response;
    }

    // @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/{id}")
    public ApiResponse<DiningTableResponse> getDiningTableById(@PathVariable Long id){
        ApiResponse<DiningTableResponse> response = new ApiResponse<>();
        response.setResult(diningTableService.getDiningTableById(id));
        return response;
    }

    // @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/get-all")
    public ApiResponse<Page<DiningTableResponse>> getAllDiningTable(Pageable pageable){
        Page<DiningTableResponse> result = diningTableService.getAllDiningTable(pageable);
        return ApiResponse.<Page<DiningTableResponse>>builder()
                .result(result)
                .build();
    }

    // @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ApiResponse<DiningTableResponse> updateDiningTable(@PathVariable Long id, @RequestBody @Valid UpdateDiningTableRequest request){
        return ApiResponse.<DiningTableResponse>builder()
                .result(diningTableService.updateDiningTable(id, request))
                .build();
    }

    // @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/active/{id}")
    public void updateActive(@PathVariable Long id){
        diningTableService.updateActive(id);
    }

    // @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ApiResponse<String> deleteDiningTable(@PathVariable Long id){
        ApiResponse<String> response = new ApiResponse<>();
        diningTableService.deleteDiningTable(id);
        response.setMessage("Dining table deleted successfully");
        return response;
    }

    // @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    public ApiResponse<Page<DiningTableResponse>> searchDiningTable(
            @RequestParam(required = false) String tableCode,
            @RequestParam(required = false) Integer capacity,
            @RequestParam(required = false) Boolean active,
            @RequestParam(required = false) String location,
            @RequestParam(required = false) Long restaurantId,
            Pageable pageable) {

        ApiResponse<Page<DiningTableResponse>> response = new ApiResponse<>();

        response.setResult(
                diningTableService.searchDiningTable(
                        tableCode,
                        capacity,
                        active,
                        location,
                        restaurantId,
                        pageable
                )
        );

        return response;
    }
}
