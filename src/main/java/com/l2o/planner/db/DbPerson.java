package com.l2o.planner.db;

import java.util.Objects;
import java.util.UUID;

import com.l2o.planner.dto.Person;
import com.l2o.planner.dto.PersonResponse;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity(name = "person")
public class DbPerson {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private String name;

    public DbPerson() {
    }

    public DbPerson(Person person) {
	copyFrom(person);
    }

    @Override
    public int hashCode() {
	return Objects.hash(id, name);
    }

    @Override
    public boolean equals(Object obj) {
	if (this == obj)
	    return true;
	if (obj == null)
	    return false;
	if (getClass() != obj.getClass())
	    return false;
	DbPerson other = (DbPerson) obj;
	return Objects.equals(id, other.id) && Objects.equals(name, other.name);
    }

    public DbPerson copyFrom(Person person) {
	this.name = person.name;
	return this;
    }

    public PersonResponse toResponse() {
	PersonResponse response = new PersonResponse();
	response.id = id;
	response.name = name;
	return response;
    }

    public UUID getId() {
	return id;
    }

    public void setId(UUID id) {
	this.id = id;
    }

    public String getName() {
	return name;
    }

    public void setName(String name) {
	this.name = name;
    }

}
