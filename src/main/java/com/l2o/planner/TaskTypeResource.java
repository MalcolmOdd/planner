package com.l2o.planner;

import java.util.List;
import java.util.UUID;

import com.l2o.planner.dto.TaskType;
import com.l2o.planner.dto.TaskTypeResponse;

import io.smallrye.mutiny.Uni;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;

@Path("/v1/task-type")
public class TaskTypeResource {
    private final TaskTypeService taskTypeService;

    public TaskTypeResource(TaskTypeService taskTypeService) {
	this.taskTypeService = taskTypeService;
    }

    @GET
    public Uni<List<TaskTypeResponse>> getAll() {
	return taskTypeService.getAll();
    }

    @POST
    public Uni<TaskTypeResponse> create(TaskType taskType) {

	return taskTypeService.create(taskType);
    }

    @GET
    @Path("{id}")
    public Uni<TaskTypeResponse> get(@PathParam("id") UUID id) {
	return taskTypeService.get(id);
    }

    @DELETE
    @Path("{id}")
    public Uni<Void> delete(@PathParam("id") UUID id) {
	return taskTypeService.delete(id);
    }

    @PUT
    @Path("{id}")
    public Uni<TaskTypeResponse> update(@PathParam("id") UUID id, TaskType taskType) {
	return taskTypeService.update(id, taskType);
    }
}
