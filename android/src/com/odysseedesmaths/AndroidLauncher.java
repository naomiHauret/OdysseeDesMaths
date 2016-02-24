package com.odysseedesmaths;

import android.os.Bundle;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.odysseedesmaths.minigames.coffeePlumbing.HardcoreCoffeeTest;

public class AndroidLauncher extends AndroidApplication {
	@Override
	protected void onCreate (Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		System.out.print("This is ");
		AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
		initialize(new OdysseeDesMaths(), config);
	}
}
