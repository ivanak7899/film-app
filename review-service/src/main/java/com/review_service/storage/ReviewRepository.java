package com.review_service.storage;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Long> {
    List<Review> findByFilmId(Long filmId);

    List<Review> findByReviewerNameIgnoreCaseContaining(String reviewerName);
}
