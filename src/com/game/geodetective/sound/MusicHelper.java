package com.game.geodetective.sound;

import com.game.geodetective.R;
import com.game.geodetective.utility.Logger;

public class MusicHelper {
	protected final static StringBuffer _tag = new StringBuffer("MusicHelper");
	
	public static int getResourceId(int sound) {
		switch (sound) {
		default:
			Logger.e(_tag, "Invalid sound");
			return 0;
		}
	}
}
