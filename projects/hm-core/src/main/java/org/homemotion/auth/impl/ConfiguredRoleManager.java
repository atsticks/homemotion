package org.homemotion.auth.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.homemotion.auth.Action;
import org.homemotion.auth.ActionManager;
import org.homemotion.auth.Role;
import org.homemotion.auth.RoleManager;
import org.homemotion.common.config.ConfigSection;
import org.homemotion.common.config.Configuration;
import org.homemotion.common.config.Row;
import org.homemotion.dao.spi.AbstractConfiguredItemManager;

public class ConfiguredRoleManager extends AbstractConfiguredItemManager<Role>
		implements RoleManager {

	private ActionManager actionManager;
	private List<Role> defaultRoles = new ArrayList<Role>();

	@Inject
	public ConfiguredRoleManager(ActionManager actionManager) {
		super(Role.class, "security/Roles");
		this.actionManager = actionManager;
		load();
	}

	public Collection<Role> getDefaultRoles() {
		return Collections.unmodifiableCollection(this.defaultRoles);
	}

	/**
	 * [roles]
	 * 
	 * @id @actionIds @transitive-roles [default-roles]
	 * @roleId
	 */
	@Override
	protected void load(Configuration configuration, Map<String, Role> items) {
		ConfigSection section = configuration.getSection("roles");
		if (section != null) {
			for (Row row : section) {
				String[] fields = row.getFields("id", "actions",
						"transitive-roles");
				Role r = new Role(fields[0]);
				String[] actionIds = fields[1].split(",");
				for (String actionId : actionIds) {
					actionId = actionId.trim();
					if (actionId.isEmpty()) {
						continue;
					}
					Action a = actionManager.get(actionId);
					if (a == null) {
						logger.error("Invalid action for role, ignoring action '"
								+ fields[0] + "': " + actionId);
						continue;
					}
					r.addAction(a);
				}
				items.put(r.getIdentifier(), r);
				// TODO handle transitive roles
			}
		}
		logger.info("Configured Roles: " + this.items.keySet());
		// default roles
		section = configuration.getSection("default-roles");
		if (section != null) {
			for (Row row : section) {
				String[] fields = row.getFields("roleId");
				Role r = get(fields[0].trim());
				if (r == null) {
					logger.error("Invalid default role, ignoring '" + fields[0]
							+ "': " + fields[0]);
					continue;
				}
				this.defaultRoles.add(r);
			}
		}
	}

}
