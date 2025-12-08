package com.devsuperior.dsmeta.controllers;

import com.devsuperior.dsmeta.dto.SaleSummaryDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.devsuperior.dsmeta.dto.SaleMinDTO;
import com.devsuperior.dsmeta.services.SaleService;

import java.util.List;

@RestController
@RequestMapping(value = "/sales")
public class SaleController {

	@Autowired
	private SaleService service;
	
	@GetMapping(value = "/{id}")
	public ResponseEntity<SaleMinDTO> findById(@PathVariable Long id) {
		SaleMinDTO dto = service.findById(id);
		return ResponseEntity.ok(dto);
	}

	@GetMapping(value = "/report")
	public ResponseEntity<Page<SaleMinDTO>> getReport(
			@RequestParam(value = "maxDate", required = false) String dateStart,
			@RequestParam(value = "minDate", required = false) String dateEnd,
			@RequestParam(value = "name", required = false) String name,
			Pageable pageable
	) {
		Page<SaleMinDTO> page = service.searchDateSeller(dateStart, dateEnd, name, pageable);
		return ResponseEntity.ok(page);
	}



	@GetMapping(value = "/summary")
	public ResponseEntity<List<SaleSummaryDTO>> getSalesSummary(
			@RequestParam(value = "minDate", required = false) String minDateStr,
			@RequestParam(value = "maxDate", required = false) String maxDateStr
	) {
		List<SaleSummaryDTO> list = service.getSalesSummary(minDateStr, maxDateStr);
		return ResponseEntity.ok(list);
	}
}
