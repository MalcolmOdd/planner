package com.l2o.planner;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

import com.l2o.planner.dto.Task;
import com.l2o.planner.dto.TaskResponse;

import io.smallrye.mutiny.Uni;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.QueryParam;

@Path("/v1/task")
public class TaskResource {
    private final TaskService taskService;

    public TaskResource(TaskService taskService) {
	this.taskService = taskService;
    }

    @GET
    public Uni<List<TaskResponse>> getTasks(@QueryParam("from") Instant from, @QueryParam("to") Instant to) {
	return taskService.getTasks(from, to);
    }

    @POST
    public Uni<TaskResponse> create(Task task) {

	return taskService.create(task);
    }

    @GET
    @Path("{id}")
    public Uni<TaskResponse> get(@PathParam("id") UUID id) {
	return taskService.get(id);
    }

    @DELETE
    @Path("{id}")
    public Uni<Void> delete(@PathParam("id") UUID id) {
	return taskService.delete(id);
    }

    @PUT
    @Path("{id}")
    public Uni<TaskResponse> update(@PathParam("id") UUID id, Task task) {
	return taskService.update(id, task);
    }
}
