package com.ridam.GrabACycle.repository;

import com.ridam.GrabACycle.model.Cycle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;


public interface CycleRepo extends JpaRepository<Cycle,Long> {
    public List<Cycle> findByCycleName(String name);
}
