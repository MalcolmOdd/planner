package com.l2o.planner.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class PersonScheduleResponse {
    public PersonResponse person;
    public List<TaskResponse> tasks;
}
