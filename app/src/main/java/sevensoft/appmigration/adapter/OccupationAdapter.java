package sevensoft.appmigration.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Filter;

import java.util.ArrayList;
import java.util.List;

import sevensoft.appmigration.R;
import sevensoft.appmigration.object.Occupation;

/**
 * Created by STJHN on 13/12/2016.
 */

public class OccupationAdapter extends ArrayAdapter<Occupation> {

	Context context;
	int resource, textViewResourceID;
	List<Occupation> items, tempItems, suggestion;

	public OccupationAdapter(Context context, int resource, int textViewResourceID, List<Occupation> items){
		super(context, resource, textViewResourceID, items);
		this.context = context;
		this.resource = resource;
		this.textViewResourceID = textViewResourceID;
		this.items = items;
		tempItems = new ArrayList<Occupation>(items);
		suggestion = new ArrayList<Occupation>();
	}


	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View view = convertView;
		if(convertView == null){
			LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			view = inflater.inflate(R.layout.adapter_autotext, parent, false);
		}

		Occupation occupation = items.get(position);
		if(occupation != null){
			TextView lblName = (TextView)view.findViewById(R.id.lbl_name);
			if(lblName != null){
				lblName.setText(occupation.getOccupationCode() + " " +occupation.getOccupationName());
			}
		}

		return view;
	}

//	@NonNull
	@Override
	public Filter getFilter() {
		return nameFilter;
	}

	Filter nameFilter = new Filter() {

		@Override
		public CharSequence convertResultToString(Object resultValue) {
			String str = ((Occupation)resultValue).getOccupationCode() + " " + ((Occupation)resultValue).getOccupationName();
			return str;
		}

		@Override
		protected FilterResults performFiltering(CharSequence charSequence) {
			if(charSequence != null){
				suggestion.clear();
				for(Occupation occupation:tempItems){
					if((occupation.getOccupationCode() +" " + occupation.getOccupationName()).contains(charSequence.toString().toLowerCase())){
						suggestion.add(occupation);
					}
				}

				FilterResults filterResults = new FilterResults();
				filterResults.values = suggestion;
				filterResults.count = suggestion.size();
				return  filterResults;
			}else{
				return new FilterResults();
			}
		}

		@Override
		protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
			List<Occupation> filterList =  (ArrayList<Occupation>) filterResults.values;
			clear();
			if(filterResults != null && filterResults.count >0){

//				for(Occupation occupation : filterList){
//					add(occupation);
//				}
				for (int i = 0; i < filterList.size(); i++) {
					add(filterList.get(i));
//					poiAdapter.notifyDataSetInvalidated();
				}
				if (filterResults.count > 0) {
					notifyDataSetChanged();
				} else {
					notifyDataSetInvalidated();
				}
			}
		}
	};
}
