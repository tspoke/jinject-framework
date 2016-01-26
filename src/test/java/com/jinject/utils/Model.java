package com.jinject.utils;

import com.jinject.inject.api.Inject;
import com.jinject.inject.api.InjectConstructor;

public class Model implements IModel {
	@Inject
	private IOther other;
	
	public String str;
	private int value;
	
	@InjectConstructor
	public Model(IOther again) {
		System.out.println("test/utils/Model.class : " + again);
	}
	
	public Model() {
		
	}
	
	
	@Override
	public int getValue() {
		return value;
	}
	@Override
	public void setValue(int value) {
		this.value = value;
	}
	@Override
	public IOther getOther() {
		return other;
	}
	@Override
	public void setOther(IOther other) {
		this.other = other;
	}
}
