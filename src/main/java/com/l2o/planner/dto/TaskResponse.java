package com.l2o.planner.dto;

import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class TaskResponse extends Task {
    public UUID id;
    public TaskType taskType;
}
