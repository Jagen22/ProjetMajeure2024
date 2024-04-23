package com.example.historyservice.service;

import com.example.historydto.CreateHistoryDTO;
import com.example.historydto.HistoryDTO;
import com.example.historyservice.model.History;
import com.example.historyservice.repository.HistoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class historyService {

	@Autowired
	HistoryRepository historyRepository;
	RestTemplate restTemplate;


	public void updateHistory(Integer historyId, HistoryDTO historyDTO) {
		History history = historyRepository.findById(historyId).get();
		String activities = history.getActivities();
		activities = activities + "|" + historyDTO.getText();
		history.setActivities(activities);
		historyRepository.save(history);

	}

	public String getHistory(Integer historyId) {
		History history = historyRepository.findById(historyId).get();
		return history.getDescription();
	}

	public Integer createHistory(CreateHistoryDTO createHistoryDTO) {
		History history = new History(createHistoryDTO.getDate(),createHistoryDTO.getText(),"");
		historyRepository.save(history);
		return history.getId();
	}
}
