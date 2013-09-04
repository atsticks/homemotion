package org.homemotion.auth;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.homemotion.dao.spi.AbstractConfiguredItem;

/**
 * A role instance.
 */
public final class Action extends AbstractConfiguredItem implements
		Serializable {

	private static final long serialVersionUID = -1574628715506591010L;

	private Set<Role> roles = new HashSet<Role>();

	public Action(String id) {
		super(id, "security/Actions");
	}

	void addRole(Role role) {
		roles.add(role);
	}

	void removeRole(Role role) {
		roles.remove(role);
	}

	public String toString() {
		return new ToStringBuilder(this).append("id", getIdentifier())
				.toString();
	}

}
