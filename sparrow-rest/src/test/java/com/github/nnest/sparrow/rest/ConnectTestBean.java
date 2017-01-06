/**
 * 
 */
package com.github.nnest.sparrow.rest;

import java.util.Date;
import java.util.List;

/**
 * @author brad.wu
 * @since 0.0.1
 * @version 0.0.1
 */
public class ConnectTestBean {
	private Long id = null;
	private String name = null;
	private String code = null;
	private Integer time = null;
	private Date born = null;
	private List<ConnectTestNestBean> nestedBeans = null;

	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name
	 *            the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the code
	 */
	public String getCode() {
		return code;
	}

	/**
	 * @param code
	 *            the code to set
	 */
	public void setCode(String code) {
		this.code = code;
	}

	/**
	 * @return the time
	 */
	public Integer getTime() {
		return time;
	}

	/**
	 * @param time
	 *            the time to set
	 */
	public void setTime(Integer time) {
		this.time = time;
	}

	/**
	 * @return the born
	 */
	public Date getBorn() {
		return born;
	}

	/**
	 * @param born
	 *            the born to set
	 */
	public void setBorn(Date born) {
		this.born = born;
	}

	/**
	 * @return the nestedBeans
	 */
	public List<ConnectTestNestBean> getNestedBeans() {
		return nestedBeans;
	}

	/**
	 * @param nestedBeans
	 *            the nestedBeans to set
	 */
	public void setNestedBeans(List<ConnectTestNestBean> nestedBeans) {
		this.nestedBeans = nestedBeans;
	}
}
