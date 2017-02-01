/**
 * 
 */
package com.github.nnest.sparrow.rest.command.mixins.serialize;

import java.io.IOException;
import java.util.Set;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.github.nnest.sparrow.command.document.geo.Coordinate;
import com.github.nnest.sparrow.command.document.query.Example;
import com.github.nnest.sparrow.command.document.sort.Sort;
import com.github.nnest.sparrow.command.document.sort.SortBy;
import com.github.nnest.sparrow.command.document.sort.SortByField;
import com.github.nnest.sparrow.command.document.sort.SortByGeoDistance;
import com.github.nnest.sparrow.command.document.sort.SortByScript;
import com.github.nnest.sparrow.command.document.sort.SortMode;
import com.github.nnest.sparrow.command.document.sort.SortOrder;
import com.github.nnest.sparrow.command.document.type.DataType;

/**
 * sort serializer, see {@linkplain Sort}
 * 
 * @author brad.wu
 * @since 0.0.1
 * @version 0.0.1
 */
public class SortSerializer extends JsonSerializer<Sort> {
	/**
	 * (non-Javadoc)
	 * 
	 * @see com.fasterxml.jackson.databind.JsonSerializer#serialize(java.lang.Object,
	 *      com.fasterxml.jackson.core.JsonGenerator,
	 *      com.fasterxml.jackson.databind.SerializerProvider)
	 */
	@Override
	public void serialize(Sort sort, JsonGenerator gen, SerializerProvider serializers)
			throws IOException, JsonProcessingException {
		gen.writeStartObject();
		this.writeSortName(sort, gen);
		gen.writeStartObject();
		this.writeOrder(sort, gen);
		this.writeMode(sort, gen);
		this.writeNestedPath(sort, gen);
		this.writeNestedFilter(sort, gen);
		this.writeMissingValue(sort, gen);
		this.writeUnmappedType(sort, gen);
		this.writeGeoProperties(sort, gen);
		this.writeScriptProperties(sort, gen);
		gen.writeEndObject();
		gen.writeEndObject();
	}

	/**
	 * write script properties
	 * 
	 * @param sort
	 *            sort
	 * @param gen
	 *            generator
	 * @throws IOException
	 *             exception
	 */
	protected void writeScriptProperties(Sort sort, JsonGenerator gen) throws IOException {
		SortBy by = sort.getBy();
		if (by instanceof SortByScript) {
			SortByScript script = (SortByScript) by;
			if (script.getType() != null) {
				gen.writeStringField("type", script.getType().name().toLowerCase());
			}
			gen.writeObjectField("script", script.getScript());
		}
	}

	/**
	 * write geo properties
	 * 
	 * @param sort
	 *            sort
	 * @param gen
	 *            generator
	 * @throws IOException
	 *             exception
	 */
	protected void writeGeoProperties(Sort sort, JsonGenerator gen) throws IOException {
		SortBy by = sort.getBy();
		if (by instanceof SortByGeoDistance) {
			SortByGeoDistance geo = (SortByGeoDistance) by;
			if (geo.getUnit() != null) {
				gen.writeStringField("unit", geo.getUnit().name().toLowerCase());
			}
			gen.writeFieldName(geo.getFieldName());
			Set<Coordinate> coordinates = geo.getCoordinates();
			gen.writeStartArray();
			for (Coordinate coordinate : coordinates) {
				gen.writeStartArray();
				gen.writeNumber(coordinate.getLongitude());
				gen.writeNumber(coordinate.getLatitude());
				gen.writeEndArray();
			}
			gen.writeEndArray();
		}
	}

	/**
	 * write unmapped type
	 * 
	 * @param sort
	 *            sort
	 * @param gen
	 *            generator
	 * @throws IOException
	 *             exception
	 */
	protected void writeUnmappedType(Sort sort, JsonGenerator gen) throws IOException {
		DataType unmappedType = sort.getUnmappedType();
		if (unmappedType != null) {
			gen.writeStringField("unmapped_type", unmappedType.name().toLowerCase());
		}
	}

	/**
	 * write missing value
	 * 
	 * @param sort
	 *            sort
	 * @param gen
	 *            generator
	 * @throws IOException
	 *             exception
	 */
	protected void writeMissingValue(Sort sort, JsonGenerator gen) throws IOException {
		String missingValue = sort.getMissingValue();
		if (missingValue != null) {
			gen.writeStringField("missing", missingValue);
		}
	}

	/**
	 * write nested filter
	 * 
	 * @param sort
	 *            sort
	 * @param gen
	 *            generator
	 * @throws IOException
	 *             exception
	 */
	protected void writeNestedFilter(Sort sort, JsonGenerator gen) throws IOException {
		Example nestedFilter = sort.getNestedFilter();
		if (nestedFilter != null) {
			gen.writeFieldName("nested_filter");
			gen.writeObject(nestedFilter);
		}
	}

	/**
	 * write nested path
	 * 
	 * @param sort
	 *            sort
	 * @param gen
	 *            generator
	 * @throws IOException
	 *             exception
	 */
	protected void writeNestedPath(Sort sort, JsonGenerator gen) throws IOException {
		String nestedPath = sort.getNestedPath();
		if (nestedPath != null) {
			gen.writeStringField("nested_path", nestedPath);
		}
	}

	/**
	 * write sort mode
	 * 
	 * @param sort
	 *            sort
	 * @param gen
	 *            generator
	 * @throws IOException
	 *             exception
	 */
	protected void writeMode(Sort sort, JsonGenerator gen) throws IOException {
		SortMode mode = sort.getMode();
		if (mode != null) {
			gen.writeStringField("mode", mode.name().toLowerCase());
		}
	}

	/**
	 * write sort name
	 * 
	 * @param sort
	 *            sort
	 * @param gen
	 *            generator
	 * @throws IOException
	 *             exception
	 */
	protected void writeSortName(Sort sort, JsonGenerator gen) throws IOException {
		SortBy by = sort.getBy();
		if (by instanceof SortByField) {
			String fieldName = ((SortByField) by).getFieldName();
			gen.writeFieldName(fieldName);
		} else if (by instanceof SortByScript) {
			gen.writeFieldName("_script");
		} else if (by instanceof SortByGeoDistance) {
			gen.writeFieldName("_geo_distance");
		} else {
			throw new IllegalArgumentException(String.format("Sort by[%1$s] is unsupported", by.getClass()));
		}
	}

	/**
	 * write order property
	 * 
	 * @param sort
	 *            sort
	 * @param gen
	 *            generator
	 * @throws IOException
	 *             exception
	 */
	protected void writeOrder(Sort sort, JsonGenerator gen) throws IOException {
		SortOrder order = sort.getOrder();
		if (order == null) {
			SortBy by = sort.getBy();
			if (by instanceof SortByField) {
				if (((SortByField) by).getFieldName().equals(Sort.BY_SCORE)) {
					order = SortOrder.DESC;
				} else {
					order = SortOrder.ASC;
				}
			}
		}
		if (order != null) {
			gen.writeStringField("order", order.name().toLowerCase());
		}
	}
}
