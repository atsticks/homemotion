package org.homemotion.auth;

import java.io.Serializable;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.homemotion.dao.spi.AbstractConfiguredItem;

/**
 * A role instance.
 */
public final class Role extends AbstractConfiguredItem implements
		Serializable {

	private static final long serialVersionUID = -1574628715506591010L;

	private String id;

	private Set<Action> actions = new HashSet<Action>(0);

	public Role(String id) {
		super(id, "security/Roles");
	}


	public void addAction(Action action) {
		actions.add(action);
		action.addRole(this);
	}

	public void removeAction(Action action) {
		actions.remove(action);
		action.removeRole(this);
	}

	@Override
	public int hashCode() {
		return id.hashCode();
	}

	public boolean equals(Role secRight) {
		return id.equals(secRight.id);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (obj instanceof Role) {
			Role secRight = (Role) obj;
			return equals(secRight);
		}

		return false;
	}

	public String toString() {
		return new ToStringBuilder(this).append("id", getIdentifier())
				.toString();
	}

	public Collection<Action> getActions() {
		return Collections.unmodifiableCollection(this.actions);
	}

}
