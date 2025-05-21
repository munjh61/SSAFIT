package com.ssafy.ssafit.model.service;

import com.ssafy.ssafit.model.dao.ReviewDao;
import com.ssafy.ssafit.model.dto.Review;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReviewServiceImpl implements ReviewService {

    private final ReviewDao reviewDao;

    @Transactional
    @Override
    public int insert(Review review) {
        return reviewDao.insert(review);
    }

    @Override
    public List<Review> reviewList(int boardId) {
        return reviewDao.reviewList(boardId);
    }

    @Transactional
    @Override
    public String update(Review review) {
        Review tmp = reviewDao.reviewDetail(review.getReviewId());
        if (tmp == null)
            return "잘못된 접근입니다.";
        if (!tmp.getUserId().equals(review.getUserId()))
            return "로그인 정보와 작성자가 다릅니다.";
        if (reviewDao.update(review) > 0)
            return "수정되었습니다.";
        return "수정에 실패하였습니다.";

    }

    @Transactional
    @Override
    public String delete(int reviewId, String userId) {
        Review tmp = reviewDao.reviewDetail(reviewId);
        if (tmp == null)
            return "잘못된 접근입니다.";
        if (!tmp.getUserId().equals(userId))
            return "로그인 정보와 작성자가 다릅니다.";
        if (reviewDao.delete(reviewId) > 0)
            return "삭제되었습니다.";
        return "삭제에 실패하였습니다.";
    }
}
