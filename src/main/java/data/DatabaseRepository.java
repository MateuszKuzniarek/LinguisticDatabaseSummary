package data;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class DatabaseRepository {

    public int getPlayerCount() {
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        int result = 0;
        try {
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection("jdbc:sqlite:src/main/resources/database.sqlite");
            connection.setAutoCommit(false);

            statement = connection.createStatement();

            resultSet = statement.executeQuery("SELECT COUNT(*) FROM PLAYERS_INFO");

            result = resultSet.getInt(1);
            return result;
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        } finally {
            closeConnection(resultSet, statement, connection);
        }

        return result;
    }

    public PlayerInfo getPlayerInfo(Integer id) {
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        PlayerInfo result = null;
        try {
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection("jdbc:sqlite:src/main/resources/database.sqlite");
            connection.setAutoCommit(false);

            statement = connection.createStatement();

            resultSet = statement.executeQuery("SELECT * FROM PLAYERS_INFO WHERE ROWID=" + id + ";");

            result = new PlayerInfo(
                    resultSet.getString("player_name"),
                    resultSet.getDouble("height"),
                    resultSet.getDouble("weight"),
                    resultSet.getDouble("overall_rating"),
                    resultSet.getDouble("potential"),
                    resultSet.getDouble("acceleration"),
                    resultSet.getDouble("sprint_speed"),
                    resultSet.getDouble("agility"),
                    resultSet.getDouble("shot_power"),
                    resultSet.getDouble("stamina"),
                    resultSet.getDouble("strength"),
                    resultSet.getDouble("aggression"),
                    resultSet.getDouble("age"));
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        } finally {
            closeConnection(resultSet, statement, connection);
        }

        return result;
    }

    public List<PlayerInfo> getAllPlayersInfo() {
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        List<PlayerInfo> result = new ArrayList<>();
        try {
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection("jdbc:sqlite:src/main/resources/database.sqlite");
            connection.setAutoCommit(false);

            statement = connection.createStatement();

            resultSet = statement.executeQuery("SELECT * FROM PLAYERS_INFO");

            while(resultSet.next()) {
                result.add( new PlayerInfo(
                        resultSet.getString("player_name"),
                        resultSet.getDouble("height"),
                        resultSet.getDouble("weight"),
                        resultSet.getDouble("overall_rating"),
                        resultSet.getDouble("potential"),
                        resultSet.getDouble("acceleration"),
                        resultSet.getDouble("sprint_speed"),
                        resultSet.getDouble("agility"),
                        resultSet.getDouble("shot_power"),
                        resultSet.getDouble("stamina"),
                        resultSet.getDouble("strength"),
                        resultSet.getDouble("aggression"),
                        resultSet.getDouble("age")));
            }

        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        } finally {
            closeConnection(resultSet, statement, connection);
        }

        return result;
    }

    private void closeConnection(ResultSet resultSet, Statement statement, Connection connection) {
        try {
            if (resultSet != null) {
                resultSet.close();
            }
            if (statement != null) {
                statement.close();
            }
            if (connection != null) {
                connection.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
