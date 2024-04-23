package com.example.historyservice.repository;

import com.example.historyservice.model.History;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface HistoryRepository extends CrudRepository<History,Integer> {

	public Optional<History> findById(Integer id);


}
