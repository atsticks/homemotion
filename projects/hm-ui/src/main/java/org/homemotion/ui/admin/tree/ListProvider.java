package org.homemotion.ui.admin.tree;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import javax.faces.bean.ApplicationScoped;
//import javax.faces.bean.ManagedBean;
//import javax.faces.model.SelectItem;
//import javax.inject.Inject;
//
//import org.homemotion.tree.Node;
//import org.homemotion.tree.TreeManager;
//
//@ManagedBean(name="NodeLists")
//@ApplicationScoped
//public final class ListProvider {
//
//	@Inject
//	private TreeManager treeManager;
//	
//	public List<SelectItem> getAllNodes() {
//		List<SelectItem> items = new ArrayList<SelectItem>();
//		for (Node cat : treeManager.getAllItems()) {
//			items.add(new SelectItem(cat,cat.getFullName()));
//		}
//		return items;
//	}
//
//}
