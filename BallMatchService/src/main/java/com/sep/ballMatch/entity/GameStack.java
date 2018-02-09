package com.sep.ballMatch.entity;


import java.util.ArrayList;
import java.util.List;


public class GameStack {
    private List<GameRoundList> storage = new ArrayList<GameRoundList>();

    public void push(GameRoundList v) {
        storage.add(v);
    }

    public GameRoundList getLastball() {
        return storage.get(storage.size()-2);
    }
    
    public GameRoundList getCurrentball() {
        return storage.get(storage.size()-1);
    }

    public boolean empty() {
        return storage.isEmpty();
    }
    
    public int size() {
        return storage.size();
    }
    
    public int playAsize() {
    	int size = 0;
    	for(GameRoundList list : storage) {
    		if("A".equals(list.getPlayer())){
    			size ++;
    		}
    	}
    	return size;
    }
    
    public int playBsize() {
    	int size = 0;
    	for(GameRoundList list : storage) {
    		if("B".equals(list.getPlayer())){
    			size ++;
    		}
    	}
    	return size;
    }
}