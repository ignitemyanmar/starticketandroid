package com.ignite.mdm.ticketing.agent.callcenter;

import android.app.Application;
import uk.co.chrisjenx.calligraphy.CalligraphyConfig;

/**
 * Created by user on 2/1/17.
 */

public class StarApplication extends Application {
  @Override public void onCreate() {
    super.onCreate();
    FontOverride.setDefaultFont(getApplicationContext(), "DEFAULT", "fonts/zawgyi.ttf");
    FontOverride.setDefaultFont(getApplicationContext(), "MONOSPACE", "fonts/zawgyi.ttf");
    FontOverride.setDefaultFont(getApplicationContext(), "SANS_SERIF", "fonts/zawgyi.ttf");
    TypeFaceUtil.overrideFont(getApplicationContext(), "SERIF", "fonts/zawgyi.ttf");
    CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
        .setDefaultFontPath("fonts/zawgyi.ttf")
        .setFontAttrId(R.attr.fontPath)
        .build()
    );
  }

}