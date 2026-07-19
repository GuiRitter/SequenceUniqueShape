package io.github.guiritter.sequence_unique_shape;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

public final class Util {

	public static final ScriptEngineManager factory = new ScriptEngineManager();

	public static final ScriptEngine engine;

	static {
		engine = factory.getEngineByName("nashorn");
	}

	public static boolean isTruthy(String value) throws ScriptException {
		Object object = engine.eval("!!" + value);
		return (Boolean) object;
	}

	public static void main(String args[]) throws ScriptException {
		System.out.println("\"\"" + " · " + isTruthy("\"\""));
		System.out.println("null" + " · " + isTruthy("null"));
		System.out.println("undefined" + " · " + isTruthy("undefined"));
		System.out.println("0" + " · " + isTruthy("0"));
		System.out.println("NaN" + " · " + isTruthy("NaN"));
		System.out.println("\"false\"" + " · " + isTruthy("\"false\""));
		System.out.println("true" + " · " + isTruthy("true"));
		System.out.println("1" + " · " + isTruthy("1"));
		System.out.println("a" + " · " + isTruthy("\"a\""));
		System.out.println("{}" + " · " + isTruthy("{}"));
		System.out.println("[]" + " · " + isTruthy("[]"));
	}
}
