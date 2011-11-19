package org.alexsotob.spEL223;

import static org.junit.Assert.assertThat;
import static org.hamcrest.CoreMatchers.instanceOf;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;

import org.alexsotob.spel223.SpelScriptEngineImpl;
import org.junit.Test;



public class ScriptEngineManagerBehaviour {

	@Test
	public void shouldReturnSpelScriptEngine() {
		
		ScriptEngineManager factory = new ScriptEngineManager();
		ScriptEngine scriptEngine = factory.getEngineByName("spEL");
		assertThat(scriptEngine, instanceOf(SpelScriptEngineImpl.class));
	}

}
