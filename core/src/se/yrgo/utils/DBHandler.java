package se.yrgo.utils;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DBHandler {
    private String connectionString;
    private Connection conn;


    public DBHandler() throws SQLException {
        //varför börjar den leta i assets istället för AM23?
        connectionString = "jdbc:sqlite:../core/src/se/yrgo/database/highscore.db";
        conn = DriverManager.getConnection(connectionString);
    }

    // hantera exception i Score
    public List<String> getTop5Highscore() throws SQLException {
        List<String> top5 = new ArrayList<>();

        try {
            Statement stm = conn.createStatement();
            ResultSet rs = stm.executeQuery("SELECT score FROM highscore ORDER BY score DESC LIMIT 5");

            while (rs.next()) {
                top5.add(rs.getString("score"));
            }

            return top5;
        }
        catch (SQLException e) {
            throw new SQLException("Something wrong with getting highscore");
        }
    }

    public void putHighScore(int score) throws SQLException {
        try {
            String updateString = "INSERT INTO highscore VALUES (?)";
            PreparedStatement pstm = conn.prepareStatement(updateString);
            pstm.setInt(1, score);
            pstm.execute();
        }
        catch (SQLException e ) {
            throw new SQLException("Something wrong with putting into highscore..." + e.getMessage());

        }
    }


    //använd! Just nu stängs connection genom att stänga spelet.
    public void dispose() throws SQLException {
        conn.close();
    }
}

