package com.geely.geely;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

public class Api extends AsyncTask<String, Void, String> {

	HttpResponse response;
	BufferedReader in;
	HttpPost httppost;
	Context ctx;
	String res = "no";
	String url;

	public Api(Context ctx, String url) {
		this.ctx = ctx;
		this.url = url;
	}

	@Override
	protected String doInBackground(String... arg0) {
		// TODO Auto-generated method stub

		return httpClient();
	}

	protected void onPostExecute(String result) {
		// might want to change "executed" for the returned string passed into
		// onPostExecute() but that is upto you
		Toast.makeText(ctx, result, Toast.LENGTH_LONG).show();
	}

	private String httpClient() {
		try {
			HttpClient httpclient = new DefaultHttpClient();

			httppost = new HttpPost(url);
			response = httpclient.execute(httppost);

			return readStream(response.getEntity().getContent());

		} catch (ClientProtocolException e) {
		} catch (IOException e) {
		}
		return null;

	}

	private String readStream(InputStream in) {
		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new InputStreamReader(in));
			String line = "";
			while ((line = reader.readLine()) != null) {
				return line;
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return null;

	}

}
