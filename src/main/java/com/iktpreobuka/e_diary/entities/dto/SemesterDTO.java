package com.iktpreobuka.e_diary.entities.dto;

import java.util.ArrayList;
import java.util.Date;
import java.util.stream.Collectors;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Range;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.iktpreobuka.e_diary.entities.MarkEntity;
import com.iktpreobuka.e_diary.entities.SemesterEntity;


public class SemesterDTO {
	
	private Long id;
	
	@NotNull(message="Order number must be provided!")
	@Range (min = 1, max = 2, message = "Order number must be {min} or {max}!")
	private Integer orderNumber;
	
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone="Europe/Belgrade")
	private Date dateFrom;
	
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone="Europe/Belgrade")
	private Date dateTo;
	
	private Long schoolYearID;
	private ArrayList<Long> marksIDs;
	
	public SemesterDTO(Long id, Integer orderNumber, Date dateFrom, Date dateTo, Long schoolYearID, ArrayList<Long> marksIDs) {
		super();
		this.id = id;
		this.orderNumber = orderNumber;
		this.dateFrom = dateFrom;
		this.dateTo = dateTo;
		this.schoolYearID = schoolYearID;
		this.marksIDs = marksIDs;
	}
	
	public SemesterDTO (SemesterEntity semester) {
		this.id = semester.getId();
		this.orderNumber = semester.getOrderNumber();
		this.dateFrom = semester.getDateFrom();
		this.dateTo = semester.getDateTo();
		this.schoolYearID = semester.getSchoolYear().getId();
		this.marksIDs = (ArrayList<Long>) semester.getMarks().stream().map(MarkEntity::getId).collect(Collectors.toList());
	}

	public Long getId() {
		return id;
	}

	public Integer getOrderNumber() {
		return orderNumber;
	}

	public Date getDateFrom() {
		return dateFrom;
	}

	public Date getDateTo() {
		return dateTo;
	}

	public Long getSchoolYearID() {
		return schoolYearID;
	}

	public ArrayList<Long> getMarksIDs() {
		return marksIDs;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setOrderNumber(Integer orderNumber) {
		this.orderNumber = orderNumber;
	}

	public void setDateFrom(Date dateFrom) {
		this.dateFrom = dateFrom;
	}

	public void setDateTo(Date dateTo) {
		this.dateTo = dateTo;
	}

	public void setSchoolYearID(Long schoolYearID) {
		this.schoolYearID = schoolYearID;
	}

	public void setMarksIDs(ArrayList<Long> marksIDs) {
		this.marksIDs = marksIDs;
	}
	
	

}
