package com.l2o.planner.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ScheduleResponse {
    public List<PersonScheduleResponse> personSchedules;
    public List<TaskResponse> unassignedTasks;
}
