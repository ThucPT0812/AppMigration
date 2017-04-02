package sevensoft.appmigration.object;

/**
 * Created by STJHN on 15/12/2016.
 */

public class UserAnswers {

	private int questionID;
	private String answers;
	private int point;
	private String question;
	private int position ;

	public UserAnswers(){}

	public UserAnswers(int questionID, String answers, int point, String question, int position){
		this.questionID = questionID;
		this.answers = answers;
		this.point = point;
		this.question = question;
		this.position = position;
	}

	public void setQuestionID(int questionID){
		this.questionID = questionID;
	}

	public int getQuestionID(){
		return this.questionID;
	}

	public void setAnswers(String answers){
		this.answers = answers;
	}

	public String getAnswers(){
		return this.answers;
	}

	public void setPoint(int point){
		this.point = point;
	}

	public int getPoint(){
		return this.point;
	}

	public void setQuestion(String question){
		this.question = question;
	}

	public String getQuestion(){
		return  this.question;
	}

	public void setPosition(int position){
		this.position = position;
	}

	public int getPosition(){
		return this.position;
	}
}
