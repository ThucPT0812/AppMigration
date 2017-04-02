package sevensoft.appmigration.object;

/**
 * Created by TrungThuc on 2016/12/20.
 */

import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

public class InsertDataServer extends AsyncTask<String, Void, String> {

	private Context context;

	public InsertDataServer(Context context){
		this.context = context;
	}

	@Override
	protected void onPreExecute() {
		super.onPreExecute();
	}

	@Override
	protected String doInBackground(String... strings) {
		String name = strings[0];
		String emailAddress = strings[1];
		String acc_country = strings[2];
		String acc_phone = strings[3];
		String acc_accupation = strings[4];
		String point = strings[5];

		String link;
		String data;
		BufferedReader bufferedReader;
		String result;

		try {
			data = "?acc_name=" + URLEncoder.encode(name, "UTF-8");
			data += "&acc_email=" + URLEncoder.encode(emailAddress, "UTF-8");
			data += "&acc_country=" + URLEncoder.encode(acc_country, "UTF-8");
			data += "&acc_phone=" + URLEncoder.encode(acc_phone, "UTF-8");
			data += "&acc_accupation=" + URLEncoder.encode(acc_accupation, "UTF-8");
			data += "&acc_point=" + URLEncoder.encode(point, "UTF-8");

//			link = "http://101.96.122.12:8086/webmypham/services/services/" + data;
			link = "http://ozskilledmigration.com/services/services/" + data;
			URL url = new URL(link);
			HttpURLConnection con = (HttpURLConnection) url.openConnection();

			bufferedReader = new BufferedReader(new InputStreamReader(con.getInputStream()));
			result = bufferedReader.readLine();
			return result;
		} catch (Exception e) {
			return new String("Exception: " + e.getMessage());
		}
	}

	@Override
	protected void onPostExecute(String result) {
		String jsonStr = result;
		if (jsonStr != null) {
			try {
				JSONObject jsonObj = new JSONObject(jsonStr);
				String query_result = jsonObj.getString("query_result");
				if (query_result.equals("SUCCESS")) {
					Toast.makeText(context, "Data inserted successfully. Signup successful.", Toast.LENGTH_SHORT).show();
				} else if (query_result.equals("FAILURE")) {
					Toast.makeText(context, "Data could not be inserted. Signup failed.", Toast.LENGTH_SHORT).show();
				} else {
					Toast.makeText(context, "Couldn't connect to remote database.", Toast.LENGTH_SHORT).show();
				}
			} catch (JSONException e) {
				e.printStackTrace();
//				Toast.makeText(context, "Error parsing JSON data.", Toast.LENGTH_SHORT).show();
			}
		} else {
//			Toast.makeText(context, "Couldn't get any JSON data.", Toast.LENGTH_SHORT).show();
		}
	}
}
