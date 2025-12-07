package com.devsuperior.dsmeta.repositories;

import com.devsuperior.dsmeta.dto.SaleMinDTO;
import com.devsuperior.dsmeta.dto.SaleSummaryDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.devsuperior.dsmeta.entities.Sale;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface SaleRepository extends JpaRepository<Sale, Long> {

    @Query("SELECT new com.devsuperior.dsmeta.dto.SaleMinDTO(s.id, s.date, s.amount, s.seller.name) "
            + "FROM Sale s "
            + "WHERE s.date BETWEEN :startDate AND :endDate "
            + "AND LOWER(s.seller.name) LIKE LOWER(CONCAT('%', :name, '%')) "
            + "ORDER BY s.date DESC")
    Page<SaleMinDTO> searchByDateAndSeller(
            @Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate,
            @Param("name") String name,
            Pageable pageable
    );

    @Query("SELECT new com.devsuperior.dsmeta.dto.SaleSummaryDTO(s.seller.name, SUM(s.amount)) "
            + "FROM Sale s "
            + "WHERE s.date BETWEEN :minDate AND :maxDate "
            + "GROUP BY s.seller.name "
            + "ORDER BY SUM(s.amount) DESC")
    List<SaleSummaryDTO> getSalesSummary(
            @Param("minDate") LocalDate minDate,
            @Param("maxDate") LocalDate maxDate
    );


}
