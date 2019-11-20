package utils;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import entity.GameScore;
import entity.Player;
import org.apache.derby.jdbc.EmbeddedDriver;

/*
 Author : Manjul Shrestha
 Description: Created to establish database connection
 Prerequisite: derby.jar file is needed to get this class working, derby.jar added in project library.
 If required please download it from http://db.apache.org/derby/releases/release-10.14.2.0.cgi

 ChangeLog:

 */
public class DatabaseConnection {

    Connection conn = null;
    PreparedStatement pstmt;
    Statement stmt;
    ResultSet rs = null;

    /*
     * Test cases for database connection , insert, fetch and validate player and save/fetch game score
     */
    public static void main(String[] args) {
        DatabaseConnection connection = new DatabaseConnection();
       /* Player player = new Player();
        player.setPassword("123");
        player.setPlayerName("Test Player");
        player.setUserName("testPlayer");
        // connection.insertPlayer(player);*/
       /* for (Player player1 : connection.getPlayers()) {
            System.out.println(player1.getId());
        }*/
       /* Player validatePlayer = connection.validatePlayer(player);
        System.out.println(validatePlayer.getPlayerName());*/
        GameScore score = new GameScore();
        Player player1 = new Player();
        player1.setId(101);
        Player player2 = new Player();
        player2.setId(201);
        score.setWinningPlayer(player1);
        score.setLosingPlayer(player2);
        score.setWinningPlayerScore(900);
        score.setLosingPlayerScore(895);
        connection.insertGameScore(score);
        for (GameScore score1 : connection.getGameScore()) {
            System.out.println(score1.getWinningPlayer().getPlayerName());
        }
    }

    public DatabaseConnection() {
        // dropTables();
        //createTables();
    }

    /*
     * To create the tables in the initilization of the game, should be executed
     * only once while installing or running
     */
    public void createTables() {
        String createSQL = "create table player (" + "id integer not null generated always as"
                + " identity (start with 1, increment by 1),   " + "name varchar(30) not null, username varchar(30),"
                + "password varchar(100)," + "constraint primary_key_player primary key (id))";
        String createSQLScore = "create table game_score (id integer not null generated always as"
                + " identity (start with 1, increment by 1),   wining_player_id integer not null, losing_player_id integer not null,"
                + "winning_score integer, losing_score integer,  constraint pk_game_id primary key (id))";
        try {
            conn = initiateConnection();
            stmt = conn.createStatement();
            //  stmt.execute(createSQL);
            stmt.execute(createSQLScore);
            conn.commit();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public void stopConnection() {
        try {
            DriverManager.getConnection("jdbc:derby:;shutdown=true");
        } catch (SQLException ex) {
            if (ex.getErrorCode() == 50000 && "XJ015".equals(ex.getSQLState())) {
                System.out.println("Derby shut down normally");
            } else {
                System.err.println("Derby did not shut down normally");
                System.err.println(ex.getMessage());
            }
        }
    }

    public Connection initiateConnection() {
        try {
            Driver derbyEmbeddedDriver = new EmbeddedDriver();
            DriverManager.registerDriver(derbyEmbeddedDriver);
            conn = DriverManager.getConnection("jdbc:derby:testdb1;create=true");// , "pass123");
            conn.setAutoCommit(false);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return conn;
    }

    /*
     * Insert a player into the database
     */
    public Player insertPlayer(Player player) {

        try {

            conn = initiateConnection();
            conn.setAutoCommit(false);
            pstmt = conn.prepareStatement("insert into player(name,username,password) values(?,?,?)");
            pstmt.setString(1, player.getPlayerName());
            pstmt.setString(2, player.getUserName());
            pstmt.setString(3, player.getPassword());
            pstmt.executeUpdate();
            conn.commit();

        } catch (SQLException ex) {
            ex.printStackTrace();
            System.out.println("in connection" + ex);
        }
        return player;
    }

    /*
     * Get list of all players in the database
     */
    public List<Player> getPlayers() {
        List<Player> players = new ArrayList<>();
        try {
            conn = initiateConnection();
            stmt = conn.createStatement();
            rs = stmt.executeQuery("select * from player");
            Player player;
            while (rs.next()) {
                player = new Player();
                player.setId(rs.getInt(1));
                player.setPlayerName(rs.getString(2));
                player.setUserName(rs.getString(3));
                player.setPassword(rs.getString(4));
                players.add(player);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return players;
    }

    /*
     * Get player
     */
    public Player getPlayer(int id) {
        Player player = new Player();
        try {
            conn = initiateConnection();
            stmt = conn.createStatement();
            rs = stmt.executeQuery("select * from player where id=" + id);
            while (rs.next()) {

                player.setId(rs.getInt(1));
                player.setPlayerName(rs.getString(2));
                player.setUserName(rs.getString(3));
                player.setPassword(rs.getString(4));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return player;
    }

    /*
     * Method to check if the player exists in the database
     *
     * @param Player object with username and password set
     *
     * @return Player object with id, player name, username and password if player
     * is found and null if player not found
     */
    public Player validatePlayer(Player player) {
        boolean playerFound = false;
        try {

            conn = initiateConnection();
            stmt = conn.createStatement();
            rs = stmt.executeQuery("select * from player  where username='" + player.getUserName() + "' and password='"
                    + player.getPassword() + "'");
            while (rs.next()) {
                player = new Player();
                player.setId(rs.getInt(1));
                player.setPlayerName(rs.getString(2));
                player.setUserName(rs.getString(3));
                player.setPassword(rs.getString(4));
                playerFound = true;

            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return playerFound ? player : null;
    }

    /*
     * Insert a game score into the database
     */
    public GameScore insertGameScore(GameScore gameScore) {

        try {

            conn = initiateConnection();
            conn.setAutoCommit(false);
            pstmt = conn.prepareStatement("insert into game_score(wining_player_id, losing_player_id ,winning_score, losing_score) values(?,?,?,?)");
            pstmt.setInt(1, gameScore.getWinningPlayer().getId());
            pstmt.setInt(2, gameScore.getLosingPlayer().getId());
            pstmt.setInt(3, gameScore.getWinningPlayerScore());
            pstmt.setInt(4, gameScore.getLosingPlayerScore());
            pstmt.executeUpdate();
            conn.commit();

        } catch (SQLException ex) {
            ex.printStackTrace();
            System.out.println("in connection" + ex);
        }
        return gameScore;
    }

    /*
     * Get list of all game scores in the database
     */
    public List<GameScore> getGameScore() {
        List<GameScore> gameScores = new ArrayList<>();
        try {
            conn = initiateConnection();
            stmt = conn.createStatement();
            rs = stmt.executeQuery("select * from game_score");
            GameScore gameScore;
            while (rs.next()) {
                gameScore = new GameScore();
                gameScore.setId(rs.getInt(1));
                gameScore.setWinningPlayer(new Player(rs.getInt(2)));
                gameScore.setLosingPlayer(new Player(rs.getInt(3)));
                gameScore.setWinningPlayerScore(rs.getInt(4));
                gameScore.setLosingPlayerScore(rs.getInt(5));
                gameScores.add(gameScore);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        for (GameScore gameScore : gameScores) {
            gameScore.setWinningPlayer(getPlayer(gameScore.getWinningPlayer().getId()));
            gameScore.setLosingPlayer(getPlayer(gameScore.getLosingPlayer().getId()));
        }
        return gameScores;
    }

    public void dropTables() {
        try {
            conn = initiateConnection();
            stmt = conn.createStatement();
            stmt.execute("drop table game_score");

            conn.commit();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
