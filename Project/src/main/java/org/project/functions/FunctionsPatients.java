package org.project.functions;

import java.util.ArrayList;

public class FunctionsPatients {
    FunctionsDB functionsDB = new FunctionsDB();

    public ArrayList<Integer> listOfDoctorsId(String preference) {
        ArrayList<Integer> finalIds = new ArrayList<>();
        String[] ids = preference.split(",");
        for (int i = 0; i < ids.length; i++) {
            int idDoc = Integer.parseInt(ids[i]);
            if (functionsDB.checkExistence(idDoc) == 1) {
                finalIds.add(idDoc);
            }
        }
        return finalIds;
    }

    public ArrayList<Integer> listOfDoctorsForJSONFile(ArrayList<Integer> arrayList) {
        ArrayList<Integer> finalIds = new ArrayList<>();
        for (Integer id : arrayList) {
            if (functionsDB.checkExistence(id) == 1) {
                finalIds.add(id);
            }
        }
        return finalIds;
    }
}
