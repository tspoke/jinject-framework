package com.jinject.action;

import org.junit.Test;

import com.jinject.action.impl.ActionBinder;
import com.jinject.bind.exception.BindingResolverException;
import com.jinject.inject.impl.InjectionBinder;
import com.jinject.inject.impl.Injector;
import com.jinject.reflect.impl.Reflector;
import com.jinject.utils.Events.BinaryTestComplexEvent;
import com.jinject.utils.Events.BinaryTestEvent;
import com.jinject.utils.Events.SimpleTestEvent;
import com.jinject.utils.IOther;
import com.jinject.utils.MyAction;
import com.jinject.utils.MyOtherAction;
import com.jinject.utils.Other;
import com.jinject.utils.ReachedException;

/**
 * Test class for actions execution throught events
 * @author Thibaud Giovannetti
 *
 */
public class ActionTest {
	
	@Test(expected=ReachedException.class)
	public void bindEventToAction() throws InstantiationException, IllegalAccessException, BindingResolverException{
		ActionBinder actionBinder = new ActionBinder(new Injector(new Reflector()));
		actionBinder.bind(SimpleTestEvent.class).to(MyAction.class);
		
		SimpleTestEvent event = (SimpleTestEvent) actionBinder.getBinding(SimpleTestEvent.class);
		event.fire();
	}
	
	@Test(expected=ReachedException.class)
	public void bindEventToActionWithParams() throws InstantiationException, IllegalAccessException, BindingResolverException{
		ActionBinder actionBinder = new ActionBinder(new Injector(new Reflector()));
		actionBinder.bind(BinaryTestEvent.class).to(MyAction.class);
		
		BinaryTestEvent event = (BinaryTestEvent) actionBinder.getBinding(BinaryTestEvent.class);
		event.fire("Test bindings", 111);
	}

	@Test(expected=ReachedException.class)
	public void bindEventToActionWithComplexParams() throws InstantiationException, IllegalAccessException, BindingResolverException{
		Injector injector = new Injector(new Reflector());
		InjectionBinder injectionBinder = new InjectionBinder(injector);
		injectionBinder.bind(IOther.class).to(Other.class);
		
		ActionBinder actionBinder = new ActionBinder(injector, injectionBinder);
		actionBinder.bind(BinaryTestComplexEvent.class).to(MyOtherAction.class);
		
		BinaryTestComplexEvent event = (BinaryTestComplexEvent) actionBinder.getBinding(BinaryTestComplexEvent.class);
		IOther other = new Other();
		other.setValue(555);
		event.fire("Test bindings", other);
	}
	
	/**
	 * This test is special because it use a anonymous class to fire its event. No special errors with that but the action doesn't receive the good one.
	 * The reason is that the enclosing class is the ActionTest.class (not the interface) and the injector doesn't detect that this instance is injectable to a field
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 * @throws BindingResolverException
	 */
	@Test(expected=ReachedException.class)
	public void bindEventToActionWithComplexParamsWithAnonymousClass() throws InstantiationException, IllegalAccessException, BindingResolverException{
		Injector injector = new Injector(new Reflector());
		InjectionBinder injectionBinder = new InjectionBinder(injector);
		injectionBinder.bind(IOther.class).to(Other.class);
		
		ActionBinder actionBinder = new ActionBinder(injector, injectionBinder);
		actionBinder.bind(BinaryTestComplexEvent.class).to(MyOtherAction.class);
		
		BinaryTestComplexEvent event = (BinaryTestComplexEvent) actionBinder.getBinding(BinaryTestComplexEvent.class);
		
		IOther other = new IOther(){
			@Override
			public int getValue() {
				return 789;
			}

			@Override
			public void setValue(int value) {}
		};
		event.fire("Test bindings", other);
	}
}
