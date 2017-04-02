package sevensoft.appmigration.screen;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import sevensoft.appmigration.R;
import sevensoft.appmigration.object.ActivityMigration;
import sevensoft.appmigration.object.InsertDataServer;
import sevensoft.appmigration.object.SendMailPHP;
import sevensoft.appmigration.object.SqlLiteDbHelper;
import sevensoft.appmigration.util.Save;

/**
 * Created by TrungThuc on 12/12/2016.
 */

public class Total extends ActivityMigration {

	private TextView total_text_point, total_messeage_point, dialog_tv_message;
	private String message = "";
	private Button total_send, total_dialog_popup_btn_cancel, total_dialog_popup_btn_ok, total_back;
	private String messageSend = "";
	private String messageTotal = "";
	private SqlLiteDbHelper dbHelper;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.l_total);
		initActivity();
		Save.currentActivity =this;
		Save.posActivity ++;
		Save.listActivity[Save.posActivity] = this;
		setupContent();
		setUpListener();
	}

	@Override
	public void setupContent() {
		super.setupContent();
		total_text_point = (TextView)findViewById(R.id.total_text_point);
		total_messeage_point = (TextView)findViewById(R.id.total_messeage_point);
		dialog_tv_message = (TextView)findViewById(R.id.dialog_tv_messeage);
		setupContentMessage();
		total_text_point.setText(messageTotal);
		total_messeage_point.setText(message);
		total_send = (Button)findViewById(R.id.total_send);
		total_dialog_popup_btn_cancel = (Button)findViewById(R.id.total_dialog_popup_btn_cancel);
		total_dialog_popup_btn_ok = (Button)findViewById(R.id.total_dialog_popup_btn_ok);
		total_back = (Button)findViewById(R.id.total_back);
		dialog_tv_message.setText(messageSend);
	}

	private void setupContentMessage(){
		int point = Save.user.getTotal();
//		messageTotal += "TOTAL POINT: " + String.valueOf(point + " to " + String.valueOf((point + 10)));
		dbHelper = new SqlLiteDbHelper(Save.currentActivity);
		dbHelper.openDataBase();

		String content = Save.user.getOccupation();
		int position = content.indexOf(" ");
		String occupationCode = content.substring(0, position);
		String occupationName = content.substring(position +1);

		boolean checkCSOL = dbHelper.checkOccupationCSOL(occupationCode, occupationName);
		messageTotal ="You may get: \n";
		if(!checkCSOL) {
			messageTotal +=String.valueOf(point) +  " points for 189 visa \n";
		}
		messageTotal +=String.valueOf(point + 5) +  " points for 190 visa & \n";
		messageTotal +=String.valueOf(point + 10) +  " points for 489 visa.";

		if(point >= 60){
			if(!checkCSOL){
				message = "Congratulations! It seems like you may be eligible for all of the following visa: 189, 190 or 489.\n";
			}else{
				message = "Congratulations! It seems like you may be eligible for all of the following visa: 190 or 489.\n";
			}

			message += "WHAT'S NEXT?\n";
			message += "You need at least a total score of 60 points to be eligible for a subclass 189, 190 or 489 visa. If you are interested in applying for these visas, please visit our website at http://ozskilledmigration.com for a step-by-step guide to do-it-yourself or contact us at ozskilledmigration@gmail.com to receive assistance from our registered migration agents ";
			messageSend = "";
		}else if(point >= 55 ){
			message = "Congratulations! It seems like you may be eligible for a subclass 190 or 489 visa.\n";
			message += "WHAT'S NEXT?\n";
			message += "You need at least a total score of 60 points to be eligible for a subclass 189, 190 or 489 visa. If you are interested in applying for these visas, please visit our website at http://ozskilledmigration.com for a step-by-step guide to do-it-yourself or contact us at ozskilledmigration@gmail.com to receive assistance from our registered migration agents ";
			messageSend = "";
		}else if(point >= 50){
			message = "Congratulations! It seems like you may be eligible for a subclass 489 visa.\n";
			message += "WHAT'S NEXT?\n";
			message += "You need at least a total score of 60 points to be eligible for a subclass 189, 190 or 489 visa. If you are interested in applying for these visas, please visit our website at http://ozskilledmigration.com for a step-by-step guide to do-it-yourself or contact us at ozskilledmigration@gmail.com to receive assistance from our registered migration agents ";
			messageSend += "";
		}else{
			message = "Unfortunately, it seems like you may not be eligible for any of the visa subclasses 189, 190 or 489.\n" +
					"\n" +
					"Please contact a migration agent/lawyer to explore other visa options.";
			messageSend = "You need at least a total score of 60 points to be eligible for a subclass 189, 190 or 489 visa. If you are interested in other visa options, please contact us at ozskilledmigration@gmail.com to receive assistance from our registered migration agents.";
		}
	}

	public void setUpListener() {
		total_send.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				findViewById(R.id.total_dialog_popup).setVisibility(View.VISIBLE);
			}
		});

		total_dialog_popup_btn_cancel.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				findViewById(R.id.total_dialog_popup).setVisibility(View.INVISIBLE);
			}
		});

		total_dialog_popup_btn_ok.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				if(Save.user.getTotal() >= 50){
					Toast.makeText(Save.currentActivity, "Sent", Toast.LENGTH_LONG).show();
					new InsertDataServer(Save.currentActivity).execute(Save.user.getName(), Save.user.getEmail(), Save.user.getCountry(), Save.user.getPhone(), Save.user.getOccupation(), String.valueOf(Save.user.getTotal()));
					new SendMailPHP(Save.currentActivity).execute(Save.user.getEmail(), message, String.valueOf(Save.user.getTotal()));

				}
				findViewById(R.id.total_dialog_popup).setVisibility(View.INVISIBLE);
			}
		});

		total_back.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				Intent intentHome = new Intent(Save.currentActivity, Home.class);
				Save.user.setName("");
				Save.user.setEmail("");
				Save.user.setOccupation("");
				Save.user.setTotal(0);
				Save.user.setCountry("");
				Save.user.setPhone("");
				intentHome.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				startActivity(intentHome);
			}
		});
	}

	public void closeDialogTotalMessage(View v){
		findViewById(R.id.total_dialog_popup).setVisibility(View.INVISIBLE);
	}

	@Override
	protected void onResume() {
		super.onResume();
	}

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
}