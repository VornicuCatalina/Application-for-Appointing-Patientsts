package org.project.functions;

import org.project.databases.Database;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Date;

public class FunctionsDB {
    private void examplePLSQ(){
        try{
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
    public void showPatient(int id){
        String name_doc;
        String name_patient;
        int id_doc;
        Date registrationDate;
        boolean visited;
        try{
            Connection con = Database.getConnection();
            CallableStatement callableStatement = con.prepareCall("CALL showPatient(?,?,?,?,?,?)");
            callableStatement.setInt(1,id);
            callableStatement.registerOutParameter(2,Types.VARCHAR);
            callableStatement.registerOutParameter(3,Types.INTEGER);
            callableStatement.registerOutParameter(4,Types.VARCHAR);
            callableStatement.registerOutParameter(5,Types.DATE);
            callableStatement.registerOutParameter(6,Types.BOOLEAN);
            callableStatement.executeUpdate();

            name_patient = callableStatement.getString(2);
            id_doc = callableStatement.getInt(3);
            name_doc = callableStatement.getString(4);
            registrationDate = callableStatement.getDate(5);
            visited = callableStatement.getBoolean(6);

            System.out.println("The patient "+name_patient+" with the id "+id+
                    " has an appointment with the " + id_doc +" id of the " +
                    "doctor "+name_doc+" on date "+registrationDate+" and it");

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
