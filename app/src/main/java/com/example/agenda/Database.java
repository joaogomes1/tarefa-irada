package com.example.agenda;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class Database {
    Connection conn;
    Statement stmt;

    Database() {
        conn = null;
        stmt = null;

        try {
            Class.forName("org.sqlite.JDBC");
            conn = DriverManager.getConnection("jdbc:sqlite:test.db");

            stmt = conn.createStatement();
            String query = "CREATE TABLE IF NOT EXISTS agenda " +
                    "( id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "  data DATE NOT NULL, " +
                    "  descricao TEXT NOT NULL);";

            stmt.executeUpdate(query);
            // stmt.close();
            // conn.close();
        } catch ( Exception e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
        }
    }
}
