package com.game.geodetective.utility;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class GameSettings {
	protected final static StringBuffer _tag = new StringBuffer("GameSettings");

	protected SharedPreferences _prefs;
	
	public GameSettings() {
		_prefs = Global.Context.getSharedPreferences("phase", Context.MODE_PRIVATE);
	}
	
	public boolean getSoundEnabled() {
		return _prefs.getBoolean("soundEnabled", true);
	}
	
	public void setSoundEnabled(boolean enabled) {
		Editor editor = _prefs.edit();
		editor.putBoolean("soundEnabled", enabled);
		editor.commit();
	}
	
	public void setLevelComplete(int level, boolean complete) {
		if (level < 0 || level > 34) {
			Logger.e(_tag, "Level out of range");
			return;
		}
		Editor editor = _prefs.edit();
		editor.putBoolean("level_complete_" + level, complete);
		editor.commit();
	}
	
	public boolean getLevelComplete(int level) {
		if (level < 0 || level > 34) {
			Logger.e(_tag, "Level out of range");
			return false;
		}
		return _prefs.getBoolean("level_complete_" + level, false);
	}
	
	public int getCompletedLevelCount() {
		int total = 0;
		for (int i = 0; i < 34; i++) {
			if (getLevelComplete(i))
				total++;
		}
		return total;
	}

	public void setCurrentChapter(int chapter) {
		if (chapter < 0 || chapter > 6) {
			Logger.e(_tag, "Chapter out of range");
			return;
		}
		Editor editor = _prefs.edit();
		editor.putInt("current_chapter", chapter);
		editor.commit();
	}
	
	public int getCurrentChapter() {
		return _prefs.getInt("current_chapter", 0);
	}
}
