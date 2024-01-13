package com.devsuperior.bds02.services;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.devsuperior.bds02.dto.EventDTO;
import com.devsuperior.bds02.entities.Event;
import com.devsuperior.bds02.repository.CityRepository;
import com.devsuperior.bds02.repository.EventRepository;
import com.devsuperior.bds02.services.exceptions.ResourceIdCityNotFoundException;
import com.devsuperior.bds02.services.exceptions.ResourceNotFoundException;

@Service
public class EventService {

    @Autowired
    private EventRepository eventRepository;

    @Autowired
	private CityRepository cityRepository;

    @Transactional
    public EventDTO update(Long id, EventDTO updatedEventDTO) {
        try {
            Event event = eventRepository.getOne(id);
            mapDtoToEntity(updatedEventDTO, event);
            event = eventRepository.save(event);
            return new EventDTO(event);
        } catch (EntityNotFoundException e) {
            //e.printStackTrace();
            throw new ResourceNotFoundException("Evento com ID " + id + " não encontrado");       
            }
    }

    private void mapDtoToEntity(EventDTO updatedEventDTO, Event event) {
        event.setName(updatedEventDTO.getName());
        event.setDate(updatedEventDTO.getDate());
        event.setUrl(updatedEventDTO.getUrl());

        if (updatedEventDTO.getCityId() != null) {
            event.setCity(cityRepository.findById(updatedEventDTO.getCityId())
                            .orElseThrow(() -> new ResourceIdCityNotFoundException("Cidade não encontrada com ID: " + updatedEventDTO.getCityId())));
        } else {
            event.setCity(null);
        }
    }
}

