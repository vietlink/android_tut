package com.example.lunchlist10;

import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;

import com.example.R;
import com.example.model.Restaurant;
import com.example.model.RestaurantHelper;

public class DetailForm extends Activity{
	private EditText name=null;
	private EditText address= null;
	private EditText note=null;
	private RadioGroup types=null;
	private RestaurantHelper helper=null;
	Restaurant current=null;
	String restaurantId=null;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.detail_form);
		helper=new RestaurantHelper(this);
		name=(EditText)findViewById(R.id.name);
		address=(EditText)findViewById(R.id.address);
		note=(EditText)findViewById(R.id.note);
		types=(RadioGroup)findViewById(R.id.types);
		Button save= (Button)findViewById(R.id.save);
		save.setOnClickListener(onSave);
		restaurantId=getIntent().getStringExtra(MainActivity.ID_EXTRA);
		if (restaurantId!=null){
			load();
		}
	}
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		helper.close();
	}
	private void load(){
		Cursor c= helper.getById(restaurantId);
		c.moveToFirst();
		name.setText(helper.getName(c));
		address.setText(helper.getAddress(c));
		if (helper.getType(c).equals("sit_down"))
			types.check(R.id.sit_down);
		if (helper.getType(c).equals("take_out"))
			types.check(R.id.take_out);
		else
			types.check(R.id.delivery);
		note.setText(helper.getNote(c));
	}
public View.OnClickListener onSave= new View.OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
//			Restaurant r= new Restaurant();
			current=new Restaurant();
			current.setName(name.getText().toString());
			current.setAddr(address.getText().toString());
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
			if (restaurantId==null)
				helper.insert(current.getName(), current.getAddr(), current.getType(), current.getNote());
//			models.requery();
			else
				helper.update(restaurantId, current.getName(), current.getAddr(), current.getType(), current.getNote());
			finish();
		}
	};

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("name", name.getText().toString());
        outState.putString("address", address.getText().toString());
        outState.putString("notes", note.getText().toString());
        outState.putInt("type", types.getCheckedRadioButtonId());
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        name.setText(savedInstanceState.getString("name"));
        address.setText(savedInstanceState.getString("address"));
        note.setText(savedInstanceState.getString("notes"));
        types.check(savedInstanceState.getInt("type"));
    }
}
