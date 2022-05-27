package com.paevolution.appiogateway.core.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.paevolution.appiogateway.core.domain.Problem;

@Repository
public interface ProblemRepository extends CrudRepository<Problem, Long> {

    List<Problem> findAll();

    Optional<Problem> findById(Long id);

    Optional<Problem> findByMessaggi(Long id);
}
