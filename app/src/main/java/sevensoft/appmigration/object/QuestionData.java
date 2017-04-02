package sevensoft.appmigration.object;

/**
 * Created by STJHN on 15/12/2016.
 */

public class QuestionData {

	private int ID;
	private String name;
	private String content;

	public QuestionData(){};

	public QuestionData(int ID, String name, String content){
		this.ID = ID;
		this.name = name;
		this.content = content;
	}

	public void setID(int ID){
		this.ID = ID;
	}

	public int getID(){
		return this.ID;
	}

	public void setName(String name){
		this.name = name;
	}

	public String getName(){
		return this.name;
	}

	public void setContent(String content){
		this.content = content;
	}

	public String getContent(){
		return this.content;
	}
}
