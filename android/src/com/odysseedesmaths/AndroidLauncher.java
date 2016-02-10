package com.odysseedesmaths;

import android.os.Bundle;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
<<<<<<< HEAD
import com.odysseedesmaths.menus.ScreenPause;
import com.odysseedesmaths.minigames.arriveeremarquable.ArriveeRemarquable;
=======
>>>>>>> 4ff1a32049e1cf6f95a3013de275c479eb51df5e

public class AndroidLauncher extends AndroidApplication {
	@Override
	protected void onCreate (Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
		initialize(new OdysseeDesMaths(), config);
	}
}
