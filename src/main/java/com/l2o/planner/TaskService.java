package com.l2o.planner;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

import org.hibernate.reactive.mutiny.Mutiny;

import com.l2o.planner.db.DbTask;
import com.l2o.planner.dto.Task;
import com.l2o.planner.dto.TaskResponse;

import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.criteria.CriteriaQuery;

@ApplicationScoped
public class TaskService {
    @Inject
    private Mutiny.SessionFactory sf;

    public Uni<List<TaskResponse>> getTasks(Instant from, Instant to) {
	CriteriaQuery<DbTask> query = sf.getCriteriaBuilder().createQuery(DbTask.class);
	query.select(query.from(DbTask.class));
	return sf.withSession(session -> session.createQuery(query).getResultList()
		.map(list -> list.stream().map(DbTask::toResponse).toList()));
    }

    public Uni<TaskResponse> create(Task task) {
	DbTask entity = new DbTask(task);
	return sf.withTransaction(session -> session.persist(entity)).map(unused -> entity.toResponse());
    }

    public Uni<TaskResponse> get(UUID id) {
	return sf.withSession(session -> session.find(DbTask.class, id)).map(DbTask::toResponse);
    }

    public Uni<Void> delete(UUID id) {
	return sf.withTransaction(session -> session.find(DbTask.class, id).flatMap(session::remove));
    }

    public Uni<TaskResponse> update(UUID id, Task task) {
	return sf.withTransaction(session -> session.find(DbTask.class, id).map(dbEntity -> dbEntity.copyFrom(task)))
		.map(DbTask::toResponse);
    }
}
