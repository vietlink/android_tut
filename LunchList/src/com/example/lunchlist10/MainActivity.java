package com.example.lunchlist10;

import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.widget.CursorAdapter;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.R;
import com.example.model.Restaurant;
import com.example.model.RestaurantHelper;

@SuppressWarnings("deprecation")
public class MainActivity extends ListActivity{
	
	Restaurant current=null;
//	AtomicBoolean isActive=new AtomicBoolean(true);
	public final static String ID_EXTRA="apk.example._ID";

protected void onListItemClick(ListView l, View v, int position, long id) {
	// TODO Auto-generated method stub
	Intent i= new Intent(MainActivity.this, DetailForm.class);
	i.putExtra(ID_EXTRA, String.valueOf(id));
	startActivity(i);
};
	
	SharedPreferences prefs;
    private SharedPreferences.OnSharedPreferenceChangeListener prefListener=
            new SharedPreferences.OnSharedPreferenceChangeListener(){
                @Override
                public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String s) {
                    if (s.equals("sort_order"))
                        initList();
                }
            };
	Cursor models= null;
	RestaurantAdapter adapter= null;
	RestaurantHelper helper= null; //connect to DB
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
//		requestWindowFeature(Window.FEATURE_PROGRESS); //call progress bar feature
		setContentView(R.layout.activity_main08);
		prefs= PreferenceManager.getDefaultSharedPreferences(this);
		helper= new RestaurantHelper(this);
        initList();
		prefs.registerOnSharedPreferenceChangeListener(prefListener);
		}
	
//	public View.OnClickListener onSave= new View.OnClickListener() {
//		
//		@Override
//		public void onClick(View v) {
//			// TODO Auto-generated method stub
////			Restaurant r= new Restaurant();
//			current=new Restaurant();
//			current.setName(name.getText().toString());
//			current.setAddr(addr.getText().toString());
//			current.setNote(note.getText().toString());
//			switch (types.getCheckedRadioButtonId()) {
//			case R.id.sit_down:
//				current.setType("sit_down");
//				break;
//			case R.id.take_away:
//				current.setType("take_away");
//				break;
//			case R.id.delivery:
//				current.setType("delivery");
//			break;
//			}
////			adapter.add(current);
//			helper.insert(current.getName(), current.getAddr(), current.getType(), current.getNote());
//			models.requery();
//		}
//	};
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		new MenuInflater(this).inflate(R.menu.option, menu);
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
		if (id==R.id.add){
			startActivity(new Intent(MainActivity.this, DetailForm.class));
			return true;
		}
		if(id==R.id.prefs){
			startActivity(new Intent(MainActivity.this, EditPreference.class));
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
    private void initList(){
        if(models!=null){
            stopManagingCursor(models);
            models.close();
        }
        models=helper.getAll(prefs.getString("order_by", "name"));
        startManagingCursor(models);
        adapter=new RestaurantAdapter(models);
        setListAdapter(adapter);
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
