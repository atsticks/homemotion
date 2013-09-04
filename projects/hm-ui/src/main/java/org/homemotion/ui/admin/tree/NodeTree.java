package org.homemotion.ui.admin.tree;

//import javax.faces.application.FacesMessage;
//import javax.faces.bean.ManagedBean;
//import javax.faces.bean.SessionScoped;
//import javax.faces.context.FacesContext;
//import javax.inject.Inject;
//import javax.servlet.http.HttpServletRequest;
//
//import org.homemotion.dao.ItemManager;
//import org.homemotion.tree.Node;
//import org.homemotion.tree.TreeManager;
//import org.homemotion.ui.widgets.AbstractPageControl;
//import org.primefaces.event.DragDropEvent;
//import org.primefaces.event.NodeSelectEvent;
//import org.primefaces.model.DefaultTreeNode;
//import org.primefaces.model.TreeNode;
//
//@ManagedBean
//@SessionScoped
//public final class NodeTree extends AbstractPageControl<Node> {
//
//	private static final long serialVersionUID = 1L;
//	
//	@Inject
//	private TreeManager treeManager;
//	
//	public NodeTree() {
//		super("/admin/tree/Node");
//	}
//
//	private TreeNode root;
//	
//	private TreeNode selectedNode;
//	
//	private synchronized void initTree() {
//		if(this.root==null){
//			Node rootCat = treeManager.getRoot();
//			System.out.println("Created root.");
//			this.root = new DefaultTreeNode(rootCat, null);
//			for (Node cat : rootCat.getChildren()) {
//				buildNode(cat, root);
//			}
//		}
//	}
//
//	private void buildNode(Node cat, TreeNode parent) {
//		TreeNode newNode = new DefaultTreeNode(cat, parent);
//		System.out.println("Created child: " + cat.getName()+" of " + parent +"[IDs:"+cat.getId()+'/'+((Node)parent.getData()).getId()+"],[Hashes:"+cat.hashCode()+'/'+parent.hashCode()+"]");
//		for (Node childCat : cat.getChildren()) {
//			buildNode(childCat, newNode);
//		}
//	}
//
//	public void onDragDrop(DragDropEvent event) {  
//        TreeNode node = (TreeNode) event.getData();  
//        Node dataNode = (Node)node.getData();
//        Node targetParent = (Node)node.getParent().getData();
//        Node oldParent = dataNode.getParent();
//        dataNode = getItemManager().get(dataNode.getId());
//        oldParent = getItemManager().get(oldParent.getId());
//        targetParent = getItemManager().get(targetParent.getId());
//        dataNode.setParent(targetParent);
//        getItemManager().update(oldParent);
//        getItemManager().update(targetParent);
//        getItemManager().update(dataNode);
//        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "DragDrop", node + " moved to " + node.getParent());  
//        FacesContext.getCurrentInstance().addMessage(null, message);  
//    }  
//	
//	public String reset(){
//		this.root = null;
//		return null;
//	}
//	
//	public TreeNode getRoot() {
//		initTree();
//		return root;
//	}
//
//	public TreeNode getSelectedNode() {
//		return selectedNode;
//	}
//
//	public void setSelectedNode(TreeNode selectedNode) {
//		this.selectedNode = selectedNode;
//	}
//
//	public void onNodeSelect(NodeSelectEvent event) {
//		this.selectedNode = event.getTreeNode();
//	}
//	public void onNodeUnselect(NodeSelectEvent event) {
//		this.selectedNode = null;
//	}
//
//	@Override
//	public String createNew() {
//		HttpServletRequest hrequest = (HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest();
//		Node item = createNewItem();
//		if (item == null) {
//			throw new IllegalStateException("createNew did not create a new item.");
//		}
//		hrequest.setAttribute(getItemName(), item);
//		if (this.selectedNode != null) {
//			hrequest.setAttribute(getItemName()+"_parent",this.selectedNode.getData());
//		} else {
//			hrequest.setAttribute(getItemName()+"_parent",this.root.getData());
//		}
//		return getCreateTarget();
//	}
//	
//	@Override
//	protected Node createNewItem() {
//		initTree();
//		return new Node();
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
//}
