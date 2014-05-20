package com.zaiquiri.janus;

import java.lang.reflect.AccessibleObject;

public class Exposer {

	public void expose(AccessibleObject object){
		object.setAccessible(true);
	}
	
	public void hide(AccessibleObject object){
		object.setAccessible(false);
	}
}
