package com.D210.soojoobback.repository;

import com.D210.soojoobback.entity.Record;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.List;

public interface RecordRepository extends JpaRepository<Record, Integer> {

    Record findById(Long id);

}
