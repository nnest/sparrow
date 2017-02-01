/**
 * 
 */
package com.github.nnest.sparrow.command.document.geo;

import java.math.BigDecimal;

/**
 * coordinate
 * 
 * @author brad.wu
 * @since 0.0.1
 * @version 0.0.1
 */
public class Coordinate {
	private BigDecimal latitude = null;
	private BigDecimal longitude = null;

	public Coordinate() {
	}

	public Coordinate(BigDecimal longitude, BigDecimal latitude) {
		this.withLatitude(latitude).withLongitude(longitude);
	}

	/**
	 * @return the latitude
	 */
	public BigDecimal getLatitude() {
		return latitude;
	}

	/**
	 * @param latitude
	 *            the latitude to set
	 */
	public Coordinate withLatitude(BigDecimal latitude) {
		assert latitude != null : "Latitude cannot be null.";

		this.latitude = latitude;
		return this;
	}

	/**
	 * @return the longitude
	 */
	public BigDecimal getLongitude() {
		return longitude;
	}

	/**
	 * @param longitude
	 *            the longitude to set
	 */
	public Coordinate withLongitude(BigDecimal longitude) {
		assert longitude != null : "Longitude cannot be null.";

		this.longitude = longitude;
		return this;
	}

	/**
	 * value of
	 * 
	 * @param longitude
	 *            longitude
	 * @param latitude
	 *            latitude
	 * @return coordinate
	 */
	public static Coordinate valueOf(BigDecimal longitude, BigDecimal latitude) {
		return new Coordinate(longitude, latitude);
	}
}
