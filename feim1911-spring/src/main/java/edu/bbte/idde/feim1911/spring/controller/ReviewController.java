package edu.bbte.idde.feim1911.spring.controller;

import edu.bbte.idde.feim1911.spring.controller.exception.NotFoundException;
import edu.bbte.idde.feim1911.spring.dao.HardwareComponentDao;
import edu.bbte.idde.feim1911.spring.dto.incoming.ReviewInDto;
import edu.bbte.idde.feim1911.spring.dto.outgoing.ReviewOutDto;
import edu.bbte.idde.feim1911.spring.mapper.ReviewMapper;
import edu.bbte.idde.feim1911.spring.model.HardwareComponent;
import edu.bbte.idde.feim1911.spring.model.Review;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Collection;
import java.util.List;

@Controller
@RequestMapping("/api/computerComponents")
@Slf4j
public class ReviewController {
    @Autowired
    HardwareComponentDao hardwareComponentDao;

    @Autowired
    ReviewMapper reviewMapper;

    @GetMapping("/{id}/reviews")
    @ResponseBody
    public Collection<ReviewOutDto> findAllReviews(@PathVariable Long id) {
        HardwareComponent hardwareComponent = hardwareComponentDao.getById(id);
        if (hardwareComponent == null) {
            throw new NotFoundException();
        }

        return reviewMapper.modelsToDtos(hardwareComponent.getReviews());
    }

    @PostMapping("/{id}/reviews")
    @ResponseStatus(HttpStatus.CREATED)
    @ResponseBody
    public ReviewOutDto createReviewForComponent(@PathVariable Long id, @RequestBody @Valid ReviewInDto entity) {
        HardwareComponent hardwareComponent = hardwareComponentDao.getById(id);
        if (hardwareComponent == null) {
            throw new NotFoundException();
        }

        Review newEntity = reviewMapper.dtoToModel(entity);
        newEntity.setComponent(hardwareComponent);

        hardwareComponent.getReviews().add(newEntity);
        List<Review> list = hardwareComponentDao.save(hardwareComponent).getReviews();

        return reviewMapper.modelToDto(list.get(list.size() - 1));
    }

    @DeleteMapping("/{id}/reviews/{reviewId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteReview(@PathVariable Long id, @PathVariable Long reviewId) {
        HardwareComponent hardwareComponent = hardwareComponentDao.getById(id);
        if (hardwareComponent == null) {
            throw new NotFoundException();
        }
        Review review = hardwareComponent.getReviews()
                .stream()
                .filter(rev -> rev.getId().equals(reviewId))
                .findFirst().orElse(null);
        if (review == null) {
            throw new NotFoundException();
        }

        hardwareComponent.getReviews().remove(review);
        hardwareComponentDao.save(hardwareComponent);
    }
}
