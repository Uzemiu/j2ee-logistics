package com.example.logistics.service;

import com.example.logistics.model.dto.ClientRateDTO;
import com.example.logistics.model.entity.ClientRate;
import com.example.logistics.model.entity.Order;
import com.example.logistics.model.param.ClientRateParam;
import com.example.logistics.service.base.AbstractCrudService;
import com.example.logistics.service.base.CrudService;

import java.util.Optional;

public interface ClientRateService extends CrudService<ClientRate, Long> {

    ClientRate rateOrder(ClientRateParam rate);

    Optional<ClientRate> getByOrder(Order order);

    ClientRateDTO toDto(ClientRate clientRate);
}
