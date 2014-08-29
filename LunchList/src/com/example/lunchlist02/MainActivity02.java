package com.example.lunchlist02;

import java.util.ArrayList;
import java.util.List;

//import com.example.lunchlist.R;
import com.example.model.Restaurant;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioGroup;
import com.example.R;

public class MainActivity02 extends ActionBarActivity{
	private EditText name;
	private EditText addr;
	private RadioGroup types;
	List<Restaurant> models= new ArrayList<Restaurant>();
	ArrayAdapter<Restaurant> adapter= null;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main02);
		name= (EditText)findViewById(R.id.name);
		addr= (EditText)findViewById(R.id.addr);
		types= (RadioGroup)findViewById(R.id.types);
		Button save= (Button)findViewById(R.id.save);
		ListView list=(ListView)findViewById(R.id.restaurants);
		adapter= new ArrayAdapter<Restaurant>(this, android.R.layout.simple_list_item_1, models);
		list.setAdapter(adapter);
		save.setOnClickListener(onSave);
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
				r.setType("Sit_down");
				break;
			case R.id.take_away:
				r.setType("Take Out");
				break;
			case R.id.delivery:
				r.setType("Delivery");
			
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
}

