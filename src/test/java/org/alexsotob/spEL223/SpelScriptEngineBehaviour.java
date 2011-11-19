package org.alexsotob.spEL223;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.core.IsInstanceOf.instanceOf;
import static org.junit.Assert.assertThat;

import java.util.Calendar;

import javax.script.ScriptContext;
import javax.script.ScriptException;
import javax.script.SimpleScriptContext;

import org.alexsotob.spel223.SpelScriptEngineImpl;
import org.junit.Test;

public class SpelScriptEngineBehaviour {

	@Test
	public void shouldNavigateThroughProperties() throws ScriptException {

		SpelScriptEngineImpl scriptEngineImpl = new SpelScriptEngineImpl();
		ScriptContext scriptContext = new SimpleScriptContext();
		scriptContext.setAttribute(SpelScriptEngineImpl.ROOT_OBJECT,
				new Inventor("Mike Tesla"), ScriptContext.ENGINE_SCOPE);

		String name = (String) scriptEngineImpl.eval("#root.name",
				scriptContext);
		assertThat(name, is("Mike Tesla"));

	}

	@Test
	public void shouldExecuteLogicalOperations() throws ScriptException {

		SpelScriptEngineImpl scriptEngineImpl = new SpelScriptEngineImpl();

		Boolean trueValue = (Boolean) scriptEngineImpl.eval("true or false");
		assertThat(trueValue, is(true));

	}

	@Test
	public void shouldExecuteMathematicalOperations() throws ScriptException {

		SpelScriptEngineImpl scriptEngineImpl = new SpelScriptEngineImpl();

		Integer two = (Integer) scriptEngineImpl.eval("1+1");
		assertThat(two, is(2));

	}

	@Test
	public void shouldEvalVariables() throws ScriptException {
		SpelScriptEngineImpl scriptEngineImpl = new SpelScriptEngineImpl();

		ScriptContext scriptContext = new SimpleScriptContext();
		scriptContext.setAttribute("name", "Mike Tesla",
				ScriptContext.ENGINE_SCOPE);

		String name = (String) scriptEngineImpl.eval("#name", scriptContext);
		assertThat(name, is("Mike Tesla"));
	}

	@Test
	public void shouldUseTypes() throws ScriptException {

		SpelScriptEngineImpl scriptEngineImpl = new SpelScriptEngineImpl();
		Calendar calendar = (Calendar) scriptEngineImpl
				.eval("T(java.util.Calendar).getInstance()");

		assertThat(calendar, instanceOf(Calendar.class));

	}

	private static class Inventor {

		private String name;

		public Inventor(String inventor) {
			this.name = inventor;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

	}

}
