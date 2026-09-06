package com.example.marketplace.service.implementations;

import com.example.marketplace.dto.request.ReviewRequestDTO;
import com.example.marketplace.dto.request.ReviewUpdateDTO;
import com.example.marketplace.dto.response.ReviewResponseDTO;
import com.example.marketplace.entity.Asking;
import com.example.marketplace.entity.Review;
import com.example.marketplace.exception.AskingServiceNotFoundException;
import com.example.marketplace.exception.ReviewNotFoundException;
import com.example.marketplace.mapper.request.ReviewRequestMapper;
import com.example.marketplace.mapper.response.ReviewResponseMapper;
import com.example.marketplace.repository.AskingRepository;
import com.example.marketplace.repository.ReviewRepository;
import com.example.marketplace.service.interfaces.ReviewServiceInterface;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
class ReviewService implements ReviewServiceInterface {

    private final ReviewRequestMapper requestMapper;

    private final ReviewResponseMapper responseMapper;

    private final ReviewRepository repository;

    private final Authentication auth;

    private final AskingRepository askingRepository;

    public ReviewService(ReviewRequestMapper requestMapper, ReviewResponseMapper responseMapper, ReviewRepository repository, AskingRepository askingRepository) {
        this.requestMapper = requestMapper;
        this.responseMapper = responseMapper;
        this.repository = repository;
        this.askingRepository = askingRepository;
        this.auth = SecurityContextHolder.getContext().getAuthentication();
    }

    @Override
    public ReviewResponseDTO save(ReviewRequestDTO dto) {
        Asking asking = askingRepository.findById(dto.getAskingserviceId()).orElseThrow(
                ()->new AskingServiceNotFoundException("AskingService not found with this id")
        );

        Review review = requestMapper.toEntity(dto);
        review.setAskingservice(asking);

        repository.save(review);

        return responseMapper.toDto(review);
    }

    @Override
    public List<ReviewResponseDTO> getAll() {
        throw new UnsupportedOperationException();
    }

    @Override
    public ReviewResponseDTO getById(int id) {
        Review review = repository.findById(id).orElseThrow(
                ()->new ReviewNotFoundException("Review not found for this id")
        );

        return responseMapper.toDto(review);
    }

    @Override
    public void deleteById(int id) {
        repository.findById(id).orElseThrow(
                ()->new ReviewNotFoundException("Review not found for this id")
        );

        repository.deleteById(id);
    }

    @Override
    public ReviewResponseDTO updateReview(ReviewUpdateDTO update, Integer reviewId) {
        Review review = repository.findById(reviewId).orElseThrow(
                ()->new ReviewNotFoundException("No Review found for this id")
        );

        requestMapper.partialUpdate(update,review);


        return responseMapper.toDto(repository.save(review));
    }
}
