package org.homemotion.tree.impl;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import org.homemotion.building.impl.ZoneManagerImpl;
import org.homemotion.common.system.Container;
import org.junit.Assert;
import org.junit.Test;

public class TreeManagerImplTest {

	@Test
	public void testTreeManagerImpl() throws InterruptedException {
		Container.start();
		assertNotNull(Container.getInstance(ZoneManagerImpl.class));
		synchronized (this) {
			while (true) {
				Thread.sleep(1000L);
			}
		}
	}

	@Test
	public void testGetBuilding() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetIdentifiers() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetBinding() {
		fail("Not yet implemented");
	}

	@Test
	public void testCreate() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetString() {
		fail("Not yet implemented");
	}

	@Test
	public void testDelete() {
		fail("Not yet implemented");
	}

	@Test
	public void testFindItemsString() {
		fail("Not yet implemented");
	}

	@Test
	public void testFindItemsMapOfStringObject() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetAllItems() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetSize() {
		fail("Not yet implemented");
	}

	@Test
	public void testUpdate() {
		fail("Not yet implemented");
	}

	@Test
	public void testRefresh() {
		fail("Not yet implemented");
	}

	@Test
	public void testIsPersistent() {
		fail("Not yet implemented");
	}

	@Test
	public void testIterator() {
		fail("Not yet implemented");
	}

}
