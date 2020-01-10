package com.cavisson.ata.service;

import java.util.Hashtable;
import java.util.Properties;

import javax.naming.Context;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.Attribute;
import javax.naming.directory.Attributes;
import javax.naming.directory.BasicAttribute;
import javax.naming.directory.BasicAttributes;
import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;
import javax.naming.directory.SearchControls;
import javax.naming.directory.SearchResult;

public class LDAPService {
	public static String add(String employee_number, String firstname, String lastname, String host, String port,
			String organisation, String organisation_unit) throws NamingException {

		Hashtable<String, Object> env = new Hashtable<>();

		env.put(Context.SECURITY_AUTHENTICATION, "simple");
		env.put(Context.SECURITY_PRINCIPAL, "uid=admin,ou=system");
		env.put(Context.SECURITY_CREDENTIALS, "secret");
		env.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
		env.put(Context.PROVIDER_URL, "ldap://" + host + ":" + port);

		DirContext context = new InitialDirContext(env);
		System.out.println("Directory Connected..");
		System.out.println(employee_number);

		BasicAttributes attributes = new BasicAttributes();
		Attribute attribute = new BasicAttribute("objectClass");

		attribute.add("inetOrgPerson");
		attributes.put(attribute);

		Attribute sn = new BasicAttribute("sn");
		sn.add(lastname);
		Attribute cn = new BasicAttribute("cn");
		cn.add(firstname);

		attributes.put(sn);
		attributes.put(cn);

		String empadd = "employeeNumber=" + employee_number + ",ou=" + organisation_unit + ",o=" + organisation;

		System.out.println(empadd);
		context.createSubcontext(empadd, attributes);
		// context.createSubcontext(emp, attributes);

		System.out.println("context  created successfully");

		return "context created successfully";
	}

	public static String delete(String employee_number, String firstname, String surname, String host, String port,
			String organisation, String organisation_unit) throws NamingException {
		Hashtable<String, Object> env = new Hashtable<>();

		env.put(Context.SECURITY_AUTHENTICATION, "simple");
		env.put(Context.SECURITY_PRINCIPAL, "uid=admin,ou=system");
		env.put(Context.SECURITY_CREDENTIALS, "secret");
		env.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
		env.put(Context.PROVIDER_URL, "ldap://" + host + ":" + port);

		DirContext context = new InitialDirContext(env);
		System.out.println("Directory Connected..");
		System.out.println("del" + employee_number);
		String empdel = "employeeNumber=" + employee_number + ",ou=" + organisation_unit + ",o=" + organisation;

		context.destroySubcontext(empdel);
		// context.destroySubcontext("employeeNumber=11,ou=users,o=Company");

		System.out.println("context  deleted successfully");

		return "context deleted successfully";
	}

	@SuppressWarnings("unused")
	public static String fetch(String employee_number, String firstname, String surname, String host, String port,
			String organisation, String organisation_unit) throws NamingException {

		Properties initialProperties = new Properties();

		initialProperties.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
		initialProperties.put(Context.PROVIDER_URL, "ldap://" + host + ":" + port);
		initialProperties.put(Context.SECURITY_PRINCIPAL, "uid=admin,ou=system");
		initialProperties.put(Context.SECURITY_CREDENTIALS, "secret");
		DirContext context = new InitialDirContext(initialProperties);
		System.out.println("Directory Connected..");

		String searchFilter = "(objectClass=inetOrgPerson)";
		String[] requiredAttributes = { "sn", "cn", "employeeNumber" };
		SearchControls controls = new SearchControls();
		controls.setSearchScope(SearchControls.SUBTREE_SCOPE);
		controls.setReturningAttributes(requiredAttributes);

		NamingEnumeration<SearchResult> users = context.search("ou=" + organisation_unit + ",o=" + organisation,
				searchFilter, controls);
		String resultFinal = null;
		SearchResult searchResult = null;
		String commonName = null;
		String lastname = null;
		String employeeNum = null;
		String result = null;
		while (users.hasMore()) {

			searchResult = (SearchResult) users.next();
			Attributes attr = searchResult.getAttributes();

			commonName = attr.get("cn").get(0).toString();
			lastname = attr.get("sn").get(0).toString();

			employeeNum = attr.get("employeeNumber").get(0).toString();

			if (null == attr.get("employeeNumber"))
				System.out.println("no employee");

			System.out.println("Firstname = " + commonName);
			System.out.println("Lastname  = " + lastname);
			System.out.println("Employee Number = " + employeeNum);
			System.out.println("-------------------------------------------");

			result = result + "," + "     Name = " + commonName + "     Lastname = " + lastname
					+ "     Employee Number = " + employeeNum + "\t";

		}

		if (result == null) {
			result = "no data";
			return result;
		}
		return result.replace("null,", "");
	}
}
