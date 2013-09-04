

import java.util.Map;

import org.homemotion.di.Registry;
import org.homemotion.events.EventManager;
import org.homemotion.macros.Macro;
import org.homemotion.macros.MacroContext;
import org.homemotion.macros.MacroEngine;
import org.homemotion.macros.MacroManager;

public final class RUNNER {

	private static MacroManager macroManager;
	private static EventManager eventManager;
	
	private RUNNER(){
		
	}
	
	public static void exec(String macroName) throws Exception{
		init();
		Macro macro = macroManager.getByName(macroName);
		
		MacroEngine engine = macroManager.getExecutionEngine(macro.getEngine());
		if(engine==null){
			throw new IllegalStateException("ExecutionEngine missing: " + macro.getEngine());
		}
		MacroContext.setValue(Macro.class.getName(),macro);
		MacroContext.setValue(MacroEngine.class.getName(),engine);
		Object result = engine.execute(macro.getScript());
		MacroContext.setValue(Macro.class.getName()+"#"+macroName, result);
	}
	
	public static Object execMacro(String macroName) throws Exception{
		init();
		Macro macro = macroManager.getByName(macroName);
		
		MacroEngine engine = macroManager.getExecutionEngine(macro.getEngine());
		if(engine==null){
			throw new IllegalStateException("ExecutionEngine missing: " + macro.getEngine());
		}
		MacroContext.setValue(Macro.class.getName(),macro);
		MacroContext.setValue(MacroEngine.class.getName(),engine);
		return engine.execute(macro.getScript());
	}
	
	public static Object execMacro(String macroName, Map<String,Object> initialContext) throws Exception{
		init();
		Macro macro = macroManager.getByName(macroName);
		MacroEngine engine = macroManager.getExecutionEngine(macro.getEngine());
		if(engine==null){
			throw new IllegalStateException("ExecutionEngine missing: " + macro.getEngine());
		}
		MacroContext.setValue(Macro.class.getName(),macro);
		MacroContext.setValue(MacroEngine.class.getName(),engine);
		MacroContext.init(initialContext);
		return engine.execute(macro.getScript());
	}
	
	private static void init() {
		if(macroManager==null){
			macroManager = Registry.getInstance(MacroManager.class);
		}
		if(eventManager==null){
			eventManager = Registry.getInstance(EventManager.class);
		}
	}

}
