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

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.homemotion.dao.spi.AbstractEntity;

/**
 * EN: Model class for <b>SecLoginlog</b>.<br>
 * DE: Model Klasse fuer <b>Login Log</b>.<br>
 * 
 * @author bbruhns
 * @author sgerth
 */
@Entity
public class SecLoginlog extends AbstractEntity {

	private static final long serialVersionUID = -2628240632347849393L;
	@Column(nullable = false)
	private String lglLoginname;
	
	@Column(nullable = false)
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date lglLogtime;
	private String lglSessionid;
	
	@Column(nullable = false)
	private String lglIp;
	
	private String browserType;
	
	private boolean successful;

	public SecLoginlog() {
	}

	public SecLoginlog(String lglLoginname, Date lglLogin, boolean successful) {
		this.lglLoginname = lglLoginname;
		this.lglLogtime = lglLogin;
		this.successful = successful;
	}

	public SecLoginlog(String lglLoginname, Date lglLogtime,
			String lglSessionid, String lglIp, String browserType, boolean successful) {
		this.lglLoginname = lglLoginname;
		this.lglLogtime = lglLogtime;
		this.lglSessionid = lglSessionid;
		this.lglIp = lglIp;
		this.browserType = browserType;
		this.successful = successful;
	}

	public String getLglLoginname() {
		return this.lglLoginname;
	}

	public void setLglLoginname(String lglLoginname) {
		this.lglLoginname = lglLoginname;
	}

	public Date getLglLogtime() {
		return this.lglLogtime;
	}

	public void setLglLogtime(Date lglLogtime) {
		this.lglLogtime = lglLogtime;
	}

	public String getLglSessionid() {
		return this.lglSessionid;
	}

	public void setLglSessionid(String lglSessionid) {
		this.lglSessionid = lglSessionid;
	}

	public String getLglIp() {
		return this.lglIp;
	}

	public void setLglIp(String lglIp) {
		this.lglIp = lglIp;
	}

	public void setBrowserType(String browserType) {
		this.browserType = browserType;
	}

	public String getBrowserType() {
		return browserType;
	}

	@Override
	public int hashCode() {
		return Long.valueOf(getId()).hashCode();
	}

	public boolean equals(SecLoginlog secLoginlog) {
		return getId() == secLoginlog.getId();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (obj instanceof SecLoginlog) {
			SecLoginlog secLoginlog = (SecLoginlog) obj;
			return equals(secLoginlog);
		}

		return false;
	}

	public String toString() {
		return new ToStringBuilder(this).append("id", getId()).toString();
	}

	public boolean isSuccessful() {
		return successful;
	}

	public void setSuccessful(boolean successful) {
		this.successful = successful;
	}

}
