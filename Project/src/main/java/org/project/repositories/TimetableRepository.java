package org.project.repositories;

import org.project.entities.Timetable;

public class TimetableRepository extends DataRepository<Timetable, Long> {
    public Class<Timetable> getEntityClass() {
        return Timetable.class;
    }

    public TimetableRepository() {
    }

    public void create(Timetable timetable) {
        save(timetable);
    }

    public Timetable findByDay(int day) {
        return getEntityManager().
                createNamedQuery("Timetable.findByDay", Timetable.class)
                .setParameter(1, day)
                .getSingleResult();
    }
}
