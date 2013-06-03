package si.kajzer.pokerprirocnik;


import android.app.Activity;
import android.content.ClipData;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.DragEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.DragShadowBuilder;
import android.view.View.OnDragListener;
import android.view.View.OnTouchListener;
import android.widget.ImageView;
import android.widget.Toast;


public class KombinacijeActivity extends Activity {

	ImageView option[], choice[], zeIzbrani[];
	int stevecIzbranih = 0;
	String izbraneKarte[]; //izbrane karte v string formatu
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		zeIzbrani = new ImageView[5];
		izbraneKarte = new String[5];
		setContentView(R.layout.activity_kombinacije_default);	
	}
	
	/* ko se dotaknemo opcijo, jo zaènemo draggati */
	private final class ChoiceTouchListener implements OnTouchListener {
		
		@Override
		public boolean onTouch(View view, MotionEvent me) {
			if(me.getAction() == MotionEvent.ACTION_DOWN) {
				//zaèetek dragganja
				ClipData data = ClipData.newPlainText("", "");
				DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(view);
				view.startDrag(data, shadowBuilder, view, 0);
				return true;
			}
			else
				return false;
		}
	}
	
    /* razred bo hendlal droppe na choic-e */
	private class ChoiceDragListener implements OnDragListener {
		
		@Override
		public boolean onDrag(View v, DragEvent de) {
			switch(de.getAction()) {
				case DragEvent.ACTION_DRAG_STARTED:
					break;
				case DragEvent.ACTION_DRAG_ENTERED:
					break;
				case DragEvent.ACTION_DRAG_EXITED:
					break;
				case DragEvent.ACTION_DROP:
					//handlaj dregan view ki je dropan na drop view
					View view = (View) de.getLocalState();
					//nehamo prikazovat view tam kjer je bil, ko smo ga odvlekli
					view.setVisibility(View.INVISIBLE);

					ImageView dropTarget = (ImageView) v;
					ImageView dropped = (ImageView) view;
					
					Bitmap prenesenaSlika = ((BitmapDrawable)dropped.getDrawable()).getBitmap();
					dropTarget.setImageBitmap(Bitmap.createScaledBitmap(prenesenaSlika, dropped.getWidth(), dropped.getHeight(), false));
					
					int pos = Integer.parseInt((String)dropTarget.getTag())-1;
					izbraneKarte[pos] = (String)dropped.getTag();
					zeIzbrani[pos] = dropped;
					zeIzbrani[pos].setTag("jeIzbran"); //ima sliko / je izbran
					
					if(prestejIzbrane() == 5) {
						//Toast.makeText(getApplicationContext(), "5 izbranih!", Toast.LENGTH_SHORT).show();
						preglejKombinacije();
					}
					break;
				case DragEvent.ACTION_DRAG_ENDED:
					break;
				default:
					break;
			}			
			return true;
		}
	}
	
	//da ne izgubimo slik, ki smo jih dregali
	public void posodobiLayout() {
		for (int i = 0; i < 5; i++) {
			if (zeIzbrani[i] != null) {
				if ((String) zeIzbrani[i].getTag() == "jeIzbran") {
					Bitmap prenesenaSlika = ((BitmapDrawable) zeIzbrani[i]
							.getDrawable()).getBitmap();
					choice[i].setImageBitmap(Bitmap.createScaledBitmap(
							prenesenaSlika, zeIzbrani[i].getWidth(),
							zeIzbrani[i].getHeight(), false));
				}
			}
		}
	}
	
	//koliko kart smo potegnili na vrh
	public int prestejIzbrane() {
		int i;
		for(i = 0; i< 5; i++)
			if(zeIzbrani[i] == null)
				break;
		
		return i;
	}
	public void onClickKrizi(View view) {
		setContentView(R.layout.activity_kombinacije_krizi);
		nastaviTouchListenerje("krizi");
		posodobiLayout();		
	}
	
	public void onClickSrca(View view) {
		setContentView(R.layout.activity_kombinacije_srce);
		nastaviTouchListenerje("srca");
		posodobiLayout();
	}
	
	public void onClickKare(View view) {
		setContentView(R.layout.activity_kombinacije_karo);
		nastaviTouchListenerje("kare");
		posodobiLayout();
	}
	
	public void onClickPiki(View view) {
		setContentView(R.layout.activity_kombinacije_piki);
		nastaviTouchListenerje("piki");
		posodobiLayout();
	}
	
	public void onClickNazaj(View view) {
		setContentView(R.layout.activity_kombinacije_default);
		nastaviTouchListenerje("null");
		posodobiLayout();
	}
	
	public boolean royalFlush(Karta[] karte) {
		
		//ce niso vse iste barve, vrnemo false
		String barva = karte[0].getBarva();
		for(int i=0; i<5; i++) {
			if(! karte[i].getBarva().equals(barva)) 
				return false;
		}
		
		//prestejemo kolikokrat se je katera stevilka ponavljala(v primer RF od 10-ASA vsaka enkrat)
		int[] stevci = new int [15];
		for(int i=0; i<13; i++) 
			stevci[i] = 0;
		
		for(int i=0; i<5; i++) {
			stevci[karte[i].getSt()]++;
		}
		
		if(stevci[10] == 1 && stevci[11] == 1 && stevci[12] == 1 && stevci[13] == 1 && stevci[14] == 1)
			return true;
		
		return false;
	}
	
	public boolean straightFlush(Karta[] karte) {
		//ce niso vse iste barve, vrnemo false
		String barva = karte[0].getBarva();
		for (int i = 0; i < 5; i++) {
			if (!karte[i].getBarva().equals(barva))
				return false;
		}
		
		//poiscemo najmanjsega
		int stevilke[] = new int[5];
		int MIN = 15;
		for(int i=0; i<5; i++) {
			if(karte[i].getSt() < MIN)
				MIN = karte[i].getSt();
			stevilke[i] = karte[i].getSt();
		}
		
		//prestejemo kolikokrat se je katera stevilka ponavljala
		int[] stevci = new int[15];
		for (int i = 0; i < 13; i++)
			stevci[i] = 0;

		for (int i = 0; i < 5; i++) {
			stevci[karte[i].getSt()]++;
		}
		
		if(stevci[MIN+1] == 1 && stevci[MIN+2] == 1 && stevci[MIN+3] == 1 && stevci[MIN+4] == 1)
			return true;
		
		return false;
	}
	
	public boolean fourOfAKind(Karta[] karte) {
		//prestejemo kolikokrat se je katera stevilka ponavljala
		int[] stevci = new int[15];
		for (int i = 0; i < 13; i++)
			stevci[i] = 0;

		for (int i = 0; i < 5; i++) {
			stevci[karte[i].getSt()]++;
		}
				
		for(int i=0; i<15; i++) {
			if(stevci[i] == 4)
				return true;
		}
		return false;
	}
	
	public boolean fullHouse(Karta[] karte) {
		//prestejemo kolikokrat se je katera stevilka ponavljala
		int[] stevci = new int[15];
		for (int i = 0; i < 13; i++)
			stevci[i] = 0;

		for (int i = 0; i < 5; i++) {
			stevci[karte[i].getSt()]++;
		}
		
		boolean dve=false, tri=false; //ce smo nasli dve isti, in tri iste
		for(int i=0; i<15; i++) {
			if(stevci[i] == 2)
				dve = true;
			if(stevci[i] == 3)
				tri = true;
		}	
		
		if(dve && tri) 
			return true;
		
		return false;
	}
	
	public boolean flush(Karta[] karte) {
		//ce niso vse iste barve, vrnemo false
		String barva = karte[0].getBarva();
		for (int i = 0; i < 5; i++) {
			if (!karte[i].getBarva().equals(barva))
				return false;
		}
		
		return true;
	}
	
	public boolean straight(Karta[] karte) {
		//poiscemo najmanjsega
		int stevilke[] = new int[5];
		int MIN = 15;
		for(int i=0; i<5; i++) {
			if(karte[i].getSt() < MIN)
				MIN = karte[i].getSt();
			stevilke[i] = karte[i].getSt();
		}
		
		//prestejemo kolikokrat se je katera stevilka ponavljala
		int[] stevci = new int[15];
		for (int i = 0; i < 13; i++)
			stevci[i] = 0;

		for (int i = 0; i < 5; i++) {
			stevci[karte[i].getSt()]++;
		}

		if (stevci[MIN + 1] == 1 && stevci[MIN + 2] == 1 && stevci[MIN + 3] == 1 && stevci[MIN + 4] == 1)
			return true;
		return false;
	}
	
	public boolean threeOfAKind(Karta[] karte) {
		//prestejemo kolikokrat se je katera stevilka ponavljala
		int[] stevci = new int[15];
		for (int i = 0; i < 13; i++)
			stevci[i] = 0;

		for (int i = 0; i < 5; i++) {
			stevci[karte[i].getSt()]++;
		}

		for(int i=0; i<15; i++) {
			if(stevci[i] == 3)
				return true;
		}	
		
		return false;
	}
	
	public boolean twoPair(Karta[] karte) {
		//prestejemo kolikokrat se je katera stevilka ponavljala
		int[] stevci = new int[15];
		for (int i = 0; i < 13; i++)
			stevci[i] = 0;

		for (int i = 0; i < 5; i++) {
			stevci[karte[i].getSt()]++;
		}

		int parCount = 0; //kolikokrat se je pojavil par ( v našem primeru se mora 2krat)
		for(int i=0; i<15; i++) {
			if(stevci[i] == 2)
				parCount++;
		}	
		if(parCount == 2)
			return true;
		return false;
	}
	
	public boolean onePair(Karta[] karte) {
		//prestejemo kolikokrat se je katera stevilka ponavljala
		int[] stevci = new int[15];
		for (int i = 0; i < 13; i++)
			stevci[i] = 0;

		for (int i = 0; i < 5; i++) {
			stevci[karte[i].getSt()]++;
		}

		int parCount = 0; //kolikokrat se je pojavil par ( v našem primeru se mora 1krat)
		for(int i=0; i<15; i++) {
			if(stevci[i] == 2)
				parCount++;
		}	
		if(parCount == 1)
			return true;
		return false;
	}
	
	public void preglejKombinacije() {
		//prebere karte ki smo jih izbrali, kreira objekte tipa Karta
		Karta[] karte = new Karta[5];
		for(int i=0; i<5; i++) { 
			karte[i] = new Karta();
			karte[i].setBarva(izbraneKarte[i].substring(5, 9));
			karte[i].setSt(Integer.parseInt(izbraneKarte[i].substring(9))); 
		}
		if(royalFlush(karte))
			Toast.makeText(getApplicationContext(), "ROYAL FLUSH!", Toast.LENGTH_SHORT).show();
		else if(straightFlush(karte))
			Toast.makeText(getApplicationContext(), "STRAIGHT FLUSH!", Toast.LENGTH_SHORT).show();
		else if(fourOfAKind(karte))
			Toast.makeText(getApplicationContext(), "FOUR OF A KIND!", Toast.LENGTH_SHORT).show();
		else if(fullHouse(karte))
			Toast.makeText(getApplicationContext(), "FULL HOUSE!", Toast.LENGTH_SHORT).show();
		else if(flush(karte))
			Toast.makeText(getApplicationContext(), "FLUSH!", Toast.LENGTH_SHORT).show();
		else if(straight(karte))
			Toast.makeText(getApplicationContext(), "STRAIGHT!", Toast.LENGTH_SHORT).show();
		else if(threeOfAKind(karte))
			Toast.makeText(getApplicationContext(), "THREE OF A KIND!", Toast.LENGTH_SHORT).show();
		else if(twoPair(karte))
			Toast.makeText(getApplicationContext(), "TWO PAIR!", Toast.LENGTH_SHORT).show();
		else if(onePair(karte))
			Toast.makeText(getApplicationContext(), "ONE PAIR!", Toast.LENGTH_SHORT).show();
		else
			Toast.makeText(getApplicationContext(), "High card.", Toast.LENGTH_SHORT).show();
	}
	private void nastaviTouchListenerje(String barva) {
		choice = new ImageView[5];
		//imageviewi na katere droppamo
		choice[0] = (ImageView)findViewById(R.id.kartaChoice1);
		choice[1] = (ImageView)findViewById(R.id.kartaChoice2);
		choice[2] = (ImageView)findViewById(R.id.kartaChoice3);
		choice[3] = (ImageView)findViewById(R.id.kartaChoice4);
		choice[4] = (ImageView)findViewById(R.id.kartaChoice5);
		
		//nastavimo drag listenerje
		choice[0].setOnDragListener(new ChoiceDragListener());
		choice[1].setOnDragListener(new ChoiceDragListener());
		choice[2].setOnDragListener(new ChoiceDragListener());
		choice[3].setOnDragListener(new ChoiceDragListener());
		choice[4].setOnDragListener(new ChoiceDragListener());
		
		if(barva == "krizi") {
			option = new ImageView[13];
			//imageviewi ki jih dregamo
			option[0] = (ImageView)findViewById(R.id.kartakriz02);	
			option[1] = (ImageView)findViewById(R.id.kartakriz03);	
			option[2] = (ImageView)findViewById(R.id.kartakriz04);
			option[3] = (ImageView)findViewById(R.id.kartakriz05);
			option[4] = (ImageView)findViewById(R.id.kartakriz06);
			option[5] = (ImageView)findViewById(R.id.kartakriz07);
			option[6] = (ImageView)findViewById(R.id.kartakriz08);
			option[7] = (ImageView)findViewById(R.id.kartakriz09);
			option[8] = (ImageView)findViewById(R.id.kartakriz10);
			option[9] = (ImageView)findViewById(R.id.kartakriz11);
			option[10] = (ImageView)findViewById(R.id.kartakriz12);
			option[11] = (ImageView)findViewById(R.id.kartakriz13);
			option[12] = (ImageView)findViewById(R.id.kartakriz14);			
			//nastavimo touch listenerje
			for(int i=0; i<13; i++)
				option[i].setOnTouchListener(new ChoiceTouchListener());
		}		
		else if(barva == "srca") {
			option = new ImageView[13];
			//imageviewi ki jih dregamo
			option[0] = (ImageView)findViewById(R.id.kartaSrce02);	
			option[1] = (ImageView)findViewById(R.id.kartaSrce03);	
			option[2] = (ImageView)findViewById(R.id.kartaSrce04);
			option[3] = (ImageView)findViewById(R.id.kartaSrce05);
			option[4] = (ImageView)findViewById(R.id.kartaSrce06);
			option[5] = (ImageView)findViewById(R.id.kartaSrce07);
			option[6] = (ImageView)findViewById(R.id.kartaSrce08);
			option[7] = (ImageView)findViewById(R.id.kartaSrce09);
			option[8] = (ImageView)findViewById(R.id.kartaSrce10);
			option[9] = (ImageView)findViewById(R.id.kartaSrce11);
			option[10] = (ImageView)findViewById(R.id.kartaSrce12);
			option[11] = (ImageView)findViewById(R.id.kartaSrce13);
			option[12] = (ImageView)findViewById(R.id.kartaSrce14);			
			//nastavimo touch listenerje
			for(int i=0; i<13; i++)
				option[i].setOnTouchListener(new ChoiceTouchListener());
		}		
		else if(barva == "kare") {
			option = new ImageView[13];
			//imageviewi ki jih dregamo
			option[0] = (ImageView)findViewById(R.id.kartaKaro02);	
			option[1] = (ImageView)findViewById(R.id.kartaKaro03);	
			option[2] = (ImageView)findViewById(R.id.kartaKaro04);
			option[3] = (ImageView)findViewById(R.id.kartaKaro05);
			option[4] = (ImageView)findViewById(R.id.kartaKaro06);
			option[5] = (ImageView)findViewById(R.id.kartaKaro07);
			option[6] = (ImageView)findViewById(R.id.kartaKaro08);
			option[7] = (ImageView)findViewById(R.id.kartaKaro09);
			option[8] = (ImageView)findViewById(R.id.kartaKaro10);
			option[9] = (ImageView)findViewById(R.id.kartaKaro11);
			option[10] = (ImageView)findViewById(R.id.kartaKaro12);
			option[11] = (ImageView)findViewById(R.id.kartaKaro13);
			option[12] = (ImageView)findViewById(R.id.kartaKaro14);			
			//nastavimo touch listenerje
			for(int i=0; i<13; i++)
				option[i].setOnTouchListener(new ChoiceTouchListener());
		}
		else if(barva == "piki") {
			option = new ImageView[13];
			//imageviewi ki jih dregamo
			option[0] = (ImageView)findViewById(R.id.kartaPiki02);	
			option[1] = (ImageView)findViewById(R.id.kartaPiki03);	
			option[2] = (ImageView)findViewById(R.id.kartaPiki04);
			option[3] = (ImageView)findViewById(R.id.kartaPiki05);
			option[4] = (ImageView)findViewById(R.id.kartaPiki06);
			option[5] = (ImageView)findViewById(R.id.kartaPiki07);
			option[6] = (ImageView)findViewById(R.id.kartaPiki08);
			option[7] = (ImageView)findViewById(R.id.kartaPiki09);
			option[8] = (ImageView)findViewById(R.id.kartaPiki10);
			option[9] = (ImageView)findViewById(R.id.kartaPiki11);
			option[10] = (ImageView)findViewById(R.id.kartaPiki12);
			option[11] = (ImageView)findViewById(R.id.kartaPiki13);
			option[12] = (ImageView)findViewById(R.id.kartaPiki14);			
			//nastavimo touch listenerje
			for(int i=0; i<13; i++)
				option[i].setOnTouchListener(new ChoiceTouchListener());
		}
	}
}
