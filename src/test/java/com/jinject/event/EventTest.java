package com.jinject.event;

import org.junit.Test;

import com.jinject.event.exception.EventException;
import com.jinject.event.exception.ListenerException;
import com.jinject.event.impl.BinaryListener;
import com.jinject.event.impl.SimpleListener;
import com.jinject.event.impl.TernaryListener;
import com.jinject.utils.Events.BinaryTestEvent;
import com.jinject.utils.Events.SimpleTestEvent;
import com.jinject.utils.ReachedException;

public class EventTest {
	
	@Test(expected=ReachedException.class)
	public void generalInstanciation(){
		SimpleTestEvent simpleEvent = new SimpleTestEvent();
		
		simpleEvent.addListener(new SimpleListener() {
			@Override
			public void execute(Object... params) {
				throw new ReachedException();
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
				throw new ReachedException();
			}
		});
		// Event constraints to 2 but the listener above needs 3, so for now it raise an exception
		aze.fire("String", 100);
	}
	
	@Test(expected=ReachedException.class)
	public void binaryEvent(){
		BinaryTestEvent binary = new BinaryTestEvent();

		binary.addListener(new BinaryListener<String, Integer>() {

			@Override
			public void execute(String param, Integer param2) {
				throw new ReachedException();
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
				throw new ReachedException();
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
				throw new ReachedException();
			}
			
		});
		
		binary.fire("test", 50, "ok");
	}
}
