package si.kajzer.pokerprirocnik;

import java.util.ArrayList;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class ArrayAdapterKombinacije extends ArrayAdapter<Kombinacija> {
	
	static class ViewHolder {
		public TextView ime;
		public TextView karte;
	}
	
	private ArrayList<Kombinacija> kombinacije;
	private Activity context;
	
	public ArrayAdapterKombinacije(Activity context, ArrayList<Kombinacija> objects) {
		super(context, R.layout.list_item,  objects);
		this.kombinacije = objects;
		this.context = context;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View rowView = convertView;
		
		if (rowView == null) {
			LayoutInflater inflater = context.getLayoutInflater();
			
			rowView = inflater.inflate(R.layout.list_item, null);
			ViewHolder viewHolder = new ViewHolder();
			viewHolder.ime = (TextView) rowView
					.findViewById(R.id.textView_list_item_ime);
			viewHolder.karte = (TextView) rowView
					.findViewById(R.id.textView_list_item_karte);

			rowView.setTag(viewHolder);
		}
		ViewHolder holder = (ViewHolder) rowView.getTag();
		Kombinacija tmp = kombinacije.get(position);
		holder.ime.setText("" + tmp.getIme());
		holder.karte.setText(tmp.getKarte());

		return rowView;
	}
	
}
