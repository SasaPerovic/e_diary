package com.iktpreobuka.e_diary.entities;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table (name = "parent")
public class ParentEntity extends PersonEntity {
	
	private List<StudentEntity> students = new ArrayList<>();

	
	public ParentEntity() {
		super();
	}

	public ParentEntity(String name, String lastName, String address, String phoneNumber, String jmbg, String email,
			Date birthDate, List<StudentEntity> students) {
		super(name, lastName, address, phoneNumber, jmbg, email, birthDate);
		this.students = students;
	}

	@OneToMany (mappedBy = "parent", fetch = FetchType.LAZY, cascade = {CascadeType.REFRESH})
	public List<StudentEntity> getStudents() {
		return students;
	}

	public void setStudents(List<StudentEntity> students) {
		this.students = students;
	}
	
	

}
