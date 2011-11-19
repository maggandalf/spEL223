package org.alexsotob.spel223;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineFactory;

public class SpelScriptEngineFactory implements ScriptEngineFactory {

	private static final String VERSION = "1.0";
	private static final String SHORT_NAME = "spEL";
	private static final String LANGUAGE_NAME = "spEL";
	private static final String LANGUAGE_VERSION = "3.0.x";

	@Override
	public String getEngineName() {
		return "Spring EL Scripting Language";
	}

	@Override
	public String getEngineVersion() {
		return VERSION;
	}

	@Override
	public List<String> getExtensions() {
		return EXTENSIONS;
	}

	@Override
	public List<String> getMimeTypes() {
		return MIME_TYPES;
	}

	@Override
	public List<String> getNames() {
		return NAMES;
	}

	@Override
	public String getLanguageName() {
		return LANGUAGE_NAME;
	}

	@Override
	public String getLanguageVersion() {
		return LANGUAGE_VERSION;
	}

	@Override
	public Object getParameter(String key) {
		if (ScriptEngine.NAME.equals(key)) {
			return SHORT_NAME;
		} else if (ScriptEngine.ENGINE.equals(key)) {
			return getEngineName();
		} else if (ScriptEngine.ENGINE_VERSION.equals(key)) {
			return VERSION;
		} else if (ScriptEngine.LANGUAGE.equals(key)) {
			return LANGUAGE_NAME;
		} else if (ScriptEngine.LANGUAGE_VERSION.equals(key)) {
			return LANGUAGE_VERSION;
		} else if ("THREADING".equals(key)) {
			return "MULTITHREADED";
		} else {
			throw new IllegalArgumentException("Invalid key");
		}
	}

	@Override
	public String getMethodCallSyntax(String obj, String method, String... args) {
		String ret = obj + "." + method + "(";
		int len = args.length;
		if (len == 0) {
			ret += ")";
			return ret;
		}

		for (int i = 0; i < len; i++) {
			ret += args[i];
			if (i != len - 1) {
				ret += ",";
			} else {
				ret += ")";
			}
		}
		return ret;
	}

	@Override
	public String getOutputStatement(String toDisplay) {
		throw new UnsupportedOperationException();
	}

	@Override
	public String getProgram(String... statements) {
		StringBuilder ret = new StringBuilder();
		int len = statements.length;
		for (int i = 0; i < len; i++) {
			ret.append(statements[i]);
			ret.append('\n');
		}
		return ret.toString();
	}

	@Override
	public ScriptEngine getScriptEngine() {
		return new SpelScriptEngineImpl();
	}

	private static final List<String> NAMES;
	private static final List<String> EXTENSIONS;
	private static final List<String> MIME_TYPES;

	static {
		List<String> n = new ArrayList(2);
		n.add(SHORT_NAME);
		n.add(LANGUAGE_NAME);
		NAMES = Collections.unmodifiableList(n);

		n = new ArrayList<String>(1);
		n.add("spEL");
		EXTENSIONS = Collections.unmodifiableList(n);

		n = new ArrayList<String>(1);
		n.add("application/x-spEL");
		MIME_TYPES = Collections.unmodifiableList(n);
	}

}
