package com.example.marketplace.service.interfaces;

import com.example.marketplace.dto.request.ReviewRequestDTO;
import com.example.marketplace.dto.request.ReviewUpdateDTO;
import com.example.marketplace.dto.response.ReviewResponseDTO;

public interface ReviewServiceInterface extends GeneralInterface<ReviewRequestDTO, ReviewResponseDTO>{
    ReviewResponseDTO updateReview(ReviewUpdateDTO update, Integer reviewId);
}
