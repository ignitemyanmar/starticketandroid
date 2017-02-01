package com.ignite.mdm.ticketing.agent.callcenter;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.RadioButton;
import com.ignite.mm.ticketing.application.BaseSherlockActivity;
import java.util.Locale;

public class SettingActivity extends BaseSherlockActivity implements OnClickListener{
	private RadioButton rdo_mm_zawgyi;
	private RadioButton rdo_mm_unicode;
	private RadioButton rdo_english;
	
	private Toolbar toolbar;
	private Locale myLocale;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.setting_activity);
		
		toolbar = (Toolbar)findViewById(R.id.toolbar);
		
		if (toolbar != null) {
			toolbar.setNavigationIcon(R.drawable.abc_ic_ab_back_material);
			this.setSupportActionBar(toolbar);
		}
		
		rdo_mm_zawgyi = (RadioButton)findViewById(R.id.rdo_mm_zawgyi);
		rdo_mm_unicode = (RadioButton)findViewById(R.id.rdo_mm_unicode);
		rdo_english = (RadioButton)findViewById(R.id.rdo_english);
		
/*		if (rdo_mm_zawgyi.isChecked()) {
			lang = "zawgyi";
			changeLang(lang);
		}
		if (rdo_mm_unicode.isChecked()) {
			lang = "unicode";
			changeLang(lang);
		}
		if (rdo_english.isChecked()) {
			lang = "eng";
			changeLang(lang);
		}*/
		
		rdo_mm_zawgyi.setOnClickListener(this);
		rdo_mm_unicode.setOnClickListener(this);
		rdo_english.setOnClickListener(this);
		
		loadLocale();
	}
	
	public void onClick(View v) {
		// TODO Auto-generated method stub
		String lang = "eng";
		
		switch (v.getId()) {
		case R.id.rdo_mm_zawgyi:
			lang = "zawgyi";
			rdo_english.setChecked(false);
			rdo_mm_unicode.setChecked(false);
			break;
		case R.id.rdo_mm_unicode:
			lang = "unicode";
			rdo_english.setChecked(false);
			rdo_mm_zawgyi.setChecked(false);
			break;
		case R.id.rdo_english:
			lang = "eng";
			rdo_mm_zawgyi.setChecked(false);
			rdo_mm_unicode.setChecked(false);
			break;
		default:
			break;
		}
		
		changeLang(lang);
	}
	
	public void loadLocale()
    {
    	String langPref = "Language";
    	SharedPreferences prefs = getSharedPreferences("CommonPrefs", Activity.MODE_PRIVATE);
    	String language = prefs.getString(langPref, "");
    	
		if (language.equals("eng")) {
			rdo_english.setChecked(true);
		}else if (language.equals("zawgyi")) {
			rdo_mm_zawgyi.setChecked(true);
		}else if (language.equals("unicode")) {
			rdo_mm_unicode.setChecked(true);
		}else{
			rdo_mm_zawgyi.setChecked(true);
		}
    	changeLang(language);
    }
	
	protected void changeLang(String lang) {
		// TODO Auto-generated method stub
    	if (lang.equalsIgnoreCase(""))
    		return;
    	
    	myLocale = new Locale(lang);
    	saveLocale(lang);
        Locale.setDefault(myLocale);
        android.content.res.Configuration config = new android.content.res.Configuration();
        config.locale = myLocale;
        getBaseContext().getResources().updateConfiguration(config, getBaseContext().getResources().getDisplayMetrics());
        updateTexts();
	}
	
    public void saveLocale(String lang)
    {
    	String langPref = "Language";
    	SharedPreferences prefs = getSharedPreferences("CommonPrefs", Activity.MODE_PRIVATE);
    	SharedPreferences.Editor editor = prefs.edit();
    	
    	//editor.clear();
    	//editor.commit();
    	
		editor.putString(langPref, lang);
		editor.commit();
    }
	
	private void updateTexts()
	{
		toolbar.setTitle(R.string.strmm_change_language);
		rdo_mm_zawgyi.setText(R.string.strmm_myanmar_zawgyi);
		rdo_mm_unicode.setText(R.string.strmm_myanmar_unicode);
	}

	@Override
	public Intent getSupportParentActivityIntent() {
		// TODO Auto-generated method stub
		finish();
		return super.getSupportParentActivityIntent();
		
	}
}
