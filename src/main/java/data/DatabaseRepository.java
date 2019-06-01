package data;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

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

    //todo is this function even necessary?
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

            result = new PlayerInfo(new Long(resultSet.getInt("id")),
                    resultSet.getString("player_name"),
                    resultSet.getDouble("height"),
                    resultSet.getDouble("weight"),
                    resultSet.getInt("overall_rating"),
                    resultSet.getInt("potential"),
                    resultSet.getInt("acceleration"),
                    resultSet.getInt("sprint_speed"),
                    resultSet.getInt("agility"),
                    resultSet.getInt("shot_power"),
                    resultSet.getInt("stamina"),
                    resultSet.getInt("strength"),
                    resultSet.getInt("aggression"),
                    resultSet.getInt("age"));
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        } finally {
            closeConnection(resultSet, statement, connection);
        }

        return result;
    }

    //todo possible SQL injection - fix it
    public double getPlayerAttribute(Integer id, String attributeName) {
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        double result = 0;
        try {
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection("jdbc:sqlite:src/main/resources/database.sqlite");
            connection.setAutoCommit(false);

            statement = connection.createStatement();
            resultSet = statement.executeQuery("SELECT " + attributeName + " FROM PLAYERS_INFO WHERE ROWID = " + id);

            result = resultSet.getDouble(attributeName);
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
