package com.ignite.mdm.ticketing.agent.callcenter;

import android.app.Application;

/**
 * Created by user on 2/1/17.
 */

public class StarApplication extends Application {
  @Override public void onCreate() {
    super.onCreate();
    FontOverride.setDefaultFont(getApplicationContext(), "DEFAULT", "fonts/ZawgyiOne2008.ttf");
    FontOverride.setDefaultFont(getApplicationContext(), "MONOSPACE", "fonts/ZawgyiOne2008.ttf");
    FontOverride.setDefaultFont(getApplicationContext(), "SANS_SERIF", "fonts/ZawgyiOne2008.ttf");
    TypeFaceUtil.overrideFont(getApplicationContext(), "SERIF", "fonts/ZawgyiOne2008.ttf");
    //CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
    //    .setDefaultFontPath("fonts/zawgyi.ttf")
    //    .setFontAttrId(R.attr.fontPath)
    //    .build()
    //);
  }

}