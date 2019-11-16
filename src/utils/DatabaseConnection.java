package utils;

import entity.Player;
import org.apache.derby.jdbc.EmbeddedDriver;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

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
    Test cases for database connection , insert, fetch and validate player
     */
    public static void main(String[] args) {
        DatabaseConnection connection=new DatabaseConnection();
        Player player= new Player();
        player.setPassword("123");
        player.setPlayerName("Test Player");
        player.setUserName("testPlayer");
        //connection.insertPlayer(player);
        for (Player player1:connection.getPlayers()){
            System.out.println(player1.getUserName());
        }
        Player validatePlayer= connection.validatePlayer(player);
        System.out.println(validatePlayer.getPlayerName());
    }

    public DatabaseConnection(){
       //dropTables();
      // createTables();
    }
    /*
    To create the tables in the initilization of the game, should be executed only once while installing or running
     */
    public void createTables(){
        String createSQL = "create table player ("
                + "id integer not null generated always as"
                + " identity (start with 1, increment by 1),   "
                + "name varchar(30) not null, username varchar(30),"
                +"password varchar(100),"
                + "constraint primary_key_player primary key (id))";
        try {
            conn = initiateConnection();
            stmt = conn.createStatement();
            stmt.execute(createSQL);
            conn.commit();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public void stopConnection(){
        try {
            DriverManager.getConnection
                    ("jdbc:derby:;shutdown=true");
        } catch (SQLException ex) {
            if (((ex.getErrorCode() == 50000) &&
                    ("XJ015".equals(ex.getSQLState())))) {
                System.out.println("Derby shut down normally");
            } else {
                System.err.println("Derby did not shut down normally");
                System.err.println(ex.getMessage());
            }
        }
    }

    public Connection initiateConnection(){
        try {
            Driver derbyEmbeddedDriver = new EmbeddedDriver();
            DriverManager.registerDriver(derbyEmbeddedDriver);
            conn = DriverManager.getConnection
                    ("jdbc:derby:testdb1;create=true");//, "pass123");
            conn.setAutoCommit(false);
        }catch (SQLException e){
            e.printStackTrace();
        }
        return conn;
    }
    /*
    Insert a player into the database
     */
    public Player insertPlayer(Player player){

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
            System.out.println("in connection" + ex);
        }
        return player;
    }

    /*
    Get list of all players in the database
     */
    public List<Player> getPlayers(){
        List<Player> players = new ArrayList<>();
        try {
            conn = initiateConnection();
            stmt = conn.createStatement();
            rs = stmt.executeQuery("select * from player");
            Player player;
            while (rs.next()) {
                player=new Player();
                player.setId(rs.getInt(1));
                player.setPlayerName(rs.getString(2));
                player.setUserName(rs.getString(3));
                player.setPassword(rs.getString(4));
                players.add(player);
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return players;
    }

    /*
    Method to check if the player exists in the database
    @param Player object with username and password set
    @return Player object with id, player name, username and password if player is found and null if player not found
     */
    public Player validatePlayer(Player player){
        boolean playerFound=false;
        try {

            conn = initiateConnection();
            stmt = conn.createStatement();
            rs = stmt.executeQuery("select * from player  where username='"+player.getUserName()+
                    "' and password='"+player.getPassword()+"'");
            while (rs.next()) {
                player=new Player();
                player.setId(rs.getInt(1));
                player.setPlayerName(rs.getString(2));
                player.setUserName(rs.getString(3));
                player.setPassword(rs.getString(4));
                playerFound=true;

            }
        }catch (SQLException e){
            e.printStackTrace();
        } catch (Exception ex){
            ex.printStackTrace();
        }
        return playerFound?player:null;
    }

    public void dropTables(){
        try {
            conn = initiateConnection();
            stmt = conn.createStatement();
            stmt.execute("drop table player");

            conn.commit();
        }catch (SQLException e){
            e.printStackTrace();
        }
        catch (Exception e){
            e.printStackTrace();
        }

    }
}

