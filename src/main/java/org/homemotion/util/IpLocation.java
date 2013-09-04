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
package org.homemotion.util;

import java.io.Serializable;

import org.apache.commons.lang.StringUtils;

/**
 * The IPLocator is a java wrapper for the hostip.info ip locator web service.
 * 
 * @author <a href="mailto:pillvin@iit.edu">Vinod Pillai</a>
 * @version $Revision: 1.0 $
 */
public final class IpLocation implements Serializable{
	
	/**
	 * serialVersionUID.
	 */
	private static final long serialVersionUID = 3399031878925775608L;
	
	private String city = "";
	private String country = "";
	private float longitude = -1;
	private float latitude = -1;

	/**
	 * This is a singleton class therefore make the constructor private
	 */
	public IpLocation() {
	}

	/**
	 * @return the city
	 */
	public String getCity() {
		return this.city;
	}

	/**
	 * @param city
	 *            the city to set
	 */
	public void setCity(String city) {
		this.city = city;
	}

	/**
	 * @return the country
	 */
	public String getCountry() {
		return this.country;
	}

	/**
	 * @param country
	 *            the country to set
	 */
	public void setCountry(String country) {
		this.country = country;
	}

	/**
	 * @return the longitude
	 */
	public float getLongitude() {
		return this.longitude;
	}

	/**
	 * @param longitude
	 *            the longitude to set
	 */
	public void setLongitude(float longitude) {
		this.longitude = longitude;
	}

	/**
	 * @return the latitude
	 */
	public float getLatitude() {
		return this.latitude;
	}

	/**
	 * @param latitude
	 *            the latitude to set
	 */
	public void setLatitude(float latitude) {
		this.latitude = latitude;
	}


	/**
	 * @return the country
	 */
	public String getCountryCode() {
		return StringUtils.defaultString(StringUtils.substringBetween(this.country, "(", ")"));
	}
}
