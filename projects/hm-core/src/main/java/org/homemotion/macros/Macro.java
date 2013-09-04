package org.homemotion.macros;

import java.io.Serializable;

import org.homemotion.dao.spi.AbstractConfiguredItem;

public class Macro extends AbstractConfiguredItem implements Serializable{

	private static final long serialVersionUID = -1337105038911661576L;

	public static final String DEFAULT_SCRIPT = "import org.homemotion.commands;\n"
			+ "PRINT.out(\"Script executed\");\n"
			+ "System.err.println(\"Some errors\");\n";
	public static final String DEFAULT_ENGINE = "Java";

	private String engine = DEFAULT_ENGINE;

	private String script = DEFAULT_SCRIPT;

	public Macro(String id, String binding) {
		super(id, binding);
	}
	
	public String getScript() {
		return this.script;
	}

	public void setScript(String script) {
		this.script = script;
	}

	public void setEngine(String engine) {
		if (engine == null) {
			throw new IllegalArgumentException("Engine null.");
		}
		this.engine = engine;
	}

	public String getEngine() {
		return this.engine;
	}

}
