package com.jinject.action.impl;

import com.jinject.action.api.IActionBinding;
import com.jinject.bind.api.IBinding;
import com.jinject.bind.impl.Binding;

public class ActionBinding extends Binding implements IActionBinding {
	
	public ActionBinding(Object k) {
		super(k);
	}
	
	@Override
	public IBinding to(Object o) {
		if(o instanceof Class)
			return super.to(o).toName(o);
		else
			return super.to(o).toName(o.getClass());
	}
}
