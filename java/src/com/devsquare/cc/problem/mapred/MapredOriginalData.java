package com.devsquare.cc.problem.mapred;

import java.util.Map;

public class MapredOriginalData {
	
	Map<Integer, Integer> peopleAgeGroup = null;
	
	public MapredOriginalData(Map<Integer,Integer> peopleAgeGroup) {
		this.peopleAgeGroup = peopleAgeGroup;
	}
	
	public Map<Integer, Integer> getPeopleAgeGroup(){
		return this.peopleAgeGroup;
	}

}
