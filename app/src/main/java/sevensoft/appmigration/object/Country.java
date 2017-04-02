package sevensoft.appmigration.object;

/**
 * Created by STJHN on 2016/12/21.
 */

public class Country {

	private String countryCode ;
	private String countryName;

	public  Country(){};

	public Country(String countryCode, String countryName){
		this.countryCode = countryCode;
		this.countryName = countryName;
	}

	public void setCountryCode(String countryCode){
		this.countryCode = countryCode;
	}

	public String getCountryCode(){
		return this.countryCode;
	}

	public void setCountryName(String countryName){
		this.countryName = countryName;
	}

	public String getCountryName(){
		return this.countryName;
	}
}
