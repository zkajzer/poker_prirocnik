package si.kajzer.pokerprirocnik;


import java.util.ArrayList;

import android.app.ListActivity;
import android.os.Bundle;

public class AdapterActivity extends ListActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		ArrayList<Kombinacija> seznam = new ArrayList<Kombinacija>();
		seznam.add(new Kombinacija("Flush", "2h 6h 7h Kh Ah"));
		seznam.add(new Kombinacija("Straight", "2h 3h 4h 5h 6h"));
		seznam.add(new Kombinacija("One pair", "2h 3h 4h 5h 6h"));
		ArrayAdapterKombinacije adapter = new ArrayAdapterKombinacije(this, seznam);
		setListAdapter(adapter);
	}  
}
