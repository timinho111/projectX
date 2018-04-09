package fme.ibd;

import com.exasol.jdbc.EXADriver;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBconnection {
    Connection connection = null;

    public DBconnection(){
        String connectionUrl = "jdbc:exa:exasol-p-n11.hg.fresenius.de:8563;" +
                "user=FME_AKQUISE;password=FME_AKQUISE2018";
        try {
            DriverManager.registerDriver(new EXADriver());
            System.out.println("EXASOL JDBC Driver Registered!");
        } catch (SQLException e) {
            System.out.println("Registration failed! Check output console");
            e.printStackTrace();
            return;
        }
        try {
            connection = DriverManager.getConnection(connectionUrl);

        } catch (SQLException e) {
            System.out.println("Connection Failed! Check output console");
            e.printStackTrace();
            return;
        }

        if (connection != null) {
            System.out.println("You made it! Took control of the database.");
        } else {
            System.out.println("Failed to make connection!");
        }
        try {

            connection.setSchema("FME_AKQUISE");
            System.out.println(connection.getSchema());
        } catch (Exception e) {
            System.err.println("SQL ERROR");
            System.err.println(e.getMessage());
        }

    }

    public Connection getConnection(){
        return connection;
    }



}
