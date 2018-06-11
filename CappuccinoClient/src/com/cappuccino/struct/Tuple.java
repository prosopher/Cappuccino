package com.cappuccino.struct;

import java.util.ArrayList;

public class Tuple extends ArrayList<Object> {
	private static final long serialVersionUID = 4071091559492242865L;

	public Tuple(Object... args) {
		for (Object arg : args) {
			add(arg);
		}
	}

}
