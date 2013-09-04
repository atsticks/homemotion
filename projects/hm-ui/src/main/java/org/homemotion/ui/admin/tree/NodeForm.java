package org.homemotion.ui.admin.tree;
//
//import javax.faces.bean.ManagedBean;
//import javax.faces.bean.ManagedProperty;
//import javax.faces.bean.SessionScoped;
//import javax.faces.context.FacesContext;
//import javax.inject.Inject;
//import javax.servlet.http.HttpServletRequest;
//
//import org.homemotion.dao.ItemManager;
//import org.homemotion.tree.Node;
//import org.homemotion.tree.TreeManager;
//import org.homemotion.ui.widgets.AbstractFormPage;
//
//@ManagedBean
//@SessionScoped
//public final class NodeForm extends AbstractFormPage<Node> {
//
//	private Node parent;
//	
//	@Inject
//	private TreeManager treeManager;
//
//	@ManagedProperty(value = "#{nodeTree}")
//	private NodeTree treePage;
//
//	public NodeForm() {
//		super("/admin/tree/Node", Node.class);
//	}
//
//	public Node getParent() {
//		return this.parent;
//	}
//
//	public Node getItem() {
//		HttpServletRequest hrequest = (HttpServletRequest) FacesContext
//				.getCurrentInstance().getExternalContext().getRequest();
//		Object found = hrequest.getAttribute(getItemName() + "_parent");
//		if (found != null && found instanceof Node) {
//			this.parent = (Node) found;
//			hrequest.removeAttribute(getItemName() + "_parent");
//		}
//		return super.getItem();
//	}
//
//	public String persist() {
//		Node node = getItem();
//		if (node != null) {
//			getItemManager().create(node);
//			if (this.parent != null) {
//				this.parent =  treeManager.get(this.parent.getId());
//				this.parent.addChild(node);
//				getItemManager().update(this.parent);
//			}
//		}
//		setItem(null);
//		this.parent = null;
//		this.treePage.reset();
//		return getPersistedTarget();
//	}
//
//	@Override
//	protected ItemManager<Node> getItemManager() {
//		return treeManager;
//	}
//
//	@Override
//	protected String getListTarget() {
//		return getItemName() + "Tree";
//	}
//
//	@Override
//	protected Node createNewItem() {
//		return new Node();
//	}
//
//	public void setTreePage(NodeTree treePage) {
//		this.treePage = treePage;
//	}
//
//}
