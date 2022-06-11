package com.example.agenda;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Database {
    Connection conn;
    Statement stmt;

    Database() {
        conn = null;

        try {
            // conseguir caminho para o arquivo de banco de dados
            Class.forName("org.sqlite.JDBC");
            conn = DriverManager.getConnection("jdbc:sqlite://android_asset/agenda.db");

            stmt = conn.createStatement();
            stmt.setQueryTimeout(30);

            // creates table
            String create_table = "CREATE TABLE IF NOT EXISTS agenda " +
                    "( id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "  description TEXT NOT NULL);";
            stmt.executeUpdate(create_table);
        } catch (SQLException | ClassNotFoundException e) {
            // exception with sqlite
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        }
    }

    public int executeQuery(String query) {
        System.out.println(query);
        try {
            stmt.executeUpdate(query);
        } catch (SQLException e) {
            // exception with sqlite
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            return -1;
        }
        return 0;
    }

    public String[] readQuery(String query) {
        // get total items in the database
        int size = 0;
        int count = 0;

        // empty array to return on errors
        String[] empty = {};

        try {
            ResultSet rs = stmt.executeQuery("SELECT COUNT(id) FROM agenda;");
            if (rs != null) {
                rs.last();          // moves cursor to the last row
                size = rs.getRow(); // get row id
            }
        } catch (SQLException e) {
            // exception with sqlite
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            return empty;
        }

        String[] ra = new String[size];

        try {
            ResultSet result = stmt.executeQuery(query);
            while (result.next()) {
                ra[count] = result.getString("description");
                ++count;
            }
        } catch (SQLException e) {
            // exception with sqlite
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            return empty;
        }
        return ra;
    }
}
