package com.example.historyservice.rest;

import com.example.historydto.CreateHistoryDTO;
import com.example.historydto.HistoryDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.example.historyservice.service.historyService;

@RestController
public class historyController {

	@Autowired
	historyService historyService;

	@RequestMapping("/")
	public String sayHello() {
		return "Hello World";
	}
	//TODO changer le dto pour le bon
	@RequestMapping(method= RequestMethod.POST,value="/updateHistory/{historyId}")
	public String updateHistory(@PathVariable Integer historyId, @RequestBody HistoryDTO historyDTO) {
		historyService.updateHistory(historyId, historyDTO);
		return "OK";
	}

	@RequestMapping(method=RequestMethod.GET,value="/getHistory/{historyId}")
	public String getHistory(@PathVariable Integer historyId) {
		return historyService.getHistory(historyId);
	}

	@RequestMapping(method=RequestMethod.POST,value="/createHistory")
	public Integer createHistory(@RequestBody CreateHistoryDTO createHistoryDTO) {
		Integer idHistory = historyService.createHistory(createHistoryDTO);
		return idHistory;
	}

}
