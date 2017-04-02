package sevensoft.appmigration.screen;

import android.os.Bundle;

import sevensoft.appmigration.R;
import sevensoft.appmigration.object.ActivityMigration;
import sevensoft.appmigration.util.Save;

/**
 * Created by STJHN on 2016/12/22.
 */

public class About extends ActivityMigration {


	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		init();
		setContentView(R.layout.l_about);

		initActivity();
		Save.currentActivity = this;
		Save.posActivity = 0;
		Save.listActivity[Save.posActivity] = this;
		setupContent();
		setUpListener();
	}

	public void init() {
		Save.currentActivity = null;
		Save.listActivity = new ActivityMigration[100];
		Save.posActivity = 0;

	}

	@Override
	public void setupContent() {
		super.setupContent();
	}

	public void setUpListener(){

	}

	@Override
	protected void onResume() {
		super.onResume();

		Save.currentActivity = this;
		Save.posActivity = 0;
		Save.listActivity[Save.posActivity] = this;

	}

	@Override
	protected void onPause() {
		super.onPause();
	}

	@Override
	public void finish() {
		init();
		System.exit(-1);
		super.finish();
	}

	@Override
	protected void onRestart() {
		super.onRestart();
	}
}
