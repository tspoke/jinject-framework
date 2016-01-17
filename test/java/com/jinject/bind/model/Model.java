package com.jinject.bind.model;

import com.jinject.inject.api.Inject;

public class Model implements IModel {
	@Inject
	public IOther other;
	
	public String str;
	private int value;
	
	
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
