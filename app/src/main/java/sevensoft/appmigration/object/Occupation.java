package sevensoft.appmigration.object;

/**
 * Created by STJHN on 13/12/2016.
 */

public class Occupation {

	private String occupationCode;
	private String occupationName;

	public void setOccupationCode(String occupationCode){
		this.occupationCode = occupationCode;
	}

	public String getOccupationCode(){
		return this.occupationCode;
	}

	public void setOccupationName(String occupationName){
		this.occupationName = occupationName;
	}

	public String getOccupationName(){
		return this.occupationName;
	}

	public Occupation(){

	}

	public Occupation(String occupationCode, String occupationName){
		this.occupationCode = occupationCode;
		this.occupationName = occupationName;
	}
}

