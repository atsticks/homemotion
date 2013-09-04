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
package org.homemotion.auth;

import java.util.Collection;
import java.util.List;

import org.homemotion.dao.ItemManager;

/**
 * EN: Service methods Interface for working with <b>Security data</b> dependend
 * DAOs.<br>
 * DE: Service Methoden Implementierung fuer die <b>Security data</b>
 * betreffenden DAOs.<br>
 * 
 * @author bbruhns
 * @author sgerth
 */
public interface SecurityService {

	public User changePassword(String uid, String pwd);

	public boolean isAnonymousUser(User user);

	public User getAnonymousUser();

	/**
	 * Gets a list of Users where the LastName name contains the %string% .<br>
	 * 
	 * @param nameExpression
	 *            name expression
	 * @return List of Users
	 */
	public Collection<User> getUsersByFullname(String nameExpression);

	/**
	 * Gets a list of Users where the LoginName contains the %string% .
	 * 
	 * @param nameExpression
	 *            name expression
	 * @return List of Users
	 */
	public Collection<User> getUsersById(String nameExpression);

	/**
	 * Get a list of Users by its emailaddress with the like SQL operator.
	 * 
	 * @param email
	 *            Email Address
	 * @return List of Users
	 */
	public Collection<User> getUsersByEmail(String emailExpression);

	/**
	 * Get an User by its LoginName.
	 * 
	 * @param userId
	 *            UserName
	 * @return User
	 */
	public User getUser(final String userId);

	public Collection<Action> getActionsByUser(User user);

	public Collection<UserGroup> getGroupsForUser(User user);

	public boolean isInRole(User user, Role role);

	public Collection<Action> getActions(String nameExpression);

	public Collection<User> getUsers(String nameExpression);

	public Collection<UserGroup> getUserGroups(String nameExpression);

	public Collection<Role> getRoles(String value);

	public Collection<Action> getActions();

	public Collection<User> getUsers();

	public Collection<UserGroup> getUserGroups();

	public Collection<Role> getRoles();
	
	public boolean isOneAuthorized(User user, Role... roles);
	
	public boolean isAllAuthorized(User user, Role... roles);

}
