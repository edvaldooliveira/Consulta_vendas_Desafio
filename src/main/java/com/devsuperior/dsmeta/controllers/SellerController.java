package com.devsuperior.dsmeta.controllers;

import com.devsuperior.dsmeta.dto.SellerMinDTO;
import com.devsuperior.dsmeta.services.SellerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/sellers")
public class SellerController {

    @Autowired
    private SellerService service;

    @GetMapping(value = "/{id}")
    public ResponseEntity<SellerMinDTO> findById(@PathVariable Long id) {
        SellerMinDTO dto = service.findById(id);
        return ResponseEntity.ok(dto);


    }


}
