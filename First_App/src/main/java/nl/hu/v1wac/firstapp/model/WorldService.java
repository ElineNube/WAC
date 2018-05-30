package nl.hu.v1wac.firstapp.model;


import java.util.List;

import nl.hu.v1wac.firstapp.persistence.Country;
import nl.hu.v1wac.firstapp.persistence.CountryDao;
import nl.hu.v1wac.firstapp.persistence.CountryPostgresDaoImpl;

public class WorldService {
	private CountryDao countryDAO = new CountryPostgresDaoImpl();
	
	
	public List<Country> getAllCountries() {
		return countryDAO.findAll();
	}
	
	public List<Country> get10LargestPopulations() {
		return countryDAO.find10LargestPopulations();
	}

	public List<Country> get10LargestSurfaces() {
		return countryDAO.find10LargestSurfaces();
	}
	
	public Country getCountryByCode(String code) {
		return countryDAO.findByCode(code);
	}

	public boolean delete(Country country) {
		return countryDAO.delete(country);
	}
	
	public boolean update(Country country) {
		return countryDAO.update(country);
	}
}

