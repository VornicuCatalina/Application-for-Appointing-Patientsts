package org.project.repositories;

import org.project.entities.FinalResult;

public class FinalResultRepository extends DataRepository<FinalResult,Long> {
    public Class<FinalResult> getEntityClass(){
        return FinalResult.class;
    }

    public FinalResultRepository(){}

    public void create(FinalResult finalResult){save(finalResult);}
}
