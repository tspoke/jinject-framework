package com.jinject.utils;

import com.jinject.event.impl.BinaryEvent;
import com.jinject.event.impl.SimpleEvent;
import com.jinject.event.impl.UnaryEvent;

public class Events {
	public static class SimpleTestEvent extends SimpleEvent {}
	public static class UnaryTestEvent extends UnaryEvent<String> {}
	public static class BinaryTestEvent extends BinaryEvent<String, Integer> {}
	public static class BinaryTestComplexEvent extends BinaryEvent<String, IOther> {}
	public static class BinaryTestComplexInheritedEvent extends BinaryEvent<String, Other> {}
	public static class TernaryTestEvent extends BinaryEvent<String, Integer> {}
}
