package com.example.logistics.service.impl;

import com.example.logistics.model.entity.ClientRate;
import com.example.logistics.repository.BaseRepository;
import com.example.logistics.repository.ClientRateRepository;
import com.example.logistics.service.ClientRateService;
import com.example.logistics.service.base.AbstractCrudService;
import org.springframework.stereotype.Service;

@Service("clientRateService")
public class ClientRateServiceImpl extends AbstractCrudService<ClientRate, Long> implements ClientRateService {

    private final ClientRateRepository clientRateRepository;

    protected ClientRateServiceImpl(ClientRateRepository repository) {
        super(repository);
        this.clientRateRepository = repository;
    }
}
