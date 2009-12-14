package com.android.contacts;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Intent;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ListView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.CheckBox;
import android.util.Log;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.CompoundButton;

public class DialerSettingsUI extends Activity {

	public static String TAG = "Dialer Settings";
	private String[] action = new String[] { DialerSettings.ADDCONTACTS, DialerSettings.SMS, DialerSettings.VOICEMAIL };
	private String[] vm = new String[] { DialerSettings.GV, DialerSettings.TMO, DialerSettings.NONE };
	private Spinner actionSpinner, voicemailSpinner;
	private Context context;	
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		context = this.getApplicationContext();		
		setContentView(R.layout.dialer_settings);
		
		//Wysie_Soh: action type		
		actionSpinner = (Spinner)findViewById(R.id.actionspinner);
		actionSpinner.setOnItemSelectedListener(new OnItemSelectedListener() {
			public void onItemSelected(AdapterView<?> parent, View v, int position, long id) {
				String currentSelection = action[position];
				DialerSettings.setLeftButtonType(context, currentSelection);
			}
			
			public void onNothingSelected(AdapterView<?> parent) {
				// Do nothing
			}
		});		
		
		ArrayAdapter spinnerArrayAdapter = new ArrayAdapter(context, android.R.layout.simple_spinner_item, action);
		spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		actionSpinner.setAdapter(spinnerArrayAdapter);
		findCurrentAction();
		
		voicemailSpinner = (Spinner)findViewById(R.id.voicemailapps);
		voicemailSpinner.setOnItemSelectedListener(new OnItemSelectedListener() {
			public void onItemSelected(AdapterView<?> parent, View v, int position, long id) {
				String currentSelection = vm[position];
				DialerSettings.setVoicemailApp(context, currentSelection);
			}
			
			public void onNothingSelected(AdapterView<?> parent) {
				// Do nothing
			}
		});
		spinnerArrayAdapter = new ArrayAdapter(context, android.R.layout.simple_spinner_item, vm);
		spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		voicemailSpinner.setAdapter(spinnerArrayAdapter);
		findCurrentVoicemailApp();
		
		// Wysie_Soh: Sensor settings		
		CheckBox sensorRotation = (CheckBox)findViewById(R.id.sensor_checkbox);
		
		if (DialerSettings.getSensorRotation(context))
			sensorRotation.setChecked(true);
		else
			sensorRotation.setChecked(false);
			
		sensorRotation.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				if(isChecked)
					DialerSettings.setSensorRotation(context, true);
				else
					DialerSettings.setSensorRotation(context, false);				
			}
		});
	}
		
	// Naive search to set action spinner position to current value	
	private void findCurrentAction() {
		String currSetting = DialerSettings.getLeftButtonType(context);
		for (int i = 0; i < action.length; i++) {
			if (currSetting.equals(action[i])) {
				actionSpinner.setSelection(i);
				break;
			}
		}	
	}
	
	// Naive search to set voicemail spinner position to current value	
	private void findCurrentVoicemailApp() {
		String currSetting = DialerSettings.getVoicemailApp(context);
		for (int i = 0; i < vm.length; i++) {
			if (currSetting.equals(vm[i])) {
				voicemailSpinner.setSelection(i);
				break;
			}
		}	
	}
	
}