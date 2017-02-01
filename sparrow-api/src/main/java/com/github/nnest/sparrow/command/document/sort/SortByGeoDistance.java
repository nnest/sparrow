/**
 * 
 */
package com.github.nnest.sparrow.command.document.sort;

import java.util.Set;

import com.github.nnest.sparrow.command.document.geo.Coordinate;
import com.github.nnest.sparrow.command.document.geo.DistanceType;
import com.github.nnest.sparrow.command.document.geo.DistanceUnit;
import com.google.common.base.Strings;
import com.google.common.collect.Sets;

/**
 * sort by geo distance
 * 
 * @author brad.wu
 * @since 0.0.1
 * @version 0.0.1
 */
public class SortByGeoDistance implements SortBy {
	private String fieldName = null;
	private Set<Coordinate> coordinates = null;
	private DistanceUnit unit = null;
	private DistanceType distanceType = null;

	public SortByGeoDistance(String fieldName, Coordinate... coordinates) {
		this.withFieldName(fieldName).withCoordinates(coordinates);
	}

	/**
	 * @return the fieldName
	 */
	public String getFieldName() {
		return fieldName;
	}

	/**
	 * @param fieldName
	 *            the fieldName to set
	 * @return this
	 */
	public SortByGeoDistance withFieldName(String fieldName) {
		assert Strings.nullToEmpty(fieldName).trim().length() != 0 : "Field name cannot be null or blank.";

		this.fieldName = fieldName;
		return this;
	}

	/**
	 * @return the coordinates
	 */
	public Set<Coordinate> getCoordinates() {
		return coordinates;
	}

	/**
	 * @param coordinates
	 *            the coordinates to set
	 * @return this
	 */
	public SortByGeoDistance withCoordinates(Set<Coordinate> coordinates) {
		assert coordinates != null && coordinates.size() > 0 : "Coordinates cannot be null or empty.";

		this.coordinates = coordinates;
		return this;
	}

	/**
	 * 
	 * @param coordinates
	 *            the coordinates to set
	 * @return this
	 */
	public SortByGeoDistance withCoordinates(Coordinate... coordinates) {
		assert coordinates != null && coordinates.length > 0 : "Coordinates cannot be null or empty.";

		this.coordinates = Sets.newHashSet(coordinates);
		return this;
	}

	/**
	 * @return the unit
	 */
	public DistanceUnit getUnit() {
		return unit;
	}

	/**
	 * @param unit
	 *            the unit to set
	 * @return this
	 */
	public SortByGeoDistance with(DistanceUnit unit) {
		assert unit != null : "Distance unit cannot be null.";

		this.unit = unit;
		return this;
	}

	/**
	 * @return the distanceType
	 */
	public DistanceType getDistanceType() {
		return distanceType;
	}

	/**
	 * @param distanceType
	 *            the distanceType to set
	 * @return this
	 */
	public SortByGeoDistance with(DistanceType distanceType) {
		assert distanceType != null : "Distance type cannot be null.";

		this.distanceType = distanceType;
		return this;
	}
}
