package org.project.repositories;

import org.project.entities.Doctor;

public class DoctorRepository extends DataRepository<Doctor, Long> {
    public Class<Doctor> getEntityClass() {
        return Doctor.class;
    }

    public DoctorRepository() {
    }

    public void create(Doctor doctor) {
        save(doctor);
    }

    public Doctor findById(int id) {
        return getEntityManager().
                createNamedQuery("Doctor.findById", Doctor.class)
                .setParameter(1, id)
                .getSingleResult();
    }
}
