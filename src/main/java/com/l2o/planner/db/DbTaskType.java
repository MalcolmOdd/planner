package com.l2o.planner.db;

import java.util.Objects;
import java.util.UUID;

import com.l2o.planner.dto.TaskType;
import com.l2o.planner.dto.TaskTypeResponse;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity(name = "tasktype")
public class DbTaskType {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private String name;

    public DbTaskType() {
    }

    public DbTaskType(TaskType taskType) {
	copyFrom(taskType);
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
	DbTaskType other = (DbTaskType) obj;
	return Objects.equals(id, other.id) && Objects.equals(name, other.name);
    }

    public DbTaskType copyFrom(TaskType taskType) {
	this.name = taskType.name;
	return this;
    }
    
    public TaskTypeResponse toResponse() {
	TaskTypeResponse taskTypeResult = new TaskTypeResponse();
	taskTypeResult.id = id;
	taskTypeResult.name = name;
	return taskTypeResult;
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
