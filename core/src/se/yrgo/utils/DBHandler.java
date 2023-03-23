package se.yrgo.utils;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DBHandler {
    private String connectionString;


    public DBHandler() {
        connectionString = "jdbc:sqlite:core/src/se/yrgo/database/highscore.db";
    }

    // hantera exception i Score
    public List<String> getTop5Highscore() throws SQLException {
        List<String> top5 = new ArrayList<>();

        try (Connection conn = DriverManager.getConnection(connectionString)) {
            Statement stm = conn.createStatement();
            ResultSet rs = stm.executeQuery("SELECT score FROM highscore ORDER BY score LIMIT 5");

            while (rs.next()) {
                top5.add(rs.getString("score"));
            }

            return top5;
        }
    }

    public void putHighScore(int score) throws SQLException {
        try (Connection conn = DriverManager.getConnection(connectionString)) {
            PreparedStatement pstm = conn.prepareStatement("INSERT INTO highscore VALUES (score = ?)");
            pstm.setInt(1, score);
            pstm.execute();
        }
    }
}

