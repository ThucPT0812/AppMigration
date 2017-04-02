package sevensoft.appmigration.object;

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

/**
 * Created by STJHN on 2017/02/28.
 */

public class SendMailPHP extends AsyncTask<String, Void, String> {

	private Context context;

	public SendMailPHP(Context context) {
		this.context = context;
	}

	@Override
	protected void onPreExecute() {
		super.onPreExecute();
	}

	@Override
	protected String doInBackground(String... strings) {
		String email = strings[0];
		String message = strings[1];
		String total_point = strings[2];

		String link;
		String data;
		BufferedReader bufferedReader;
		String result;

		try {
			data = "?acc_email=" + URLEncoder.encode(email, "UTF-8");
			data += "&message=" + URLEncoder.encode(message, "UTF-8");
			data += "&total_point=" + URLEncoder.encode(total_point, "UTF-8");
//			link = "http://101.96.122.12:8086/webmypham/services/services/sendmail/" + data;
			link = "http://ozskilledmigration.com/services/services/sendmail/" + data;
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
	protected void onPostExecute(String s) {
		String jsonStr = s;
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
