package com.jinject.utils;

public class Other implements IOther {
	private int value;
	@Override
	public int getValue() {
		return value;
	}

	@Override
	public void setValue(int value) {
		this.value = value;
	}

}
