package com.cavisson.ata.model;

/**
 * @author Vishal Singh
 *
 */

public class User {

	public String client;
	public String dbname;
	public String prepared;
	public String url;
	public String host;
	public String port;
	public String username;
	public String password;
	public String firstname;
	public String lastname;
	private String empno;
	public String org;
	public String orgunit;
	public String button;

	public String getEmpno() {
		return empno;
	}

	public void setEmpno(String empno) {
		this.empno = empno;
	}

	public String getOrg() {
		return org;
	}

	public void setOrg(String org) {
		this.org = org;
	}

	public String getOrgunit() {
		return orgunit;
	}

	public void setOrgunit(String orgunit) {
		this.orgunit = orgunit;
	}

	public String getButton() {
		return button;
	}

	public void setButton(String button) {
		this.button = button;
	}

	public String getPrepared() {
		return prepared;
	}

	public void setPrepared(String prepared) {
		this.prepared = prepared;
	}

	public String getClient() {
		return client;
	}

	public void setClient(String client) {
		this.client = client;
	}

	public String getDbname() {
		return dbname;
	}

	public void setDbname(String dbname) {
		this.dbname = dbname;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public String getPort() {
		return port;
	}

	public void setPort(String port) {
		this.port = port;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}
}
