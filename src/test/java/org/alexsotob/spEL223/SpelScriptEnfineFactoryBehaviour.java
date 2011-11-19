package org.alexsotob.spEL223;

import static org.junit.Assert.assertThat;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.is;

import javax.script.ScriptEngine;

import org.alexsotob.spel223.SpelScriptEngineFactory;
import org.alexsotob.spel223.SpelScriptEngineImpl;
import org.junit.Test;


public class SpelScriptEnfineFactoryBehaviour {

	@Test
	public void shouldReturnSpelScriptEngine() {
		
		SpelScriptEngineFactory spelScriptEngineFactory = new SpelScriptEngineFactory();
		ScriptEngine scriptEngine = spelScriptEngineFactory.getScriptEngine();
		
		assertThat(scriptEngine, instanceOf(SpelScriptEngineImpl.class));
		assertThat((String)spelScriptEngineFactory.getParameter(ScriptEngine.NAME), is("spEL"));
	}

}
