package org.homemotion.auth.impl;

import java.util.Map;

import javax.inject.Inject;

import org.homemotion.auth.Role;
import org.homemotion.auth.RoleManager;
import org.homemotion.auth.UserGroup;
import org.homemotion.auth.UserGroupManager;
import org.homemotion.common.config.ConfigSection;
import org.homemotion.common.config.Configuration;
import org.homemotion.common.config.ConfigurationService;
import org.homemotion.common.config.Row;
import org.homemotion.dao.spi.AbstractConfiguredItemManager;

public class ConfiguredUserGroupManager extends
		AbstractConfiguredItemManager<UserGroup>
		implements UserGroupManager {

	private RoleManager roleManager;

	@Inject
	public ConfiguredUserGroupManager(RoleManager roleManager,
			ConfigurationService configurationService) {
		super(UserGroup.class, "security/UserGroups", configurationService);
		this.roleManager = roleManager;
		load();
	}

	/**
	 * [groups]
	 * 
	 * @id @roleIds
	 */
	@Override
	protected void load(Configuration configuration,
			Map<String, UserGroup> items) {
		ConfigSection section = configuration.getSection("groups");
		if (section != null) {
			for (Row row : section) {
				String[] fields = row.getFields("id", "roleIds");
				UserGroup ug = new UserGroup(fields[0]);
				String[] roleIds = fields[1].split(",");
				for (String roleId : roleIds) {
					roleId = roleId.trim();
					if (roleId.isEmpty()) {
						continue;
					}
					Role r = roleManager.get(roleId);
					if (r == null) {
						logger.error("Invalid role for user group, ignoring role '"
								+ fields[0] + "': " + roleId);
						continue;
					}
					ug.addRole(r);
				}
				items.put(ug.getIdentifier(), ug);
			}
		}
		logger.info("Configured User Groups: " + this.items.keySet());
	}

}
