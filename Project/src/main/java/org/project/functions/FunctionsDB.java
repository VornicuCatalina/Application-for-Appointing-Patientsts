package org.project.functions;

import org.project.databases.Database;
import org.project.entities.FinalResult;
import org.project.repositories.DoctorRepository;
import org.project.repositories.FinalResultRepository;

import java.math.BigDecimal;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Types;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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

    public void deleteOlderDates() {
        try {
            Connection con = Database.getConnection();
            CallableStatement cstmt = con.prepareCall("{? = CALL deleteOlderDates()}");
            cstmt.registerOutParameter(1, Types.INTEGER);
            cstmt.executeUpdate();
            int result = cstmt.getInt(1);
            System.out.println("There had been deleted " + result + " rows");
            con.commit();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void updateChecked() {
        try {
            Connection con = Database.getConnection();
            CallableStatement cstmt = con.prepareCall("{? = CALL updateChecked()}");
            cstmt.registerOutParameter(1, Types.INTEGER);
            cstmt.executeUpdate();
            int result = cstmt.getInt(1);
            System.out.println("There had been updated " + result + " instances");
            con.commit();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    //PROCEDURES
    public int checkExistence(int id) {
        int person_type; // -1 - nonexistent ; 1-doc 0-patient
        try {
            Connection con = Database.getConnection();
            CallableStatement callableStatement = con.prepareCall("CALL checkExistence(?,?)");
            callableStatement.setInt(1, id);
            callableStatement.registerOutParameter(2, Types.INTEGER);
            callableStatement.executeUpdate();

            person_type = callableStatement.getInt(2);
            return person_type;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public String checkStatistics(int id) {
        BigDecimal percentage;
        try {
            Connection con = Database.getConnection();
            CallableStatement callableStatement = con.prepareCall("CALL get_doctor_statistics(?,?,?)");
            callableStatement.setDate(1, java.sql.Date.valueOf(java.time.LocalDate.now()));
            callableStatement.setInt(2, id);
            callableStatement.registerOutParameter(3, Types.NUMERIC);
            callableStatement.executeUpdate();

            percentage = callableStatement.getBigDecimal(3);
            String result = "The doctor with the id " + id + " has " + percentage + "% of his work hours this week assigned to patients";
            return result;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

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
                msg = "will have it soon";
            }
            FinalResult finalResult = finalResultRepository.findByIdPatient(id);
            System.out.println("The patient " + name_patient + " (id: " + id +
                    ") has an appointment with the doctor "
                    + name_doc + " (id: " + id_doc + ") on date "
                    + registrationDate.getDate() + "-" + (registrationDate.getMonth() + 1) + "-" + (registrationDate.getYear() + 1900)
                    + " at hour " + finalResult.getDate().getHours()
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
            if (result.isEmpty()) {
                System.out.println("No appointments!");
                return;
            }
            String[] eachRow = parseComma(result);
            for (int i = 0; i < eachRow.length; i++) {
                String[] rowItem = eachRow[i].split("_");
                String[] date = rowItem[2].split(" ");
                String[] timeDay = date[1].split(":");
                Date registrationDate;
                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
                try {
                    registrationDate = formatter.parse(date[0]);
                } catch (ParseException e) {
                    throw new RuntimeException(e);
                }
                System.out.println("The patient " + rowItem[1] + " (id: " + rowItem[0] + ") has its checkup on "
                        + registrationDate.getDate() + "-" + (registrationDate.getMonth() + 1) + "-" + (registrationDate.getYear() + 1900)
                        + " at hour " + timeDay[0] + ":" + timeDay[1]);
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
