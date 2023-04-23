package se.yrgo.utils;

import se.yrgo.game.screens.MainMenuScreen;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DBHandler {
    private final String connectionString;
    private Connection conn;

    public DBHandler() throws SQLException {
        //varför börjar den leta i assets istället för AM23?
        connectionString = "jdbc:sqlite:../core/src/se/yrgo/highscore.db";
        conn = DriverManager.getConnection(connectionString);
    }

    // hantera exception i Score
    public List<String> getTop5Highscore() throws SQLException {
        List<String> top5 = new ArrayList<>();

        try (Statement stm = conn.createStatement()) {

            ResultSet rs = stm.executeQuery("SELECT name, score FROM " + Difficulty.getTable() + " ORDER BY score DESC LIMIT 5");

            while (rs.next()) {
                StringBuilder sb = new StringBuilder();
                sb.append(rs.getString("name") + ": ");
                sb.append(rs.getString("score"));
                top5.add(sb.toString());
            }

            return top5;
        } catch (SQLException e) {
            throw new SQLException("Something wrong with getting " + Difficulty.getTable());
        }
    }

    public void putHighScore(int score) throws SQLException {
            String playerName = MainMenuScreen.getPlayerName();
            String updateString = "INSERT INTO " + Difficulty.getTable() + " VALUES (?, ?)";

        try (Statement stm = conn.createStatement()) {
            stm.execute("CREATE TABLE IF NOT EXISTS " + Difficulty.getTable() + " (name STRING, score INT)");

        }catch (SQLException e) {
            throw new SQLException("Something wrong with creating table... " + e.getMessage());
        }
        try (PreparedStatement pstm = conn.prepareStatement(updateString)) {
            pstm.setString(1, playerName);
            pstm.setInt(2, score);
            pstm.execute();

        } catch (SQLException e) {
            throw new SQLException("Something wrong with putting into " + Difficulty.getTable() + "... " + e.getMessage());
        }

    }


    //använd! Just nu stängs connection genom att stänga spelet.
    public void dispose() throws SQLException {
        conn.close();
    }
}

