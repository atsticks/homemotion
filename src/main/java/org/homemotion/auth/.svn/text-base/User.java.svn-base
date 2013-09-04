package org.homemotion.auth;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.homemotion.dao.spi.AbstractConfiguredItem;

/**
 * A simple user.
 * 
 * @author Anatole
 */
public class User extends AbstractConfiguredItem implements Serializable {

	private static final long serialVersionUID = -8443234918260997954L;

	private static final String ANONYMOUS_ID = "anonymous";
	
	public final static User ANONYMOUS = new User(ANONYMOUS_ID, ANONYMOUS_ID, ANONYMOUS_ID, null, Locale.ENGLISH) {
		private static final long serialVersionUID = 5612104905089191576L;
		public void setEmail(String email) {throw new UnsupportedOperationException("Not supported for anonymous user.");};
		public void setEnabled(boolean enabled) {throw new UnsupportedOperationException("Not supported for anonymous user.");};
		public void setFullname(String fullname) {throw new UnsupportedOperationException("Not supported for anonymous user.");};
		public void setIdentifier(String id) {throw new UnsupportedOperationException("Not supported for anonymous user.");};
		public void setLocale(Locale locale) {throw new UnsupportedOperationException("Not supported for anonymous user.");};
		public void setPassword(String password) {throw new UnsupportedOperationException("Not supported for anonymous user.");};
		public void setRoles(java.util.Set<Role> userroles) {
			throw new UnsupportedOperationException("Not supported for anonymous user.");
		};
	};
	
	private String id;
	private String fullName;
	private String password;
	private String email;
	private Locale locale = Locale.ENGLISH;
	private boolean enabled = true;

	private Set<Role> roles = new HashSet<Role>(0);

	public User(String userId, String password) {
		super(userId, "security/Users");
		this.password = password;
	}

	public User(String userId, String password, String fullName, String email,
			Locale locale) {
		super(userId, "security/Users");
		this.password = password;
		this.fullName = fullName;
		this.email = email;
		this.locale = locale;
	}

	public User(String userId) {
		super(userId, "security/Users");
	}

	@Override
	public String getIdentifier() {
		return id;
	}

	public void setIdentifier(String id){
		if (id == null) {
			throw new IllegalArgumentException("id is required.");
		}
		this.id = id;
	}
	
	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getFullname() {
		return this.fullName;
	}

	public void setFullname(String fullname) {
		this.fullName = fullname;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Locale getLocale() {
		return locale;
	}

	public void setLocale(Locale locale) {
		this.locale = locale;
	}

	public boolean isEnabled() {
		return this.enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public Set<Role> getRoles() {
		return this.roles;
	}

	public void setRoles(Set<Role> userroles) {
		this.roles = userroles;
	}

	@Override
	public int hashCode() {
		return Long.valueOf(getIdentifier()).hashCode();
	}

	public boolean equals(User secUser) {
		return getIdentifier() == secUser.getIdentifier();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (obj instanceof User) {
			User secUser = (User) obj;
			return equals(secUser);
		}

		return false;
	}

	public String toString() {
		return new ToStringBuilder(this).append("id", getIdentifier()).toString();
	}

	public boolean hasRole(Role role) {
		return this.roles.contains(role);
	}

	public void addRole(Role r) {
		if(!this.roles.contains(r)){
			this.roles.add(r);
		}
	}

}
