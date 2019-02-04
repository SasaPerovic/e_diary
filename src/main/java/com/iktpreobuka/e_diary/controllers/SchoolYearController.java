package com.iktpreobuka.e_diary.controllers;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.iktpreobuka.e_diary.entities.SchoolYearEntity;
import com.iktpreobuka.e_diary.entities.dto.SchoolYearDTO;
import com.iktpreobuka.e_diary.services.SchoolYearServiceImpl;
import com.iktpreobuka.e_diary.util.RESTError;

@RestController
@RequestMapping ( path = "/api/v1/years")
public class SchoolYearController {
	
	@Autowired
	private SchoolYearServiceImpl yearService;
	
	//GET
	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<List<SchoolYearDTO>> getAllYear() {
		List<SchoolYearDTO> yearDto = new ArrayList<>(); 
		List<SchoolYearEntity> year =  yearService.getAllYear();
			
			for (SchoolYearEntity y : year) {
				yearDto.add(new SchoolYearDTO(y));
			}

		return new ResponseEntity<>(yearDto, HttpStatus.OK);
	}
	
	// GET BY ID
	@RequestMapping(method = RequestMethod.GET, value = "/{id}")
	public ResponseEntity<?> getYearById(@PathVariable Long id) {
		
		try {
			SchoolYearEntity sy = yearService.findYearById(id);
			SchoolYearDTO dto = new SchoolYearDTO(sy);
			return new ResponseEntity<>(dto, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<RESTError>(new RESTError("Year not found!"), HttpStatus.NOT_FOUND);
		}
		
	}
	
	//POST, postavlja i duplikate
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<?> saveYear(@Valid @RequestBody SchoolYearDTO yearDto, BindingResult result) {

		if (result.hasErrors()) {
			return new ResponseEntity<>(RESTError.createErrorMessage(result), HttpStatus.BAD_REQUEST);
		} else {
			try {
				SchoolYearEntity year = new SchoolYearEntity(yearDto);
				SchoolYearDTO newYearDto = new SchoolYearDTO(yearService.saveYear(year));
				return new ResponseEntity<>(newYearDto, HttpStatus.CREATED);
			} catch (Exception e) {
				return new ResponseEntity<>(("School year alredy exist!"), HttpStatus.BAD_REQUEST);
			}
		}
	}
	
	//PUT
	@RequestMapping(method = RequestMethod.PUT, value = "/{id}")
	public ResponseEntity<?> updateItem(@Valid @RequestBody SchoolYearDTO yearDTO, BindingResult result, @PathVariable("id") Long id) {

		if (result.hasErrors()) {
			return new ResponseEntity<>(RESTError.createErrorMessage(result), HttpStatus.BAD_REQUEST);
		} else {
			try {
				SchoolYearEntity year = new SchoolYearEntity(yearDTO);
				SchoolYearDTO updateYear = new SchoolYearDTO(yearService.updateYear(year, id));
				return new ResponseEntity<>(updateYear, HttpStatus.OK);
			} catch (Exception e) {
				return new ResponseEntity<>(("Error has occured!"), HttpStatus.BAD_REQUEST);
			}
		}
	}

	
	//DELETE
	@RequestMapping(method = RequestMethod.DELETE, value = "/{id}")
	public ResponseEntity<?> deleteYear(@PathVariable Long id) {
		try {
			if(yearService.removeParent(id))
				{return new ResponseEntity<RESTError>(new RESTError("Delete successfully!"), HttpStatus.OK);}
			else {
				return new ResponseEntity<RESTError>(new RESTError("School year not found!"), HttpStatus.NOT_FOUND);
			}
			
		} catch (Exception e) {
			return new ResponseEntity<RESTError>(new RESTError("Id not valid"), HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
	}

}
