package org.homemotion.macros;

import org.homemotion.dao.ItemManager;

public interface MacroManager extends ItemManager<Macro, String>{
	
//	public Macro create(String name, String description);
	public Object execute(Macro macro) throws Exception;
	public MacroEngine getExecutionEngine(String id);
	public String[] getAllExecutionEngineNames();
//	public Macro getByName(String macroName);
}
