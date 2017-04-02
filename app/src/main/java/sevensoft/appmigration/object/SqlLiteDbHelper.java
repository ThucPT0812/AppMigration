package sevensoft.appmigration.object;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;

/**
 * Created by STJHN on 13/12/2016.
 */

public class SqlLiteDbHelper extends SQLiteOpenHelper {

	private static final int DATABASE_VERSION = 1;
	private static final String DATABASE_NAME = "migration.sqlite";
	private static final String DB_PATH_SUFFIX = "/databases/";
	static Context ctx;

	public SqlLiteDbHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
		ctx = context;
	}

	public void CopyDataBaseFromAsset() throws IOException {

		InputStream myInput = ctx.getAssets().open(DATABASE_NAME);

		// Path to the just created empty db
		String outFileName = getDatabasePath();

		// if the path doesn't exist first, create it
		File f = new File(ctx.getApplicationInfo().dataDir + DB_PATH_SUFFIX);
		if (!f.exists())
			f.mkdir();

		// Open the empty db as the output stream
		OutputStream myOutput = new FileOutputStream(outFileName);

		// transfer bytes from the inputfile to the outputfile
		byte[] buffer = new byte[1024];
		int length;
		while ((length = myInput.read(buffer)) > 0) {
			myOutput.write(buffer, 0, length);
		}

		// Close the streams
		myOutput.flush();
		myOutput.close();
		myInput.close();

	}

	public boolean checkOccupation(String occupationCode, String occupationName){
		boolean _check = false;
		SQLiteDatabase  db = this.getReadableDatabase();
		Cursor cursor = db.rawQuery("SELECT  count(*)   FROM occupation  WHERE occupationcode LIKE '%" + occupationCode + "%' AND occupationname LIKE '%"+ occupationName +"%' AND SOL = 1", null);
		if(cursor != null && cursor.moveToFirst()){
			int countCheck = cursor.getInt(0);
			if(countCheck > 0){
				_check = true;
			}
			cursor.close();
			db.close();
		}

		return _check;
	}

	public boolean checkOccupationCSOL(String occupationCode, String occupationName){
		boolean _check = false;
		SQLiteDatabase  db = this.getReadableDatabase();
		Cursor cursor = db.rawQuery("SELECT  count(*)   FROM occupation  WHERE occupationcode LIKE '%" + occupationCode + "%' AND occupationname LIKE '%"+ occupationName +"%' AND SOL = 0 AND CSOL = 1 ", null);
		if(cursor != null && cursor.moveToFirst()){
			int countCheck = cursor.getInt(0);
			if(countCheck > 0){
				_check = true;
			}
			cursor.close();
			db.close();
		}

		return _check;
	}

	public ArrayList<Occupation> getOccupation(){

		ArrayList<Occupation> arrOccupation = new ArrayList<Occupation>();
		SQLiteDatabase db = this.getReadableDatabase();

		Cursor cursor = db.rawQuery("SELECT * FROM occupation", null);
		if(cursor != null && cursor.moveToFirst()){
			while(cursor.isAfterLast() == false){
				Occupation occupation = new Occupation(cursor.getString(1), cursor.getString(2));
				arrOccupation.add(occupation);
				cursor.moveToNext();
			}
			cursor.close();
			db.close();
		}
		return arrOccupation;
	}

	public Country getCountry(String countryName){

		SQLiteDatabase db = this.getReadableDatabase();

		Cursor cursor = db.rawQuery("SELECT * FROM country WHERE CountryName LIKE '%" + countryName + "%'", null);
		if(cursor != null && cursor.moveToFirst()){

			Country country = new Country(cursor.getString(1), cursor.getString(2));
			cursor.close();
			db.close();
			return  country;
		}
		return null;
	}

	public ArrayList<Country> getAllCountry(){
		ArrayList<Country> arrCountry = new ArrayList<Country>();
		SQLiteDatabase db = this.getReadableDatabase();

		Cursor cursor = db.rawQuery("SELECT * FROM country ORDER BY CountryName", null);
		if(cursor != null && cursor.moveToFirst()){
			while(cursor.isAfterLast() == false){
				Country country = new Country(cursor.getString(1), cursor.getString(2));
				arrCountry.add(country);
				cursor.moveToNext();
			}
			cursor.close();
			db.close();
		}

		return arrCountry;
	}

	public ArrayList<Answers> getAnswers(int questionID, String where){
		ArrayList<Answers> arrAnswers = new ArrayList<Answers>();
		SQLiteDatabase db = this.getReadableDatabase();

		Cursor cursor = db.rawQuery("SELECT * FROM answers WHERE questionID = " + questionID + where, null);
		if(cursor != null && cursor.moveToFirst()){
			while (cursor.isAfterLast() == false){
				Answers answers = new Answers(cursor.getInt(1), cursor.getString(2), cursor.getInt(3));
				arrAnswers.add(answers);
				cursor.moveToNext();
			}
			cursor.close();
			db.close();
		}
		return arrAnswers;
	}

	public QuestionData getQuestion(int questionID){

		SQLiteDatabase db = this.getReadableDatabase();

		Cursor cursor = db.rawQuery("SELECT * FROM questions WHERE ID = " + questionID, null );
		if(cursor != null && cursor.moveToFirst()){

			QuestionData question = new QuestionData(cursor.getInt(0), cursor.getString(1), cursor.getString(2));
			cursor.close();
			db.close();
			return question;
		}
		return null;
	}

	private static String getDatabasePath() {
		return ctx.getApplicationInfo().dataDir + DB_PATH_SUFFIX
				+ DATABASE_NAME;
	}

	public SQLiteDatabase openDataBase() throws SQLException {
		File dbFile = ctx.getDatabasePath(DATABASE_NAME);

		if (!dbFile.exists()) {
			try {
				CopyDataBaseFromAsset();
				System.out.println("Copying sucess from Assets folder");
			} catch (IOException e) {
				throw new RuntimeException("Error creating source database", e);
			}
		}

		return SQLiteDatabase.openDatabase(dbFile.getPath(), null, SQLiteDatabase.NO_LOCALIZED_COLLATORS | SQLiteDatabase.CREATE_IF_NECESSARY);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {


	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub

	}
}
