package sevensoft.appmigration.object;

/**
 * Created by STJHN on 15/12/2016.
 */

public class Answers {

	private int questionID;
	private String contentAnswers;
	private int point;

	public void setQuestionID(int questionID){
		this.questionID = questionID;
	}

	public int getQuestionID(){
		return questionID;
	}

	public void setContentAnswers(String contentAnswers){
		this.contentAnswers = contentAnswers;
	}

	public String getContentAnswers(){
		return this.contentAnswers;
	}

	public void setPoint(int point){
		this.point = point;
	}

	public int getPoint(){
		return this.point;
	}

	public Answers(){

	}

	public Answers(int questionID, String contentAnswers, int point){
		this.questionID = questionID;
		this.contentAnswers = contentAnswers;
		this.point = point;
	}
}
