package com.jinject.event;

import org.junit.Test;

import com.jinject.event.exception.EventException;
import com.jinject.event.exception.ListenerException;
import com.jinject.event.impl.BinaryListener;
import com.jinject.event.impl.SimpleListener;
import com.jinject.event.impl.TernaryListener;
import com.jinject.utils.Events.BinaryTestEvent;
import com.jinject.utils.Events.SimpleTestEvent;

public class EventTest {
	
	@Test(expected=ListenerException.class)
	public void generalInstanciation(){
		SimpleTestEvent simpleEvent = new SimpleTestEvent();
		
		simpleEvent.addListener(new SimpleListener() {
			@Override
			public void execute(Object... params) {
				throw new ListenerException("Code reached");
			}
		});
		
		simpleEvent.fire();
	}
	
	@Test(expected=ListenerException.class)
	public void wrongListenerOnEvent(){
		BinaryTestEvent aze = new BinaryTestEvent();
		aze.addListener(new TernaryListener<String, Integer, Float>() {

			@Override
			public void execute(String param, Integer param2, Float param3) {
				// never reached
			}
			
		});
	}
	
	@Test(expected=ListenerException.class)
	public void binaryEvent(){
		BinaryTestEvent binary = new BinaryTestEvent();

		binary.addListener(new BinaryListener<String, Integer>() {

			@Override
			public void execute(String param, Integer param2) {
				throw new ListenerException("Code reached : " + param + " ->" + param2);
			}
			
		});

		binary.fire("test", 50);
	}
	
	@Test(expected=ListenerException.class)
	public void binaryEventWithBadParametersOnFire(){
		BinaryTestEvent binary = new BinaryTestEvent();

		binary.addListener(new BinaryListener<String, Integer>() {

			@Override
			public void execute(String param, Integer param2) {
				// never reached
			}
			
		});
		
		binary.fire("test", "ok");
	}
	
	@Test(expected=EventException.class)
	public void binaryEventWithBadParametersNumber(){
		BinaryTestEvent binary = new BinaryTestEvent();

		binary.addListener(new BinaryListener<String, Integer>() {

			@Override
			public void execute(String param, Integer param2) {
				// never reached
			}
			
		});
		
		binary.fire("test", 50, "ok");
	}
}
