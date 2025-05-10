package com.ssafy.ssafit.model.service;

import com.ssafy.ssafit.model.dto.Review;

import java.util.List;

public interface ReviewService {
    int insert(Review review);
    List<Review> reviewList(int boardId);
    String delete(int reviewId, String userId);
    String update(Review review);
}
