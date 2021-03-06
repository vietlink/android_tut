package com.example.lunchlist04;

import java.util.ArrayList;
import java.util.List;

import com.example.R;
import com.example.model.Restaurant;

import android.app.TabActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.TabHost;
import android.widget.TextView;

@SuppressWarnings("deprecation")
public class MainActivity extends TabActivity{
	private EditText name;
	private EditText addr;
	private RadioGroup types;
	private AdapterView.OnItemClickListener onListClick= new AdapterView.OnItemClickListener() {
		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			// TODO Auto-generated method stub
			Restaurant r= models.get(position);
			name.setText(r.getName());
			addr.setText(r.getAddr());
			if (r.getType().equals("sit_down")) {
				types.check(R.id.sit_down);
			} else if(r.getType().equals("take_away")){
				types.check(R.id.take_away);
			}else{
				types.check(R.id.delivery);
			}
			getTabHost().setCurrentTab(1);
		}
	};
	List<Restaurant> models= new ArrayList<Restaurant>();
	RestaurantAdapter adapter= null;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main04);
		name= (EditText)findViewById(R.id.name);
		addr= (EditText)findViewById(R.id.address);
		types= (RadioGroup)findViewById(R.id.types);
		Button save= (Button)findViewById(R.id.save);
		ListView list=(ListView)findViewById(R.id.restaurants);
		adapter= new RestaurantAdapter();
		list.setAdapter(adapter);
		save.setOnClickListener(onSave);
		TabHost.TabSpec spec= getTabHost().newTabSpec("tag1");
		spec.setContent(R.id.restaurants);
		spec.setIndicator("List", getResources().getDrawable(R.drawable.list));
		getTabHost().addTab(spec);
		spec=getTabHost().newTabSpec("tag2");
		spec.setContent(R.id.details);
		spec.setIndicator("Details", getResources().getDrawable(R.drawable.restaurant));
		getTabHost().addTab(spec);
		getTabHost().setCurrentTab(0);
		list.setOnItemClickListener(onListClick);
		
	}
	
	public View.OnClickListener onSave= new View.OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			Restaurant r= new Restaurant();
			r.setName(name.getText().toString());
			r.setAddr(addr.getText().toString());
			switch (types.getCheckedRadioButtonId()) {
			case R.id.sit_down:
				r.setType("sit_down");
				break;
			case R.id.take_away:
				r.setType("take_away");
				break;
			case R.id.delivery:
				r.setType("delivery");
			break;
			}
			adapter.add(r);
		}
	};
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	static class RestaurantHolder{
		private TextView name=null;
		private TextView address=null;
		private ImageView icon=null;
		public RestaurantHolder(View row) {
			// TODO Auto-generated constructor stub
			name=(TextView)row.findViewById(R.id.title);
			address=(TextView)row.findViewById(R.id.addr);
			icon= (ImageView)row.findViewById(R.id.icon);
		}
		void populateForm (Restaurant r){
			name.setText(r.getName());
			address.setText(r.getAddr());
			if (r.getType().equals("sit_down")){
				icon.setImageResource(R.drawable.ball_red);
			}
			else if (r.getType().equals("take_away")){
				icon.setImageResource(R.drawable.ball_yellow);
			}
			else{ 
				icon.setImageResource(R.drawable.ball_green);
			}
		}
	}
	class RestaurantAdapter extends ArrayAdapter<Restaurant>{
		
		public RestaurantAdapter() {
			// TODO Auto-generated constructor stub
			super (MainActivity.this, android.R.layout.simple_list_item_1, models);
		}
		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			// TODO Auto-generated method stub
			View row= convertView;
			RestaurantHolder holder= null;
			if (row==null){
				LayoutInflater inflater= getLayoutInflater();
				row= inflater.inflate(R.layout.row, null);
				holder= new RestaurantHolder(row);
				row.setTag(holder);
			} else{
				holder=(RestaurantHolder)row.getTag();
			}
			holder.populateForm(models.get(position));
//			Restaurant r= models.get(position);
//			((TextView)row.findViewById(R.id.title)).setText(r.getName());
//			((TextView)row.findViewById(R.id.addr)).setText(r.getAddr());
//			ImageView icon= (ImageView)row.findViewById(R.id.icon);
//			if (r.getType().equals("sit_down"))
//				icon.setImageResource(R.drawable.ball_red);
//			if(r.getType().equals("take_out"))
//				icon.setImageResource(R.drawable.ball_yellow);
//			if(r.getType().equals("delivery"))
//				icon.setImageResource(R.drawable.ball_green);
			return row;
		}
	}
}
