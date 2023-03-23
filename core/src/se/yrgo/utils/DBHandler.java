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

//
//    public void putHighscore(int score) {
//
//    }
//    public static void main(String[] args) {
//        try (Connection conn = DriverManager.getConnection("jdbc:sqlite:core/src/se/yrgo/game/utils/highscore.db")) {
//            Statement stm = conn.createStatement();
//            ResultSet rs = stm.executeQuery("select * from hoho");
//
//            while (rs.next()) {
//                System.out.println(rs.getString("popo"));
//            }
//    }
//        catch (SQLException e) {
//            e.printStackTrace();
//        }
//
//    }
    }
}
