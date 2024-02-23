package com.l2o.planner.dto;

import java.util.List;

public class ScheduleResponse {
    public List<PersonScheduleResponse> personSchedules;
    public List<TaskResponse> unassignedTasks;
}
