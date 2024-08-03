package com.example.samokatclient.services;

import com.example.samokatclient.DTO.currentOrder.NewStatusDto;

public interface StatusService {

    public void newStatusHandler(NewStatusDto newStatusDto);

}
