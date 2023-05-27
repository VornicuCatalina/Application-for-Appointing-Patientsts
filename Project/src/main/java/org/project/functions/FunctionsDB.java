package org.project.functions;

import org.project.databases.Database;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Types;

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
}
