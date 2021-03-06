/**
 * Copyright 2010 the original author or authors.
 * 
 * This file is part of Zksample2. http://zksample2.sourceforge.net/
 *
 * Zksample2 is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * Zksample2 is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with Zksample2.  If not, see <http://www.gnu.org/licenses/gpl.html>.
 */
package org.homemotion.auth.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.enterprise.inject.Default;
import javax.inject.Inject;
import javax.inject.Singleton;

import org.homemotion.auth.Action;
import org.homemotion.auth.ActionManager;
import org.homemotion.auth.Role;
import org.homemotion.auth.RoleManager;
import org.homemotion.auth.SecurityService;
import org.homemotion.auth.User;
import org.homemotion.auth.UserGroup;
import org.homemotion.auth.UserGroupManager;
import org.homemotion.auth.UserManager;
import org.homemotion.util.Encrypter;

/**
 * Implementation of {@link SecurityService} based on JSON configuration.
 */
@Singleton
@Default
public class SecurityServiceImpl implements SecurityService {

	@Inject
	private Encrypter encrypter;

	@Inject
	private ActionManager actions;

	@Inject
	private RoleManager roles;

	@Inject
	private UserManager users;

	@Inject
	private UserGroupManager userGroups;

	public User getAnonymousUser() {
		return User.ANONYMOUS;
	}

	public boolean isAnonymousUser(User user) {
		return user.equals(User.ANONYMOUS);
	}

	@Override
	public User changePassword(String uid, String pwd) {
		User user = getUser(uid);
		if (user == null) {
			throw new IllegalArgumentException("No such user.");
		}
		user.setPassword(encrypter.encrypt(pwd));
		return user;
	}

	@Override
	public Collection<User> getUsersByFullname(String nameExpression) {
		List<User> result = new ArrayList<User>();
		for (User user : users) {
			if (nameExpression == null) {
				if (user.getFullname() == null) {
					result.add(user);
				}
			} else {
				if (user.getFullname() != null
						&& user.getFullname().matches(nameExpression)) {
					result.add(user);
				}
			}
		}
		return result;
	}

	@Override
	public Collection<User> getUsersById(String nameExpression) {
		List<User> result = new ArrayList<User>();
		for (User user : users) {
			if (nameExpression == null) {
				if (user.getIdentifier() == null) {
					result.add(user);
				}
			} else {
				if (user.getIdentifier() != null
						&& user.getIdentifier().matches(nameExpression)) {
					result.add(user);
				}
			}
		}
		return result;
	}

	@Override
	public Collection<User> getUsersByEmail(String nameExpression) {
		List<User> result = new ArrayList<User>();
		for (User user : users) {
			if (nameExpression == null) {
				if (user.getEmail() == null) {
					result.add(user);
				}
			} else {
				if (user.getEmail() != null
						&& user.getEmail().matches(nameExpression)) {
					result.add(user);
				}
			}
		}
		return result;
	}

	@Override
	public User getUser(String userId) {
		for (User user : users) {
			if (user.getIdentifier().equalsIgnoreCase(userId)) {
				return user;
			}
		}
		return null;
	}

	@Override
	public Collection<Action> getActionsByUser(User user) {
		Set<Action> result = new HashSet<Action>();
		for (Role role : user.getRoles()) {
			result.addAll(role.getActions());
		}
		for (UserGroup group : getGroupsForUser(user)) {
			for (Role role : group.getRoles()) {
				result.addAll(role.getActions());
			}
		}
		return result;
	}

	@Override
	public Collection<UserGroup> getGroupsForUser(User user) {
		List<UserGroup> result = new ArrayList<UserGroup>();
		for (UserGroup group : userGroups) {
			if (group.containsUser(user)) {
				result.add(group);
			}
		}
		return result;
	}

	@Override
	public boolean isInRole(User user, Role role) {
		if (user.hasRole(role)) {
			return true;
		}
		for (UserGroup group : getGroupsForUser(user)) {
			if (group.hasRole(role)) {
				return true;
			}
		}
		return false;
	}

	@Override
	public Collection<Action> getActions(String nameExpression) {
		List<Action> result = new ArrayList<Action>();
		for (Action action : actions) {
			if (action.getIdentifier().matches(nameExpression)) {
				result.add(action);
			}
		}
		return result;
	}

	@Override
	public Collection<User> getUsers(String nameExpression) {
		List<User> result = new ArrayList<User>();
		for (User user : users) {
			if (user.getIdentifier().matches(nameExpression)) {
				result.add(user);
			}
		}
		return result;
	}

	@Override
	public Collection<UserGroup> getUserGroups(String nameExpression) {
		List<UserGroup> result = new ArrayList<UserGroup>();
		for (UserGroup usergroup : userGroups) {
			if (usergroup.getIdentifier().matches(nameExpression)) {
				result.add(usergroup);
			}
		}
		return result;
	}

	@Override
	public Collection<Role> getRoles(String nameExpression) {
		List<Role> result = new ArrayList<Role>();
		for (Role role : roles) {
			if (role.getIdentifier().matches(nameExpression)) {
				result.add(role);
			}
		}
		return result;
	}

	@Override
	public Collection<Action> getActions() {
		return this.actions.getAllItems();
	}

	@Override
	public Collection<User> getUsers() {
		return this.users.getAllItems();
	}

	@Override
	public Collection<UserGroup> getUserGroups() {
		return this.userGroups.getAllItems();
	}

	@Override
	public Collection<Role> getRoles() {
		return this.roles.getAllItems();
	}

	@Override
	public boolean isOneAuthorized(User user, Role... roles) {
		for (Role role : roles) {
			if (user.hasRole(role)) {
				return true;
			}
		}
		for (UserGroup group : getGroupsForUser(user)) {
			for (Role role : roles) {
				if (group.hasRole(role)) {
					return true;
				}
			}
		}
		return false;
	}

	@Override
	public boolean isAllAuthorized(User user, Role... roles) {
		Set<Role> authorized = new HashSet<Role>();
		for (Role role : roles) {
			if (user.hasRole(role)) {
				authorized.add(role);
			}
		}
		for (UserGroup group : getGroupsForUser(user)) {
			for (Role role : roles) {
				if (group.hasRole(role)) {
					authorized.add(role);
				}
			}
		}
		return authorized.size() == roles.length;
	}

}
