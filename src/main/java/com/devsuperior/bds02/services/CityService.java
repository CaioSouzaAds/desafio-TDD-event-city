package com.devsuperior.bds02.services;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.devsuperior.bds02.dto.CityDTO;
import com.devsuperior.bds02.entities.City;
import com.devsuperior.bds02.repository.CityRepository;
import com.devsuperior.bds02.services.exceptions.IntegrityConstraintViolationException;
import com.devsuperior.bds02.services.exceptions.ResourceNotFoundException;

@Service
public class CityService {

	@Autowired
	private CityRepository cityRepository;

	public List<CityDTO> findAll() {
		List<City> list = cityRepository.findAll(Sort.by("name"));

		return list.stream().map(CityDTO::new).collect(Collectors.toList());
	}

	@Transactional
	public CityDTO insert(CityDTO cityDTO) {
		City city = new City();
		city.setName(cityDTO.getName());

		city = cityRepository.save(city);

		return new CityDTO(city);
	}

	@Transactional
	public void delete(Long id) {
	    try {
	        Optional<City> optionalCity = cityRepository.findById(id);

	        optionalCity.orElseThrow(() -> new NoSuchElementException("City not found with ID " + id));

	        
	        cityRepository.deleteById(id);

	    } catch (NoSuchElementException e) {
	        throw new ResourceNotFoundException("Error deleting city with ID " + id);
	    } catch (DataIntegrityViolationException e) {
	       
	        //e.printStackTrace();
	        throw new IntegrityConstraintViolationException("Cannot delete city with ID " + id + ". Referential integrity constraint violation: There are associated events or dependencies.");
	    }
	}

}
