package sevensoft.appmigration.screen;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import sevensoft.appmigration.R;
import sevensoft.appmigration.object.ActivityMigration;
import sevensoft.appmigration.object.Answers;
import sevensoft.appmigration.object.Occupation;
import sevensoft.appmigration.object.QuestionData;
import sevensoft.appmigration.object.SqlLiteDbHelper;
import sevensoft.appmigration.object.UserAnswers;
import sevensoft.appmigration.util.Save;

/**
 * Created by TrungThuc on 12/12/2016.
 */

public class Question extends ActivityMigration {

	Button question_btn_next;
	Spinner question_edt_answer;
	TextView question_tv_question;
	int questionID = 0;
	QuestionData questionData = new QuestionData();
	ArrayList<Answers> arrAnswers = new ArrayList<Answers>();
	ArrayList<UserAnswers> arrUserAnswers = new ArrayList<UserAnswers>();
	SqlLiteDbHelper dbHelper;
	Button question_btn_back;
	ImageView question_image_question;
	int checkQuestion6, checkQuestion7, checkQuestion8 = 0;
	int checkQuestion12, postCode = 0;
	int checkQuestion3 = 0;
	EditText question_postcode;
	AutoCompleteTextView question_edt_work ;
	ArrayList<Occupation> arrOccupation = new ArrayList<Occupation>();
	String[] arrWork ;
	CustomSpinnerAdapter customSpinnerAdapter;
	Animation animLeft, animRight;
	boolean clickNext = true;
	boolean clickBack = false;
	Button question_btn_point;
	CheckBox question_chk_not_remember;
	Button question_dialog_btn_ok;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.l_question);

		initActivity();
		Save.currentActivity = this;
		Save.posActivity ++;
		Save.listActivity[Save.posActivity] = this;
		setupContent();
		setUpListener();
	}

	@Override
	public void setupContent() {
		super.setupContent();

		question_btn_next = (Button)findViewById(R.id.question_btn_next);
		question_edt_answer = (Spinner)findViewById(R.id.question_edt_answer);
		question_tv_question = (TextView)findViewById(R.id.question_tv_question);
		question_btn_back = (Button)findViewById(R.id.question_btn_back);
		question_image_question = (ImageView)findViewById(R.id.question_image_question);
		question_postcode = (EditText)findViewById(R.id.question_postcode);
		question_postcode.setVisibility(View.GONE);
		question_edt_work = (AutoCompleteTextView)findViewById(R.id.question_edt_work);
		question_btn_point = (Button)findViewById(R.id.question_btn_point);
		setDataAdapterAutoComplete();
		questionID = 0;
		questionID ++;
		animLeft = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_in_left);
		animRight = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_in_right);
		question_chk_not_remember = (CheckBox)findViewById(R.id.question_chk_not_remember);
		question_chk_not_remember.setVisibility(View.GONE);
		question_chk_not_remember.setChecked(false);
		question_dialog_btn_ok = (Button)findViewById(R.id.question_dialog_btn_ok);

		fillQuestion(questionID);
		initAnswerSpinnerFirst(questionID);
		question_btn_back.setVisibility(View.VISIBLE);
		question_image_question.setVisibility(View.INVISIBLE);
	}

	public void setUpListener(){
		question_btn_next.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				clickBack = false;
				clickNext = true;
				if(questionID == 14){

				}else{
					questionID ++;
				}

				if(questionID == 13 && checkQuestion12 == 1){
					questionID = 14;
				}

				if(questionID == 3){
					if(Save.user.getOccupation().compareTo("") == 0 || Save.user.getOccupation().compareTo("Not yet working") == 0 || Save.user.getOccupation().compareTo("My occupation is not in this list") == 0 || Save.user.getOccupation().compareTo("Unemployed") == 0){
						questionID = 5;
					}else{
						questionID = 3;
					}
				}

				if(questionID == 4 && checkQuestion3 == 1){
					questionID = 41;
				}else if(questionID == 4 && checkQuestion3 == 2){
					questionID = 42;
				}

				if(questionID == 43){
					questionID = 5;
				}
				if(questionID == 7 && checkQuestion6 == 2){
					questionID = 10;
				}
				if(questionID == 8 && checkQuestion7 == 1){
					questionID = 10;
				}

				if(questionID == 9 && checkQuestion8 == 2){
					questionID = 10;
				}

				if(questionID == 11){
					if(checkQuestion7 == 1 || checkQuestion6 == 2){
						questionID = 12;
					}
				}

				if(questionID == 11){
					initAnswerQuestion11(11);
				}
				question_btn_back.setVisibility(View.VISIBLE);
				handlerQuestion();
			}
		});

		question_btn_back.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {

				clickBack = true;
				clickNext = false;
				if(questionID == 1){
					finish();
				}
				if(questionID > 0 && questionID != 5){
					questionID --;
				}else if(questionID == 5){
					if(Save.user.getOccupation().compareTo("") == 0 || Save.user.getOccupation().compareTo("Not yet working") == 0){
						questionID = 2;
					}else{
						questionID = 42;
					}
				}

				if(questionID == 41 && checkQuestion3 ==2){
					questionID = 3;
				}

				if(questionID == 40){
					questionID = 3;
				}

				if(questionID == 9 && checkQuestion8 ==2){
					questionID = 8;
				}

				if (questionID == 9 && checkQuestion7  ==1){
					questionID = 7;
				}

				if(questionID == 9 && checkQuestion6 == 2){
					questionID =6;
				}

				if(questionID == 13 && checkQuestion12 == 1){
					questionID = 11;
				}

				if(questionID == 13 && checkQuestion12 == 2){
					questionID = 13;
				}

				if(questionID == 11){
					if(checkQuestion7 == 1 || checkQuestion6 == 2){
						questionID = 10;
					}
				}

				if(arrUserAnswers.size() > 0){
					arrUserAnswers.remove(arrUserAnswers.size() - 1);
				}

				handlerQuestion();
				int positionArr = 0;
				if(arrUserAnswers.size() > 0){
					positionArr = arrUserAnswers.get(arrUserAnswers.size() - 1).getPosition();
				}
				question_edt_answer.setSelection(positionArr);
			}
		});

		question_image_question.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				findViewById(R.id.question_dialog).setVisibility(View.VISIBLE);
			}
		});

		question_dialog_btn_ok.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				findViewById(R.id.question_dialog).setVisibility(View.GONE);
			}
		});

		question_chk_not_remember.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				if (((CheckBox) view).isChecked()) {
					question_postcode.setVisibility(View.GONE);
				}else{
					question_postcode.setVisibility(View.VISIBLE);
				}
			}
		});
	}

	public void setDataAdapterAutoComplete(){
		dbHelper = new SqlLiteDbHelper(Save.currentActivity);
		dbHelper.openDataBase();
		arrOccupation = dbHelper.getOccupation();
		Occupation occupation = new Occupation("", "Not yet working");
		arrOccupation.add(occupation);

		Occupation occupation1 = new Occupation("", "Unemployed");
		arrOccupation.add(occupation1);
		arrWork = new String[arrOccupation.size()];

		for(int i = 0; i < arrOccupation.size(); i ++){
			arrWork[i] = arrOccupation.get(i).getOccupationCode() + " " + arrOccupation.get(i).getOccupationName();
		}

		question_edt_work.setThreshold(1);
		ArrayAdapter<String> adapterWork = new ArrayAdapter<String>(this,  R.layout.l_custom_item, R.id.autoCompleteItem, arrWork);
		question_edt_work.setAdapter(adapterWork);
	}

	public void fillQuestion(int questionID){
		dbHelper = new SqlLiteDbHelper(Save.currentActivity);
		dbHelper.openDataBase();
		String question = "";
		if(questionID == 41){
			questionData = dbHelper.getQuestion(4);
			question = questionData.getContent().replace("\\'", "'");
			int position = question.indexOf("\n");
			question = question.substring(0, position);
			question_tv_question.setText(question);
		}else if(questionID == 42){
			questionData = dbHelper.getQuestion(4);
			question = questionData.getContent().replace("\\'", "'");
			int position = question.indexOf("\n");
			question = question.substring(position+1);
			question_tv_question.setText(question);
		}else{
			questionData = dbHelper.getQuestion(questionID);
			question = questionData.getContent().replace("\\'", "'");
			question_tv_question.setText(question);
		}
		if(clickNext && !clickBack){
			question_tv_question.startAnimation(animRight);
		}else{
			question_tv_question.startAnimation(animLeft);
		}

	}

	public void initAnswerSpinnerFirst(final int questionID){

		dbHelper = new SqlLiteDbHelper(Save.currentActivity);
		dbHelper.openDataBase();
		if(questionID == 41){
			arrAnswers = dbHelper.getAnswers(4, " AND  isOutsideAustralia = 0");
		}else if(questionID == 42){
			arrAnswers = dbHelper.getAnswers(4, " AND  isOutsideAustralia = 1");
		}else{
			arrAnswers = dbHelper.getAnswers(questionID, "");
		}

		customSpinnerAdapter=new CustomSpinnerAdapter(Save.currentActivity,arrAnswers);
		customSpinnerAdapter.notifyDataSetChanged();
		question_edt_answer.setAdapter(customSpinnerAdapter);
		setSelectItemSpinner();

	}

	public void initAnswerSpinner(final int questionID){

		dbHelper = new SqlLiteDbHelper(Save.currentActivity);
		dbHelper.openDataBase();
		if(questionID == 41){
			arrAnswers = dbHelper.getAnswers(4, " AND  isOutsideAustralia = 0");
		}else if(questionID == 42){
			arrAnswers = dbHelper.getAnswers(4, " AND  isOutsideAustralia = 1");
		}else if(questionID == 5 || questionID == 9 || questionID == 10 ){
			arrAnswers = dbHelper.getAnswers(questionID, " ORDER BY points");
		}else if(questionID == 3 ){
			arrAnswers.clear();
			Answers answersYes = new Answers(3, "Yes", 0);
			arrAnswers.add(answersYes);
			Answers answersNo = new Answers(3, "No", 0);
			arrAnswers.add(answersNo);
		}
		else{
			arrAnswers = dbHelper.getAnswers(questionID, "");
		}

		CustomSpinnerAdapter customSpinnerAdapter=new CustomSpinnerAdapter(Save.currentActivity,arrAnswers);
		customSpinnerAdapter.notifyDataSetChanged();
		question_edt_answer.setAdapter(customSpinnerAdapter);

		setSelectItemSpinner();

	}

	public void setSelectItemSpinner(){
		question_edt_answer.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

				if(questionID == 41){
					handleArrayAnswersFor(position);
				}else if(questionID == 42){
					handleArrayAnswersFor(position);
				}else if(questionID == 6){
					handleArrayAnswers(position);
					checkQuestion6 = position + 1;
					if(checkQuestion6 == 2){
						checkQuestion7 =0;
						checkQuestion8 = 0;
					}
				}else if(questionID == 7){
					handleArrayAnswers(position);
					checkQuestion7 = position + 1;
					if(checkQuestion7 == 1){
						checkQuestion8 = 0 ;
					}
				}else if(questionID == 8){
					handleArrayAnswers(position);
					checkQuestion8 = position + 1;
				}else if(questionID == 12){
					handleArrayAnswers(position);
					checkQuestion12 = position + 1;
				}else if(questionID == 3){
					handleArrayAnswers(position);
					checkQuestion3 = position + 1;
				}
				else{
					handleArrayAnswers(position);
				}
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {

			}
		});
	}

	public void initAnswerQuestion11(final int questionID){

		dbHelper = new SqlLiteDbHelper(Save.currentActivity);
		dbHelper.openDataBase();
		if(questionID == 11){
			arrAnswers = dbHelper.getAnswers(11, " AND contentanswer <> 'No'");
		}

		String contentAnswers = arrAnswers.get(0).getContentAnswers();
		String[] arrContent = contentAnswers.split(",");

		if(question_postcode.getText().toString().compareTo("") == 0){
			postCode = 0;
		}else{
			postCode = Integer.parseInt(question_postcode.getText().toString());
		}

		int point  = 0;
		boolean checkPostcode = false;
		for (int i = 0; i <arrContent.length; i ++){
			String[] arrPostCode = arrContent[i].split("to");
			int from = Integer.parseInt(arrPostCode[0].trim());
			int to = Integer.parseInt(arrPostCode[1].trim());
			if((postCode >= from) && (postCode <= to) ){
				checkPostcode = true;
				break;
			}
		}

		if(checkPostcode){
			point = 5;
		}

		UserAnswers userAnswers = new UserAnswers(11, question_postcode.getText().toString(), point,  question_tv_question.getText().toString(), 0);
		boolean _check =false;
		int k = 0;
		for(int i = 0; i < arrUserAnswers.size(); i ++){
			if(arrUserAnswers.get(i).getQuestionID() == questionID){
				_check = true;
				k = i;
			}
		}
		if(_check){
			arrUserAnswers.set(k, userAnswers);
		}else{
			arrUserAnswers.add(userAnswers);
		}

	}

	public void initAnswerQuestion13(final int questionID){

		dbHelper = new SqlLiteDbHelper(Save.currentActivity);
		dbHelper.openDataBase();
		int point = 0;
		String content = question_edt_work.getText().toString();
		if(content.compareTo("") == 0){

		}else{
			int position = content.indexOf(" ");
			String occupationCode = content.substring(0, position);
			String occupationName = content.substring(position +1);
			if(questionID == 13){
				if(dbHelper.checkOccupation(occupationCode, occupationName)){
					point = 5;
				}
			}
		}

		UserAnswers userAnswers = new UserAnswers(13, question_edt_work.getText().toString(), point, question_tv_question.getText().toString(), 0);
		boolean _check =false;
		int k = 0;
		for(int i = 0; i < arrUserAnswers.size(); i ++){
			if(arrUserAnswers.get(i).getQuestionID() == questionID){
				_check = true;
				k = i;
			}
		}
		if(_check){
			arrUserAnswers.set(k, userAnswers);
		}else{
			arrUserAnswers.add(userAnswers);
		}

	}


	public void handleArrayAnswers(int position){
		Answers answers = arrAnswers.get(position);
		UserAnswers userAnswers = new UserAnswers(answers.getQuestionID(), answers.getContentAnswers(), answers.getPoint(),  question_tv_question.getText().toString(), position);
		boolean _check =false;
		int k = 0;
		for(int i = 0; i < arrUserAnswers.size(); i ++){
			if(arrUserAnswers.get(i).getQuestionID() == questionID){
				_check = true;
				k = i;
			}
		}
		if(_check){
			arrUserAnswers.set(k, userAnswers);
		}else{
			arrUserAnswers.add(userAnswers);
		}
		calTotalPoint();
	}

	public void handleArrayAnswersFor(int position){
		Answers answers = arrAnswers.get(position);
		UserAnswers userAnswers = new UserAnswers(questionID, answers.getContentAnswers(), answers.getPoint(),  question_tv_question.getText().toString(), position);
		boolean _check =false;
		int k = 0;
		for(int i = 0; i < arrUserAnswers.size(); i ++){
			if(arrUserAnswers.get(i).getQuestionID() == questionID){
				_check = true;
				k = i;
			}
		}
		if(_check){
			arrUserAnswers.set(k, userAnswers);
		}else{
			arrUserAnswers.add(userAnswers);
		}
		calTotalPoint();
	}



	public void handlerQuestion(){
		switch (questionID){
			case 0:
//				question_btn_back.setVisibility(View.INVISIBLE);
				question_image_question.setVisibility(View.INVISIBLE);
				break;
			case 1:
				fillQuestion(questionID);
				initAnswerSpinner(questionID );
//				question_btn_back.setVisibility(View.INVISIBLE);
				question_image_question.setVisibility(View.INVISIBLE);
				break;
			case 2:
				fillQuestion(questionID);
				initAnswerSpinner(questionID );
				question_image_question.setVisibility(View.VISIBLE);
				break;
			case 3:
				fillQuestion(questionID);
				initAnswerSpinner(questionID );
				question_image_question.setVisibility(View.INVISIBLE);
				break;
			case 4:
				break;
			case 41:
				question_image_question.setVisibility(View.INVISIBLE);
				fillQuestion(questionID);
				initAnswerSpinner(questionID );
				break;
			case 42:
				fillQuestion(questionID);
				initAnswerSpinner(questionID );
				break;
			case 5:
				question_image_question.setVisibility(View.INVISIBLE);
				fillQuestion(questionID);
				initAnswerSpinner(questionID );
				break;
			case 6:
				fillQuestion(questionID);
				initAnswerSpinner(questionID );
				break;
			case 7:
				fillQuestion(questionID);
				initAnswerSpinner(questionID );
				break;
			case 8:
				fillQuestion(questionID);
				initAnswerSpinner(questionID );
				break;
			case 9:
				fillQuestion(questionID);
				initAnswerSpinner(questionID );
				break;
			case 10:
				question_edt_answer.setVisibility(View.VISIBLE);
				question_postcode.setVisibility(View.GONE);
				question_chk_not_remember.setVisibility(View.GONE);
				fillQuestion(questionID);
				initAnswerSpinner(questionID );
				break;
			case 11:
				question_edt_answer.setVisibility(View.GONE);
				question_postcode.setVisibility(View.VISIBLE);
				question_chk_not_remember.setVisibility(View.VISIBLE);
				fillQuestion(questionID);
				break;
			case 12:
				question_edt_answer.setVisibility(View.VISIBLE);
				question_postcode.setVisibility(View.GONE);
				question_edt_work.setVisibility(View.GONE);
				question_chk_not_remember.setVisibility(View.GONE);
				fillQuestion(questionID);
				initAnswerSpinner(questionID );
				break;
			case 13:
				fillQuestion(questionID);
				question_edt_work.setVisibility(View.VISIBLE);
				question_edt_answer.setVisibility(View.GONE);
				break;
			case 14:
//				if(question_edt_work.getText().toString().compareTo("") != 0){
				initAnswerQuestion13(13);
				calTotalPoint();
//				}
				Intent totalIntent = new Intent(Save.currentActivity, Total.class);
				startActivity(totalIntent);
				break;
			default:
				break;
		}
	}

	public void calTotalPoint(){
		int totalPoint = 0;
		int pointQuestion4 = 0;
		for(int i = 0; i < arrUserAnswers.size(); i++){
			if(arrUserAnswers.get(i).getQuestionID() == 41 || arrUserAnswers.get(i).getQuestionID() == 42){
				pointQuestion4 += arrUserAnswers.get(i).getPoint();
			}else{
				totalPoint += arrUserAnswers.get(i).getPoint();
			}
		}

		if(pointQuestion4 > 20){
			pointQuestion4 = 20;
		}

		totalPoint += pointQuestion4;
		Save.user.setTotal(totalPoint);
		question_btn_point.setText(String.valueOf(Save.user.getTotal()));
	}

	public void closeDialogPopupQuestionDescription(View v){
		findViewById(R.id.question_dialog).setVisibility(View.GONE);
	}

//	@Override
//	protected void onResume() {
//		super.onResume();
//		questionID = 0;
//		questionID ++;
//		arrUserAnswers.clear();
//		arrAnswers.clear();
//		question_postcode.setText("");
//		question_edt_work.setText("");
//		question_edt_answer.setVisibility(View.VISIBLE);
//		question_postcode.setVisibility(View.GONE);
//		question_edt_work.setVisibility(View.GONE);
//		handlerQuestion();
//
//	}

	@Override
	protected void onPause() {
		super.onPause();
	}

	@Override
	public void finish() {
		try {
			Save.posActivity--;
			Save.currentActivity = Save.listActivity[Save.posActivity];
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
		} catch (Exception e) {
			e.printStackTrace();
		}
		super.finish();
	}

	@Override
	protected void onRestart() {
		super.onRestart();
	}

	public class CustomSpinnerAdapter extends BaseAdapter implements SpinnerAdapter {

		private final Activity activity;
		private ArrayList<Answers> asr;

		public CustomSpinnerAdapter(Activity context,ArrayList<Answers> asr) {
			this.asr=asr;
			activity = context;
		}

		public int getCount()
		{
			return asr.size();
		}

		public Object getItem(int i)
		{
			return asr.get(i);
		}

		public long getItemId(int i)
		{
			return (long)i;
		}

		@Override
		public View getDropDownView(int position, View convertView, ViewGroup parent) {
			View row = convertView;
			if(row == null){
				LayoutInflater inflater = activity.getLayoutInflater();
				row = inflater.inflate(R.layout.drop_down_row, parent, false);
			}

			TextView tv = (TextView)row.findViewById(R.id.spinner_row_text);
			tv.setText(asr.get(position).getContentAnswers());
			return  row;
		}

		public View getView(int i, View view, ViewGroup viewgroup) {
//			TextView txt = new TextView(Save.currentActivity);
//			txt.setGravity(Gravity.CENTER_VERTICAL);
//			int padding = (int)getResources().getDimension(R.dimen.layx6);
//			txt.setPadding(padding, padding, padding, padding);
//			int textSize = (int)getResources().getDimension(R.dimen.text_size_12);
//			txt.setTextSize(textSize);
//			txt.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_down, 0);
//			txt.setText(asr.get(i).getContentAnswers());
//			txt.setTextColor(Color.parseColor("#000000"));
//			return  txt;
			View row = view;
			if(row == null){
				LayoutInflater inflater = activity.getLayoutInflater();
				row = inflater.inflate(R.layout.l_row_view_spinner, viewgroup, false);
			}

			TextView tv = (TextView)row.findViewById(R.id.spinner_row_view_text);
			tv.setText(asr.get(i).getContentAnswers());
			tv.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_down, 0);
			return  row;
		}

	}
}
