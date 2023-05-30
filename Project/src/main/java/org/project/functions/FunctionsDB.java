package org.project.functions;

import org.project.databases.Database;
import org.project.entities.FinalResult;
import org.project.repositories.DoctorRepository;
import org.project.repositories.FinalResultRepository;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Date;

public class FunctionsDB {
    DoctorRepository doctorRepository = new DoctorRepository();
    FinalResultRepository finalResultRepository = new FinalResultRepository();

    private void examplePLSQ() {
        try {
            Connection con = Database.getConnection();
            CallableStatement cstmt = con.prepareCall("{? = CALL balance(?)}");
            cstmt.registerOutParameter(1, Types.VARCHAR);
            cstmt.setInt(2, 1);
            cstmt.executeUpdate();
            String acctBal = cstmt.getString(1);
            System.out.println(acctBal);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    //PROCEDURES
    public void showPatient(int id) {
        String name_doc;
        String name_patient;
        int id_doc;
        Date registrationDate;
        boolean visited;
        try {
            Connection con = Database.getConnection();
            CallableStatement callableStatement = con.prepareCall("CALL showPatient(?,?,?,?,?,?)");
            callableStatement.setInt(1, id);
            callableStatement.registerOutParameter(2, Types.VARCHAR);
            callableStatement.registerOutParameter(3, Types.INTEGER);
            callableStatement.registerOutParameter(4, Types.VARCHAR);
            callableStatement.registerOutParameter(5, Types.DATE);
            callableStatement.registerOutParameter(6, Types.BOOLEAN);
            callableStatement.executeUpdate();

            name_patient = callableStatement.getString(2);
            id_doc = callableStatement.getInt(3);
            name_doc = callableStatement.getString(4);
            registrationDate = callableStatement.getDate(5);
            visited = callableStatement.getBoolean(6);
            System.out.println(registrationDate);
            String msg;
            if (visited) {
                msg = "already had this checkup";
            } else {
                msg = "will be have it soon";
            }
            FinalResult finalResult = finalResultRepository.findByIdPatient(id);
            System.out.println("The patient " + name_patient + " (id: " + id +
                    ") has an appointment with the doctor "
                    + name_doc + " (id: " + id_doc + ") on date " + registrationDate + " at hour " + finalResult.getDate().getHours()
                    + ":" + finalResult.getDate().getMinutes() + ".You " + msg);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void showDoctor(int id) {
        System.out.println("That is your schedule doctor " + doctorRepository.findById(id).getName() + " (id: " + id + ")");
        String result;
        try {
            Connection con = Database.getConnection();
            CallableStatement callableStatement = con.prepareCall("CALL showDoctor(?,?)");
            callableStatement.setInt(1, id);
            callableStatement.registerOutParameter(2, Types.VARCHAR);

            callableStatement.executeUpdate();

            result = callableStatement.getString(2);
            String[] eachRow = parseComma(result);
            for (int i = 0; i < eachRow.length; i++) {
                String[] rowItem = eachRow[i].split("_");
                String[] date = rowItem[2].split(" ");
                String[] timeDay = date[1].split(":");

                System.out.println("The patient " + rowItem[1] + " (id: " + rowItem[0] + ") has its checkup on " +
                        date[0] + " at hour " + timeDay[0] + ":" + timeDay[1]);
            }

            //parsing stuff
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private String[] parseComma(String s) {
        String[] stringArrayList = s.split(",");
        return stringArrayList;
    }

}
