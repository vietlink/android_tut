package com.example.lunchlist07;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

import com.example.R;
import com.example.model.Restaurant;
import com.example.model.RestaurantHelper;

import android.app.TabActivity;
import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v4.widget.CursorAdapter;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

@SuppressWarnings("deprecation")
public class MainActivity extends TabActivity{
	private EditText name;
	private EditText addr;
	private RadioGroup types;
	private EditText note;
	Restaurant current=null;
//	AtomicBoolean isActive=new AtomicBoolean(true);
	private AdapterView.OnItemClickListener onListClick= new AdapterView.OnItemClickListener() {
		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			// TODO Auto-generated method stub
			models.moveToPosition(position);
			name.setText(helper.getName(models));
			addr.setText(helper.getAddress(models));
			note.setText(helper.getNote(models));
			if (helper.getType(models).equals("sit_down")) {
				types.check(R.id.sit_down);
			} else if(helper.getType(models).equals("take_away")){
				types.check(R.id.take_away);
			}else{
				types.check(R.id.delivery);
			}
			getTabHost().setCurrentTab(1);
		}
	};
	Cursor models= null;
	RestaurantAdapter adapter= null;
	RestaurantHelper helper= null; //connect to DB
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
//		requestWindowFeature(Window.FEATURE_PROGRESS); //call progress bar feature
		setContentView(R.layout.activity_main05_06);
		name= (EditText)findViewById(R.id.name);
		addr= (EditText)findViewById(R.id.address);
		types= (RadioGroup)findViewById(R.id.types);
		note=(EditText)findViewById(R.id.note);
		Button save= (Button)findViewById(R.id.save);
		ListView list=(ListView)findViewById(R.id.restaurants);
		helper= new RestaurantHelper(this);
//		models= helper.getAll();
		startManagingCursor(models);
		adapter= new RestaurantAdapter(models);
		
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
//			Restaurant r= new Restaurant();
			current=new Restaurant();
			current.setName(name.getText().toString());
			current.setAddr(addr.getText().toString());
			current.setNote(note.getText().toString());
			switch (types.getCheckedRadioButtonId()) {
			case R.id.sit_down:
				current.setType("sit_down");
				break;
			case R.id.take_away:
				current.setType("take_away");
				break;
			case R.id.delivery:
				current.setType("delivery");
			break;
			}
//			adapter.add(current);
			helper.insert(current.getName(), current.getAddr(), current.getType(), current.getNote());
			models.requery();
		}
	};
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.option, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.toast) {
			String message="No restaurant selected";
			if (current!=null){
				message=current.getNote();
			}
			Toast.makeText(this, message, Toast.LENGTH_LONG).show();
			return true;
		}

		return super.onOptionsItemSelected(item);
	}
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		helper.close();
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
		void populateForm (Cursor c, RestaurantHelper helper){
			name.setText(helper.getName(c));
			address.setText(helper.getAddress(c));
			if (helper.getType(c).equals("sit_down")){
				icon.setImageResource(R.drawable.ball_red);
			}
			else if (helper.getType(c).equals("take_away")){
				icon.setImageResource(R.drawable.ball_yellow);
			}
			else{ 
				icon.setImageResource(R.drawable.ball_green);
			}
		}
	}
	class RestaurantAdapter extends CursorAdapter{
		
		public RestaurantAdapter(Cursor c) {
			// TODO Auto-generated constructor stub
			super(MainActivity.this, c); 
		}
		
		@Override
		public void bindView(View row, Context ctxt, Cursor c) {
			// TODO Auto-generated method stub
			RestaurantHolder holder= (RestaurantHolder)row.getTag();
			holder.populateForm(c, helper);
		}
		@Override
		public View newView(Context ctxt, Cursor c, ViewGroup parent) {
			// TODO Auto-generated method stub
			LayoutInflater inflate= getLayoutInflater();
			View row= inflate.inflate(R.layout.row, parent, false);
			RestaurantHolder holder= new RestaurantHolder(row);
			row.setTag(holder);
			return row;
		}
	}
}
