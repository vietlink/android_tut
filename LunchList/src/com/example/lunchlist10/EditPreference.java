package com.example.lunchlist10;

import android.os.Bundle;
import android.preference.PreferenceActivity;

import com.example.R;

public class EditPreference extends PreferenceActivity{
	@Override
	protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
//        getFragmentManager().beginTransaction().replace(android.R.id.content, new EditPreferenceFragment()).commit();
        addPreferencesFromResource(R.xml.preferences);
    }
//	public static class EditPreferenceFragment extends PreferenceFragment{
//		@Override
//		public void onCreate(Bundle savedInstanceState) {
//			// TODO Auto-generated method stub
//			super.onCreate(savedInstanceState);
//			addPreferencesFromResource(R.xml.preferences);
//		}
//	}


}
