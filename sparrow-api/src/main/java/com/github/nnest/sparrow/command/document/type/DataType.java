/**
 * 
 */
package com.github.nnest.sparrow.command.document.type;

/**
 * data type
 * 
 * @author brad.wu
 * @since 0.0.1
 * @version 0.0.1
 */
public enum DataType {
	TEXT, //
	KEYWORD, //

	LONG, //
	INTEGER, //
	SHORT, //
	BYTE, //
	DOUBLE, //
	FLOAT, //

	DATE, //

	BOOLEAN, //

	BINARY, //

	INTEGER_RANGE, //
	FLOAT_RANGE, //
	LONG_RANGE, //
	DOUBLE_RANGE, //
	DATE_RANGE, //

	OBJECT, //
	NESTED, //

	GEO_POINT, //
	GEO_SHAPE, //

	IP, //
	COMPLETION, //
	TOKEN_COUNT, //
	MURMUR3, //

	PERCOLATOR, //
}
