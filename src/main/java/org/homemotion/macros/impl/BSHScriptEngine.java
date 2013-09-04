package org.homemotion.macros.impl;

import org.homemotion.lang.Var;
import org.homemotion.macros.MacroEngine;

import bsh.Interpreter;

public final class BSHScriptEngine implements MacroEngine{

	private Interpreter engine = new Interpreter();
	
	public BSHScriptEngine() {
		engine.setStrictJava(false);
		engine.getClassManager().cacheClassInfo("Var", Var.class);
	}

	public Object execute(String script) throws Exception{
		return engine.eval(script);
	}


	public String getDescription() {
		return "BeanShell Java Engine.";
	}


	public String getName() {
		return "Java";
	}

}
