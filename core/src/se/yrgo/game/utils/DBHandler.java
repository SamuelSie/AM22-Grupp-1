package se.yrgo.game.utils;

import java.sql.*;
public class DBHandler {
    public static void main(String[] args) {
        try (Connection conn = DriverManager.getConnection("jdbc:sqlite:core/src/se/yrgo/game/utils/test.db")) {
            Statement stm = conn.createStatement();
            ResultSet rs = stm.executeQuery("select * from hoho");

            while (rs.next()) {
                System.out.println(rs.getString("popo"));
            }
    }
        catch (SQLException e) {
            e.printStackTrace();
        }

    }
}
