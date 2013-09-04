package org.homemotion.dao.spi;

//import java.util.Collections;
//import java.util.Date;
//import java.util.Iterator;
//import java.util.List;
//
//import javax.inject.Inject;
//import javax.persistence.EntityManager;
//import javax.persistence.PersistenceContext;
//import javax.persistence.Query;
//
//
//public abstract class AbstractJPAItemManagerImpl<T extends AbstractItem>
//		implements ItemManager<T, Long> {
//
//	protected final Logger logger = Logger.getLogger(getClass());
//
//	@Inject
//	@PersistenceContext
//	private EntityManager entityManager;
//
//	protected abstract Class<T> getItemClass();
//
//	public EntityManager getEntityManager() {
//		return this.entityManager;
//	}
//
//	public void create(T item) {
//		setCreationFields(item);
//		try {
//			this.entityManager.persist(item);
//		} catch (Exception e) {
//			throw new IllegalStateException("Failed to create instance: "
//					+ item, e);
//		}
//	}
//
//	public T getByName(String name) {
//		List<T> itemsFound = findItems(name);
//		if (itemsFound.isEmpty()) {
//			return null;
//		}
//		if (itemsFound.size() == 1) {
//			return itemsFound.get(0);
//		} else {
//			throw new IllegalArgumentException(
//					"Name given was not unique for item type " + getItemClass()
//							+ " , name was '" + name + "'.");
//		}
//	}
//
//	public void delete(T item) {
//		try {
//			item = this.entityManager.merge(item);
//			this.entityManager.remove(item);
//		} catch (Exception e) {
//			throw new IllegalStateException("Failed to delete instance: "
//					+ item, e);
//		}
//	}
//
//	@SuppressWarnings("unchecked")
//	public List<T> findItems(String nameExpression) {
//		Query query = this.entityManager.createQuery("SELECT item FROM "
//				+ getItemClass().getSimpleName()
//				+ " item WHERE item.name LIKE :nameExpression");
//		query.setParameter("nameExpression", nameExpression);
//		return query.getResultList();
//	}
//
//	public T get(Long id) {
//		return this.entityManager.find(getItemClass(), id);
//	}
//
//	@SuppressWarnings("unchecked")
//	public List<T> getAllItems() {
//		Query query = this.entityManager.createQuery(getAllItemQuery());
//		return query.getResultList();
//	}
//
//	@Override
//	public Iterator<T> iterator() {
//		return Collections.unmodifiableList(this.getAllItems()).iterator();
//	}
//
//	@Override
//	public long getSize() {
//		Query query = this.entityManager.createQuery("SELECT count(item) FROM "
//				+ getItemClass().getSimpleName() + " item");
//		return (Long) query.getSingleResult();
//	}
//
//	protected String getAllItemQuery() {
//		return "SELECT item FROM " + getItemClass().getSimpleName() + " item";
//	}
//
//	public T update(T item) {
//		try {
//			return this.entityManager.merge(item);
//		} catch (Exception e) {
//			throw new IllegalStateException("Failed to update instance: "
//					+ item, e);
//		}
//	}
//
//	public T refresh(T item) {
//		try {
//			this.entityManager.refresh(item);
//			return item;
//		} catch (Exception e) {
//			throw new IllegalStateException(
//					"Nested transaction not supported!", e);
//		}
//	}
//
//	public boolean isPersistent(T item) {
//		try {
//			return this.entityManager.contains(item);
//		} catch (Exception e) {
//			return false;
//		}
//	}
//
//	protected String getCurrentUserID() {
//		return "N/A";
//	}
//
//	protected void setCreationFields(T item) {
//		if (item instanceof AbstractItem) {
//			((AbstractItem) item).setCreatedFrom(getCurrentUserID());
//			((AbstractItem) item).setUpdatedFrom(((AbstractItem) item)
//					.getCreatedFrom());
//		}
//	}
//
//	protected void setUpdateFields(T item) {
//		if (item instanceof AbstractItem) {
//			((AbstractItem) item).setUpdatedDT(new Date());
//			((AbstractItem) item).setUpdatedFrom(getCurrentUserID());
//		}
//	}
//
//}
