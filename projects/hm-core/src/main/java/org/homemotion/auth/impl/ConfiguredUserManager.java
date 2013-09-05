package org.homemotion.auth.impl;

import java.util.Map;

import javax.inject.Inject;

import org.homemotion.auth.Role;
import org.homemotion.auth.RoleManager;
import org.homemotion.auth.User;
import org.homemotion.auth.UserGroup;
import org.homemotion.auth.UserGroupManager;
import org.homemotion.auth.UserManager;
import org.homemotion.common.config.ConfigSection;
import org.homemotion.common.config.Configuration;
import org.homemotion.common.config.ConfigurationService;
import org.homemotion.common.config.Row;
import org.homemotion.dao.spi.AbstractConfiguredItemManager;

public class ConfiguredUserManager extends AbstractConfiguredItemManager<User>
		implements UserManager {

	private UserGroupManager userGroupManager;
	private RoleManager roleManager;

	@Inject
	public ConfiguredUserManager(UserGroupManager userGroupManager,
			RoleManager roleManager, ConfigurationService configurationService) {
		super(User.class, "security/Users", configurationService);
		this.roleManager = roleManager;
		this.userGroupManager = userGroupManager;
		load();
	}

	/**
	 * [users]
	 * 
	 * @id @groups @roles @full-name @email
	 */
	@Override
	protected void load(Configuration configuration, Map<String, User> items) {
		ConfigSection section = configuration.getSection("users");
		if (section != null) {
			for (Row row : section) {
				String[] fields = row.getFields("id", "groups", "roles",
						"full-name", "email");
				User u = new User(fields[0]);
				u.setFullname(fields[3]);
				u.setEmail(fields[4]);
				String[] groupIds = fields[1].split(",");
				for (String groupId : groupIds) {
					groupId = groupId.trim();
					if (groupId.isEmpty()) {
						continue;
					}
					UserGroup ug = userGroupManager.get(groupId);
					if (ug == null) {
						logger.error("Invalid user group for user, ignoring user group '"
								+ fields[0] + "': " + groupId);
						continue;
					}
					ug.addUser(u);
				}
				String[] roleIds = fields[2].split(",");
				for (String roleId : roleIds) {
					roleId = roleId.trim();
					if (roleId.isEmpty()) {
						continue;
					}
					Role r = roleManager.get(roleId);
					if (r == null) {
						logger.error("Invalid role for user, ignoring role '"
								+ fields[0] + "': " + roleId);
						continue;
					}
					u.addRole(r);
				}
				items.put(u.getIdentifier(), u);
			}
		}
		logger.info("Configured Users: " + this.items.keySet());
	}

}
