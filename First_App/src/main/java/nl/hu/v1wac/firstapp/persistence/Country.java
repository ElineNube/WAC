package nl.hu.v1wac.firstapp.persistence;


	public class Country {
		private String code;
		private String iso3;
		private String name;
		private String capital;
		private String continent;
		private String region;
		private double surface;
		private int population;
		private String government;
		private double latitude;
		private double longitude;
		
		public Country(String code, String iso3, String nm, String cap, String ct, String reg, double sur, int pop, String gov, double lat, double lng) {
			this.code = code; 
			this.iso3 = iso3;
			this.name = nm;
			this.capital = cap;
			this.continent = ct;
			this.region = reg;
			this.surface = sur;
			this.population = pop;
			this.government = gov;
			this.latitude = lat;
			this.longitude = lng;
		}
		
		public Country(String code, String nm, String cap, String ct, String reg, double sur, int pop, String gov) {
			this.code = code; 
			this.iso3 = null;
			this.name = nm;
			this.capital = cap;
			this.continent = ct;
			this.region = reg;
			this.surface = sur;
			this.population = pop;
			this.government = gov;
			this.latitude = 0;
			this.longitude = 0;
		}
		
		public String getCode() {
			return code;
		}
		
		public String getIso3() {
			return iso3;
		}
		
		public String getName() {
			return name;
		}
		
		public void setName(String name) {
			this.name = name;
		}
		
		public String getCapital() {
			return capital;
		}
		
		public String getContinent() {
			return continent;
		}
		
		public void setCapital(String cap) {
			this.capital = cap;
		}
		
		public String getRegion() {
			return region;
		}
		
		public void setRegion(String reg) {
			this.region = reg;
		}
		
		public double getSurface() {
			return surface;
		}
		
		public void setSurface(double sur) {
			this.surface = sur;
		}
		
		public void setPopulation(int pop) {
			this.population = pop;
		}
		
		public int getPopulation() {
			return population;
		}
		
		public String getGovernment() {
			return government;
		}
		
		public double getLatitude() {
			return latitude;
		}
		
		public double getLongitude() {
			return longitude;
		}
	}


