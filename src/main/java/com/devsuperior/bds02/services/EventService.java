package com.devsuperior.bds02.services;

import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.devsuperior.bds02.dto.EventDTO;
import com.devsuperior.bds02.entities.City;
import com.devsuperior.bds02.entities.Event;
import com.devsuperior.bds02.repositories.CityRepository;
import com.devsuperior.bds02.repositories.EventRepository;
import com.devsuperior.bds02.services.exceptions.ResourceNotFoundException;

@Service
public class EventService {

	@Autowired
	private EventRepository repository;
	
	@Autowired
	private CityRepository cityRepository;
	
	@Transactional
	public EventDTO update(Long id, EventDTO dto) {
		try {
			City city = cityRepository.getOne(dto.getCityId());
			
			Event event = repository.getOne(id);
			event.setName(dto.getName());
			event.setDate(dto.getDate());
			event.setUrl(dto.getUrl());
			event.setCity(city);
			
			return new EventDTO(event);
		}
		catch(EntityNotFoundException e) {
			throw new ResourceNotFoundException("Resource not found");
		}
	}
	
	@Transactional(readOnly = true)
	public List<EventDTO> findAll(){
		List<Event> list = repository.findAll();
		return list.stream().map(x -> new EventDTO(x)).collect(Collectors.toList());
	}
	
}
