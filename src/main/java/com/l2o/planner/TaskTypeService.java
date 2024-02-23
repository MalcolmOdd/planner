package com.l2o.planner;

import java.util.List;
import java.util.UUID;

import org.hibernate.reactive.mutiny.Mutiny;

import com.l2o.planner.db.DbTaskType;
import com.l2o.planner.dto.TaskType;
import com.l2o.planner.dto.TaskTypeResponse;

import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.criteria.CriteriaQuery;

@ApplicationScoped
public class TaskTypeService {
    @Inject
    private Mutiny.SessionFactory sf;

    public Uni<List<TaskTypeResponse>> getAll() {
	CriteriaQuery<DbTaskType> query = sf.getCriteriaBuilder().createQuery(DbTaskType.class);
	query.select(query.from(DbTaskType.class));
	return sf.withSession(session -> session.createQuery(query).getResultList()
		.map(list -> list.stream().map(DbTaskType::toResponse).toList()));
    }

    public Uni<TaskTypeResponse> create(TaskType taskType) {
	DbTaskType entity = new DbTaskType(taskType);
	return sf.withTransaction(session -> session.persist(entity)).map(unused -> entity.toResponse());
    }

    public Uni<TaskTypeResponse> get(UUID id) {
	return sf.withSession(session -> session.find(DbTaskType.class, id)).map(DbTaskType::toResponse);
    }

    public Uni<Void> delete(UUID id) {
	return sf.withTransaction(session -> session.find(DbTaskType.class, id).flatMap(session::remove));
    }

    public Uni<TaskTypeResponse> update(UUID id, TaskType taskType) {
	return sf.withTransaction(session -> session.find(DbTaskType.class, id).map(dbEntity -> dbEntity.copyFrom(taskType))).map(DbTaskType::toResponse);
    }
}
