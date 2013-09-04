package org.homemotion.auth;

import java.io.Serializable;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.homemotion.dao.spi.AbstractConfiguredItem;

/**
 * A group of users.
 */
public class UserGroup extends AbstractConfiguredItem implements Serializable {

	private static final long serialVersionUID = -3284638212223216652L;

	private String id;

	private Set<Role> roles = new HashSet<Role>(0);

	private Set<User> users = new HashSet<User>(0);


	public UserGroup(String id) {
		super(id, "security/UserGroups");
	}

	public UserGroup(String id, Set<Role> roles) {
		super(id, "security/UserGroups");
		this.roles.addAll(roles);
	}

	public String getIdentifier() {
		return this.id;
	}

	public void setIdentifier(String id) {
		if (id == null) {
			throw new IllegalArgumentException("id is required.");
		}
		this.id = id;
	}

	public void addRole(Role r) {
		if (!this.roles.contains(r)) {
			this.roles.add(r);
		}
	}

	public void removeRole(Role r) {
		this.roles.remove(r);
	}

	public Set<Role> getRoles() {
		return Collections.unmodifiableSet(this.roles);
	}

	public void setRoles(Set<Role> roles) {
		this.roles = new HashSet<Role>(roles);
	}

	@Override
	public int hashCode() {
		return Long.valueOf(getIdentifier()).hashCode();
	}

	public boolean equals(UserGroup secGroup) {
		return getIdentifier() == secGroup.getIdentifier();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (obj instanceof UserGroup) {
			UserGroup secGroup = (UserGroup) obj;
			return equals(secGroup);
		}

		return false;
	}

	public String toString() {
		return new ToStringBuilder(this).append("id", getIdentifier())
				.toString();
	}

	public Set<User> getUsers() {
		return Collections.unmodifiableSet(users);
	}

	void setUsers(Set<User> users) {
		this.users = new HashSet(users);
	}

	public boolean containsUser(User user) {
		return this.users.contains(user);
	}

	public boolean hasRole(Role role) {
		return this.roles.contains(role);
	}

	public void addUser(User u) {
		if (!this.users.contains(u)) {
			this.users.add(u);
		}
	}

}
