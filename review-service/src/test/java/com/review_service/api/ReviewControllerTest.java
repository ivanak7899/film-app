package com.review_service.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.review_service.ReviewService;
import com.review_service.storage.Review;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ReviewController.class)
class ReviewControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ReviewService reviewService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testPostReview() throws Exception {
        Review review = new Review(1L, "Alice", "Nice!", 9);
        Mockito.when(reviewService.createReview(any(Review.class))).thenReturn(review);

        mockMvc.perform(post("/reviews")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(review)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.reviewerName").value("Alice"))
                .andExpect(jsonPath("$.rating").value(9));
    }

    @Test
    void testGetAllReviews() throws Exception {
        Review r1 = new Review(1L, "Alice", "Nice!", 9);
        Review r2 = new Review(2L, "Bob", "Not bad", 7);

        Mockito.when(reviewService.getAllReviews()).thenReturn(List.of(r1, r2));

        mockMvc.perform(get("/reviews"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2));
    }

    @Test
    void testGetReviewById_found() throws Exception {
        Review review = new Review(1L, "Charlie", "Cool", 8);
        review.setId(42L);
        Mockito.when(reviewService.getById(42L)).thenReturn(Optional.of(review));

        mockMvc.perform(get("/reviews/42"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.reviewerName").value("Charlie"))
                .andExpect(jsonPath("$.rating").value(8));
    }

    @Test
    void testGetReviewById_notFound() throws Exception {
        Mockito.when(reviewService.getById(999L)).thenReturn(Optional.empty());

        mockMvc.perform(get("/reviews/999"))
                .andExpect(status().isNotFound());
    }
}
