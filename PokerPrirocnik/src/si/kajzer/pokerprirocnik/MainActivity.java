package si.kajzer.pokerprirocnik;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	}
	
	public void onClickKombinacije(View view) {
		Intent intent = new Intent(this, KombinacijeActivity.class);
		startActivity(intent);
	}
	
	public void onClickSeznamKombinacij(View view) {
		Intent intent = new Intent(this, AdapterActivity.class);
		startActivity(intent);
	}
	
	public void onClickVerjetnosti(View view) {
		Toast.makeText(getApplicationContext(), "Ni implementirano", Toast.LENGTH_SHORT).show();
	}
	
	public void onClickRegistracija(View view) {
		Intent intent = new Intent(this, RegistracijaActivity.class);
		startActivity(intent);
	}
}
