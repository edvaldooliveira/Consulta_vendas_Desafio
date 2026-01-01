package com.devsuperior.dsmeta.services;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;
import java.util.Optional;

import com.devsuperior.dsmeta.dto.SaleSummaryDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.devsuperior.dsmeta.dto.SaleMinDTO;
import com.devsuperior.dsmeta.entities.Sale;
import com.devsuperior.dsmeta.repositories.SaleRepository;


@Service
public class SaleService {

	@Autowired
	private SaleRepository repository;

	public SaleMinDTO findById(Long id) {
		Optional<Sale> result = repository.findById(id);
		Sale entity = result.orElseThrow(() -> new RuntimeException("Sale not found"));
		return new SaleMinDTO(entity);
	}

	public Page<SaleMinDTO> findAll(Pageable pageable) {
		Page<Sale> result = repository.findAll(pageable);
		return result.map(SaleMinDTO::new);
	}

	public Page<SaleMinDTO> searchDateSeller(
			String dateStartStr,
			String dateEndStr,
			String name,
			Pageable pageable
	) {

		LocalDate dateEnd = (dateEndStr == null || dateEndStr.isBlank())
				? LocalDate.now()
				: LocalDate.parse(dateEndStr);

		LocalDate dateStart = (dateStartStr == null || dateStartStr.isBlank())
				? dateEnd.minusYears(1)
				: LocalDate.parse(dateStartStr);

		// 🔥 MUITO IMPORTANTE: incluir o último dia
		dateEnd = dateEnd.plusDays(1);

		String nameFilter = (name == null) ? "" : name;

		return repository.searchByDateAndSeller(
				dateStart,
				dateEnd,
				nameFilter,
				pageable
		);
	}

	public List<SaleSummaryDTO> getSalesSummary(
			String minDateStr,
			String maxDateStr
	) {

		LocalDate maxDate = (maxDateStr == null || maxDateStr.isBlank())
				? LocalDate.now()
				: LocalDate.parse(maxDateStr);

		LocalDate minDate = (minDateStr == null || minDateStr.isBlank())
				? maxDate.minusYears(1)
				: LocalDate.parse(minDateStr);

		// 🔥 MUITO IMPORTANTE: incluir o último dia
		maxDate = maxDate.plusDays(1);

		return repository.getSalesSummary(minDate, maxDate);
	}
}
