package com.example.mattias.gesaell;

import java.io.ByteArrayInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.PreparedStatement;

public class DBConnection {
	
	private Connection dbConnection = null;
	
	/**
	 * Konstruktor som skapar en databasanslutning
	 * @param url - url till databasen
	 * @param username - anv�ndarnamn till databasen
	 * @param password - l�senord till databasen
	 */
	public DBConnection(String url, String username, String password){
		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			dbConnection = DriverManager.getConnection(url, username, password);
		} catch (SQLException | InstantiationException | IllegalAccessException | ClassNotFoundException e) {
			e.printStackTrace();
		}
		
	}

    /**
     * Metod för att lägga tille ny användare i dartabasen
     * @param username önskat användarnamn som string.
     * @param password önskat lösenord som string
     * @param array användarens bild som byte []
     * @return true vid lyckad insättning, annars false.
     */
    protected boolean insertNewUser(String username, String password, byte[] array){

        try {
            String sql = "INSERT INTO User(Username, Password, Image) VALUES(?, ?, ?)";
            PreparedStatement statement = dbConnection.prepareStatement(sql);

            statement.setString(1, username);
            statement.setString(2, password);
            statement.setBinaryStream(3,new ByteArrayInputStream(array),array.length); // lägger till bild, dvsa col 2 värde
            statement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    /**
     * Metod för att försöka logga in, dvsa kolla ifall användarnamn och password är korrekt
     * @param username String som är användarnamn
     * @param password String som är lösenord
     * @return true ifall användarnamn och lösen matchade, annars false
     */
    protected boolean userLogin(String username, String password){

        try {
            String sql = "SELECT * FROM User WHERE Username = ? AND Password = ?";
            PreparedStatement statement = dbConnection.prepareStatement(sql);

            statement.setString(1, username);
            statement.setString(2, password);

            ResultSet resultSet = statement.executeQuery();

            if(resultSet.next()){
                return true;
            }


        }catch(Exception e){
            e.printStackTrace();
        }

        return false;
    }

    /**
     * Metod som hämtar bilde i form av byte arr från databas
     * @param username användarnamtet som bilden tillhör
     * @return returnerar byte arrayen vid succsess eller null vid fail
     */
    protected byte[] getPicture(String username){

        try {
            String sql = ("SELECT Image FROM User WHERE Username = ?");
            PreparedStatement statement = dbConnection.prepareStatement(sql);
            statement.setString(1, username);

            ResultSet resultSet = statement.executeQuery();
            if(resultSet.next()){
                byte[] bytes = resultSet.getBytes(1);
                return bytes;
            }

        }catch(Exception e){
            e.printStackTrace();
        }

        return null;
    }
	

}
