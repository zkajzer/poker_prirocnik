package si.kajzer.pokerprirocnik;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class RegistracijaActivity extends Activity {

	EditText mUsername, mPassword;
	JSONParser jsonParser = new JSONParser();
	
	// naslov web servica
	private static final String web_service_dodaj = "http://10.0.2.2/android_connect/dodaj_uporabnika.php";
	
	private static final String TAG_SUCCESS = "success";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_registracija);
		
		mUsername = (EditText) findViewById(R.id.editTextUsername);
		mPassword = (EditText) findViewById(R.id.editTextPassword);
	}
	
	public void onClickKreiraj(View view) {
		new DodajUporabnika().execute();
	}
	
	/* Nastavi textview-e skladno s uspešnstjo poizvedbe */
	public void setResponse(boolean uspesno) {
		if(uspesno = true) {
        	TextView tv = (TextView) findViewById(R.id.textViewOdgovor);
        	tv.setText("Uporabnik je dodan!");
        	finish();
		}
		else {
        	TextView tv = (TextView) findViewById(R.id.textViewOdgovor);
        	tv.setText("Dodajanje uporabnika ni uspelo. Poskusite ponovno.");
		}
	}
	class DodajUporabnika extends AsyncTask<String, String, String> {
		
		JSONObject json;
		@Override
		protected String doInBackground(String... arg0) {
			String username = mUsername.getText().toString();
			String password = mPassword.getText().toString();
			
			// parametri
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			params.add(new BasicNameValuePair("username", username));
			params.add(new BasicNameValuePair("password", password));
			
			json = jsonParser.makeHttpRequest(web_service_dodaj, "POST", params);

            return null;
		}
		
		protected void onPostExecute(String args0) {	
            try {
                int success = json.getInt(TAG_SUCCESS);
                
                if (success == 1) {
                    // uspešno dodalo vrstico
                	setResponse(true);
                } else {
                    // ni uspelo dodati
                	setResponse(true);
                }
            } catch (JSONException e) {
            	setResponse(true);
                e.printStackTrace();
            }
		}
	}
}
