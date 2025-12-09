package com.devsuperior.dsmeta.services;

import com.devsuperior.dsmeta.dto.SellerMinDTO;
import com.devsuperior.dsmeta.entities.Seller;
import com.devsuperior.dsmeta.repositories.SellerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class SellerService {

    @Autowired
    private SellerRepository repository;

    public SellerMinDTO findById(Long id){
        Optional<Seller> result = repository.findById(id);
        Seller entity = result.get();
        return new SellerMinDTO(entity);
    }
}
