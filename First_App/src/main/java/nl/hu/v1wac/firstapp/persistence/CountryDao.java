package nl.hu.v1wac.firstapp.persistence;

import java.util.List;

public interface CountryDao {
	
	public Country save(Country country);
	public List<Country> findAll();
	public Country findByCode(String code);
	public List<Country> find10LargestPopulations();
	public List<Country> find10LargestSurfaces();
	public boolean update(Country country);
	public boolean delete(Country country);

}
