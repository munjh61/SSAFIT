package com.ssafy.ssafit.model.dao;

import com.ssafy.ssafit.model.dto.Review;

import java.util.List;

public interface ReviewDao {
    int insert(Review review);
    Review reviewDetail(int reviewId);
    List<Review> reviewList(int boardId);
    int update(Review review);
    int delete(int reviewId);
}
