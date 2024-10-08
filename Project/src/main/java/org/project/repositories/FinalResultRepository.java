package org.project.repositories;

import org.project.entities.FinalResult;

import java.util.List;

public class FinalResultRepository extends DataRepository<FinalResult, Long> {
    public Class<FinalResult> getEntityClass() {
        return FinalResult.class;
    }

    public FinalResultRepository() {
    }

    public void create(FinalResult finalResult) {
        save(finalResult);
    }

    public List<FinalResult> findByIdDoc(int id) {
        return getEntityManager().
                createNamedQuery("Result.findByIdDoc", FinalResult.class)
                .setParameter(1, id)
                .getResultList();
    }

    public FinalResult findByIdPatient(int id) {
        return getEntityManager().
                createNamedQuery("Result.findByIdPatient", FinalResult.class)
                .setParameter(1, id)
                .getSingleResult();
    }
}
