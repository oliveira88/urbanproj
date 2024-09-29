package com.ufes.urbanapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ufes.urbanapi.model.Status;

@Repository
public interface StatusRepository extends JpaRepository<Status, Long> {
}
