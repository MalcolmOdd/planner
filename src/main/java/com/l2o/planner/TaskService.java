package com.l2o.planner;

import java.time.Instant;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.hibernate.reactive.mutiny.Mutiny;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.l2o.planner.db.DbPerson;
import com.l2o.planner.db.DbTask;
import com.l2o.planner.dto.PersonScheduleResponse;
import com.l2o.planner.dto.ScheduleResponse;
import com.l2o.planner.dto.Task;
import com.l2o.planner.dto.TaskResponse;

import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.criteria.CriteriaQuery;

@ApplicationScoped
public class TaskService {
    private static final Logger LOG = LoggerFactory.getLogger(TaskService.class);
    @Inject
    private Mutiny.SessionFactory sf;

    public Uni<ScheduleResponse> getTasks(Instant from, Instant to) {
	CriteriaQuery<DbTask> taskQuery = sf.getCriteriaBuilder().createQuery(DbTask.class);
	taskQuery.select(taskQuery.from(DbTask.class));
	CriteriaQuery<DbPerson> personQuery = sf.getCriteriaBuilder().createQuery(DbPerson.class);
	personQuery.select(personQuery.from(DbPerson.class));

	return sf
		.withSession(session -> session.createQuery(taskQuery).getResultList()
			.flatMap(tasks -> session.createQuery(personQuery).getResultList().map(persons -> toSchedule(tasks, persons))));
    }

    private ScheduleResponse toSchedule(List<DbTask> tasks, List<DbPerson> persons) {
	Map<UUID, PersonScheduleResponse> schedules = new HashMap<UUID, PersonScheduleResponse>();
	for (DbPerson dbPerson : persons) {
	    PersonScheduleResponse psr = new PersonScheduleResponse();
	    psr.person = dbPerson.toResponse();
	    psr.tasks = new ArrayList<>();
	    schedules.put(dbPerson.getId(), psr);
	}
	List<TaskResponse> unassignedTasks = new ArrayList<>();
	for (DbTask dbTask : tasks) {
	    TaskResponse task = dbTask.toResponse();
	    if (dbTask.personId == null) {
		unassignedTasks.add(task);
	    } else {
		PersonScheduleResponse psr = schedules.get(dbTask.personId);
		if (psr != null) {
		    psr.tasks.add(task);
		} else {
		    LOG.warn("Inconsistent assignment for task {} - missing person {}", dbTask.getId(),
			    dbTask.getPersonId());
		}
	    }
	}
	ScheduleResponse scheduleResponse = new ScheduleResponse();
	scheduleResponse.personSchedules = List.copyOf(schedules.values());
	scheduleResponse.unassignedTasks = unassignedTasks;
	return scheduleResponse;
    }

    public Uni<TaskResponse> create(Task task) {
	DbTask entity = new DbTask(task);
	return sf.withTransaction(session -> session.persist(entity)).map(unused -> entity.toResponse());
    }

    public Uni<Void> assign(UUID taskId, UUID personId) {
	return sf
		.withTransaction(
			session -> session.find(DbTask.class, taskId).map(dbTask -> dbTask.personId = personId))
		.replaceWithVoid();
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
