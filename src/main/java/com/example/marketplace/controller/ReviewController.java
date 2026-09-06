package com.example.marketplace.controller;


import com.example.marketplace.assembler.ReviewAssembler;
import com.example.marketplace.dto.request.ReviewRequestDTO;
import com.example.marketplace.dto.request.ReviewUpdateDTO;
import com.example.marketplace.dto.response.AskingResponseDTO;
import com.example.marketplace.dto.response.ReviewResponseDTO;
import com.example.marketplace.service.interfaces.ReviewServiceInterface;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
class ReviewController {

    ReviewServiceInterface service;

    ReviewAssembler assembler;

    public ReviewController(ReviewServiceInterface service, ReviewAssembler assembler) {
        this.service = service;
        this.assembler = assembler;
    }



    @GetMapping("/reviews/{reviewId}")
    public ResponseEntity<EntityModel<ReviewResponseDTO>> getReviewById(@PathVariable Integer reviewId) {
        return ResponseEntity.ok(assembler.toModel(service.getById(reviewId)));
    }


    @Operation(
            security = {@SecurityRequirement(name = "bearerAuth")}
    )
    @PreAuthorize("hasRole('CLIENT')")
    @PostMapping("/reviews")
    public ResponseEntity<EntityModel<ReviewResponseDTO>> createReview(@RequestBody ReviewRequestDTO dto) {
        return ResponseEntity.ok(assembler.toModel(service.save(dto)));
    }


    @Operation(
            security = {@SecurityRequirement(name = "bearerAuth")}
    )
    @PreAuthorize("hasRole('CLIENT')")
    @DeleteMapping("/reviews/{reviewId}")
    public ResponseEntity<Void> deleteMyReview(@PathVariable Integer reviewId) {
        service.deleteById(reviewId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @Operation(
            security = {@SecurityRequirement(name = "bearerAuth")}
    )
    @PreAuthorize("hasRole('CLIENT')")
    @PutMapping("/reviews/{reviewId}")
    public ResponseEntity<EntityModel<ReviewResponseDTO>> updateReview(@RequestBody ReviewUpdateDTO update, @PathVariable Integer reviewId) {
        return ResponseEntity.ok(assembler.toModel(service.updateReview(update,reviewId)));
    }
}
