package com.game.geodetective.data.entity;

public class ClueLocation {
	public int _id;
	public String Name;
	public int ClueType1Id;
	public int ClueType2Id;
	
	@Override
	public int hashCode() {
		return _id;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj == null)
			return false;
		if (obj == this)
			return true;
		if (!(obj instanceof ClueLocation))
			return false;
		
		ClueLocation location = (ClueLocation)obj;
		return _id == location._id;
	}
}
