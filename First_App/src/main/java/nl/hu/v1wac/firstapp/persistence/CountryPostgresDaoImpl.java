package nl.hu.v1wac.firstapp.persistence;

import java.sql.*;
import java.util.*;

public class CountryPostgresDaoImpl extends PostgresBaseDao implements CountryDao {

	private List<Country> putInList(ResultSet resultSet) {
		List<Country> list = new ArrayList<Country>();
		try {
			while (resultSet.next()) {
				String code = resultSet.getString("code");
				String iso3 = resultSet.getString("iso3");
				String name = resultSet.getString("name");
				String capital = resultSet.getString("capital");
				String continent = resultSet.getString("continent");				
				String region = resultSet.getString("region");
				Double surface = resultSet.getDouble("surfacearea");
				int population = resultSet.getInt("population");
				String government = resultSet.getString("governmentform");
				Double lat = resultSet.getDouble("latitude");
				Double lon = resultSet.getDouble("longitude");
				
				Country newCountry = new Country(code, iso3, name, capital, continent, region, surface, population, government, lat, lon);
				
				list.add(newCountry);
			}
			return list;
		} catch (SQLException e){
	        System.out.println(e.getMessage());
	        return null;
		}	
	}

	public boolean save(Country country) {
		try {
			Connection conn = getConnection();
			String sql = "INSERT INTO country(code, name, continent, region, surface, population, governmentform) VALUES(?,?,?,?,?,?,?)";
			PreparedStatement preparedStatement = conn.prepareStatement(sql);
			preparedStatement.setString(1, country.getCode());
			preparedStatement.setString(2, country.getName());
			preparedStatement.setString(3, country.getContinent());
			preparedStatement.setString(4, country.getRegion());
			preparedStatement.setDouble(5, country.getSurface());
			preparedStatement.setInt(6, country.getPopulation());
			preparedStatement.setString(7, country.getGovernment());
			preparedStatement.executeUpdate();
			preparedStatement.close();
			return true;
		} catch (SQLException e){
            System.out.println(e);
            return false;
        }
	}
	
	public List<Country> findAll() {
		try {
			Connection conn = super.getConnection();
			String sql = "SELECT * FROM country ORDER BY Name";
			Statement statement = conn.createStatement();
			ResultSet resultSet= statement.executeQuery(sql);
			return putInList(resultSet);		
	} catch (SQLException e){
        System.out.println(e.getMessage());
        return null;
	}
}
	
	public List<Country> find10LargestSurfaces() {
		try {
			Connection conn = super.getConnection();
			String sql = "SELECT * FROM country ORDER BY surfacearea DESC LIMIT 10";
			Statement statement = conn.createStatement();
			ResultSet resultSet= statement.executeQuery(sql);;
			return putInList(resultSet);		
	} catch (SQLException e){
        System.out.println(e.getMessage());
        return null;
		}
	}
	
	public List<Country> find10LargestPopulations() {
		try {
			Connection conn = super.getConnection();
			String sql = "SELECT * FROM country ORDER BY population DESC LIMIT 10";
			Statement statement = conn.createStatement();
			ResultSet resultSet= statement.executeQuery(sql);
			return putInList(resultSet);		
	} catch (SQLException e){
        System.out.println(e.getMessage());
        return null;
		}
	}
	
	public Country findByCode(String code) {
		try {
			Connection conn = getConnection();
			String sql = "SELECT * FROM country WHERE code = '" + code + "'";
			Statement statement = conn.createStatement();
			ResultSet resultSet = statement.executeQuery(sql);
			Country country = null;
			while (resultSet.next()) {
				country = new Country(
						resultSet.getString("code"),
						resultSet.getString("iso3"),
						resultSet.getString("name"),
						resultSet.getString("capital"),
						resultSet.getString("continent"),
						resultSet.getString("region"),
						resultSet.getDouble("surfacearea"),
						resultSet.getInt("population"),
						resultSet.getString("governmentform"),
						resultSet.getDouble("latitude"),
						resultSet.getDouble("longitude")
						);
			}
			return country;
		} catch (SQLException e){
	        System.out.println(e.getMessage());
	        return null;
		}
	}
	
	
	public boolean update(Country country) {
		try {
			Connection conn = getConnection();
			String sql = "Update country "
	 				+ "SET name = ?,"
	 				+ "continent = ?,"
	 				+ "region = ?,"
	 				+ "surfacearea = ?,"
	 				+ "population = ?,"
	 				+ "governmentform = ?"
	 				+ "WHERE code = ?";
			PreparedStatement preparedStatement = conn.prepareStatement(sql);
			preparedStatement.setString(1, country.getName());
			preparedStatement.setString(2, country.getContinent());
			preparedStatement.setString(3, country.getRegion());
			preparedStatement.setDouble(4, country.getSurface());
			preparedStatement.setInt(5, country.getPopulation());
			preparedStatement.setString(6, country.getGovernment());
			preparedStatement.setString(7, country.getCode());
			preparedStatement.executeUpdate();
			preparedStatement.close();
			return true;			
		} catch(SQLException e) {
    		System.out.println(e.getMessage());
    		return false;
    	}
	}
	
	public boolean delete(Country country) {
		boolean result = false;
		boolean countryExists = findByCode(country.getCode()) != null;
		try {
			if (countryExists) {
				 Connection conn = getConnection();
	      	        Statement statement = conn.createStatement();
	      	        String sql = "DELETE FROM country WHERE code = '" + country.getCode() +"'";
	      	        statement.executeUpdate(sql);
	      	        result = true;
			}	
      	} catch(SQLException e) {
      		System.out.println(e.getMessage());
      		result = false;
      	} 
		return result;
      }
  }

