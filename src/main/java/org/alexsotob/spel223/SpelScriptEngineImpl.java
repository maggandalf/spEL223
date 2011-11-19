package org.alexsotob.spel223;

import static org.alexsotob.spel223.IOUtils.readFully;

import java.io.Reader;
import java.util.HashMap;
import java.util.Map;

import javax.script.AbstractScriptEngine;
import javax.script.Bindings;
import javax.script.Compilable;
import javax.script.CompiledScript;
import javax.script.ScriptContext;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineFactory;
import javax.script.ScriptException;
import javax.script.SimpleBindings;

import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;

public class SpelScriptEngineImpl extends AbstractScriptEngine implements
		Compilable {

	public static final String ROOT_OBJECT = "root";

	private volatile SpelScriptEngineFactory factory;

	@Override
	public Object eval(String script, ScriptContext context)
			throws ScriptException {
		Expression expression = parse(script);
		return evalExpression(expression, context);

	}

	private Expression parse(String script) {
		ExpressionParser expressionParser = getExpressionParser();
		return expressionParser.parseExpression(script);
	}

	private Object evalExpression(Expression expression,
			ScriptContext scriptContext) {

		StandardEvaluationContext standardEvaluationContext = getStandardEvaluationContext(scriptContext);
		return expression.getValue(standardEvaluationContext);

	}

	private StandardEvaluationContext getStandardEvaluationContext(
			ScriptContext scriptContext) {

		StandardEvaluationContext standardEvaluationContext = new StandardEvaluationContext(
				scriptContext.getAttribute(ROOT_OBJECT));

		standardEvaluationContext.setVariables(getVariables(scriptContext));

		return standardEvaluationContext;
	}

	private Map<String, Object> getVariables(ScriptContext scriptContext) {

		Map<String, Object> variables = new HashMap<String, Object>();

		if (scriptContext.getBindings(ScriptContext.GLOBAL_SCOPE) != null) {
			variables.putAll(scriptContext
					.getBindings(ScriptContext.GLOBAL_SCOPE));
		}

		if (scriptContext.getBindings(ScriptContext.ENGINE_SCOPE) != null) {
			variables.putAll(scriptContext
					.getBindings(ScriptContext.ENGINE_SCOPE));
		}

		return variables;

	}

	private ExpressionParser getExpressionParser() {
		return new SpelExpressionParser();
	}

	@Override
	public Object eval(Reader reader, ScriptContext context)
			throws ScriptException {
		return eval(readFully(reader), context);
	}

	@Override
	public Bindings createBindings() {
		return new SimpleBindings();
	}

	@Override
	public ScriptEngineFactory getFactory() {
		if (factory == null) {
			synchronized (this) {
				if (factory == null) {
					factory = new SpelScriptEngineFactory();
				}
			}
		}
		return factory;
	}

	private class SpelCompiledScript extends CompiledScript {

		private final Expression expression;

		public SpelCompiledScript(Expression expression) {
			this.expression = expression;
		}

		@Override
		public Object eval(ScriptContext context) throws ScriptException {
			return evalExpression(expression, context);
		}

		@Override
		public ScriptEngine getEngine() {
			return SpelScriptEngineImpl.this;
		}

	}

	@Override
	public CompiledScript compile(String script) throws ScriptException {
		return new SpelCompiledScript(parse(script));
	}

	@Override
	public CompiledScript compile(Reader script) throws ScriptException {
		return compile(readFully(script));
	}
}
