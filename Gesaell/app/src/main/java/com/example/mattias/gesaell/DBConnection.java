package com.example.mattias.gesaell;

import java.io.ByteArrayInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
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
	 * Metod f�r att h�mta alla personer i databasen
	 * @return Str�ng som inneh�ller alla personer. 
	 */
	protected String getAllPersons(){
		
		Statement stat;
		String resstr = "Gästbok: \n\n";
		try {
			stat = dbConnection.createStatement();
			ResultSet res = stat.executeQuery("select * from Personer");
			while(res.next()){
				resstr +=("Namn: " + res.getString("Namn") + "\n");
				resstr +=("Epost: " + res.getString("Epost") + "\n");
				resstr +=("Hemsida: " + res.getString("Hemsida") + "\n");
				resstr +=("Komentar: " + res.getString("Kommentar") + "\n\n");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return resstr;
		
	}
	

    protected void insertPicture(int konto, byte[] array){

        try {
            String sql = "INSERT INTO Bild(Konto, Bild) VALUES(?, ?)";
            PreparedStatement statement = dbConnection.prepareStatement(sql);

            statement.setInt(1, konto);
            statement.setBinaryStream(2,new ByteArrayInputStream(array),array.length); // lägger till bild, dvsa col 2 värde
            statement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    protected byte[] getPicture(){

        try {
            Statement stmt = dbConnection.createStatement();
            ResultSet resultSet = stmt.executeQuery("SELECT * FROM Bild");
            resultSet.next();
            byte[] bytes = resultSet.getBytes("Bild");

            return bytes;

        }catch(Exception e){
            e.printStackTrace();
        }

        return null;
    }
	

}
