package com.cavisson.ata;

/**
 * @author Vishal Singh
 *
 */

import java.util.Map;

import javax.naming.NamingException;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.cavisson.ata.model.User;
import com.cavisson.ata.service.*;

@Controller
public class AppController {

	@RequestMapping("/")
	public String index() {
		return "index";
	}

	@RequestMapping("/http")
	public String http() {
		return "http";
	}

	@RequestMapping("/database")
	public String database() {
		return "database";
	}

	@RequestMapping("/thread")
	public String thread() {
		return "thread";
	}

	@RequestMapping("/async")
	public String async() {
		return "async";
	}

	@RequestMapping("/ldap")
	public String ldap() {
		return "ldap";
	}

	@GetMapping("/httpcallout")
	public String processHttp(Map<String, Object> request) {
		User model = new User();
		request.put("model", model);
		return "http";
	}

	@PostMapping("/httpcallout")
	public String httpcallout(@ModelAttribute("request") User request, Model model) {
		String output = null;
		String client = request.getClient();
		String url = request.getUrl();
		if (client.equals("apache")) {
			output = HTTPCallService.callApacheHttpClientService(url);
		} else if (client.equals("sun")) {
			output = HTTPCallService.callSunHttpClientService(url);
		} else if (client.equals("okhttp")) {
			output = HTTPCallService.callOkhttpClientService(url);
		} else if (client.equals("asyncHTTP")) {
			output = HTTPCallService.callApacheHttpAsyncForExchange(url);
		} else if (client.equals("asyncHTTPContent")) {
			output = HTTPCallService.callApacheHttpAsyncExchangeForContentStreaming(url);
		}
		model.addAttribute("client", client);
		model.addAttribute("output", output);
		return "callout";
	}

	@GetMapping("/dbcallout")
	public String processDb(Map<String, Object> request) {

		User model = new User();
		request.put("model", model);
		return "database";
	}

	@PostMapping("/dbcallout")
	public String dbcallout(@ModelAttribute("request") User request, Model model) {
		String output = null;
		String client = request.getClient();
		String dbName = request.getDbname();
		String prepared = request.getPrepared();
		String url = request.getUrl();
		String Host = request.getHost();
		String Port = request.getPort();
		String userName = request.getUsername();
		String passWord = request.getPassword();
		String firstName = request.getFirstname();
		String lastName = request.getLastname();

		if (client.equals("cloudant")) {
			System.out.println("dbName = " + dbName);
			String dbname = dbName.split(",")[0];
			output = DatabaseService.cloudantconnect(dbname);
		} else if (client.equals("db2")) {
			String dbname = dbName.split(",")[1];
			String username = userName.split(",")[0];
			String password = passWord.split(",")[0];
			String firstname = firstName.split(",")[0];
			String lastname = lastName.split(",")[0];
			output = DatabaseService.db2connect(url, dbname, username, password, firstname, lastname);
		} else if (client.equals("mssql") && prepared.equals("yes")) {
			String dbname = dbName.split(",")[2];
			String host = Host.split(",")[0];
			String port = Port.split(",")[0];
			String username = userName.split(",")[1];
			String password = passWord.split(",")[1];
			String firstname = firstName.split(",")[1];
			String lastname = lastName.split(",")[1];
			output = DatabaseService.mssqlPreparedConnect(host, username, password, dbname, port, firstname, lastname);
		} else if (client.equals("mssql") && prepared.equals("no")) {
			String dbname = dbName.split(",")[2];
			String host = Host.split(",")[0];
			String port = Port.split(",")[0];
			String username = userName.split(",")[1];
			String password = passWord.split(",")[1];
			output = DatabaseService.mssqlNonPrepConnect(host, username, password, dbname, port);
		} else if (client.equals("mysql") && prepared.equals("yes")) {
			String dbname = dbName.split(",")[3];
			String host = Host.split(",")[1];
			String username = userName.split(",")[2];
			String password = passWord.split(",")[2];
			String firstname = firstName.split(",")[2];
			String lastname = lastName.split(",")[2];
			output = DatabaseService.mysqlPreparedConnect(dbname, username, password, firstname, lastname, host);
		} else if (client.equals("mysql") && prepared.equals("no")) {
			String dbname = dbName.split(",")[3];
			String host = Host.split(",")[1];
			String username = userName.split(",")[2];
			String password = passWord.split(",")[2];
			output = DatabaseService.mysqlNonPrepConnect(dbname, username, password, host);
		} else if (client.equals("oracle") && prepared.equals("yes")) {
			String host = Host.split(",")[2];
			String port = Port.split(",")[1];
			String username = userName.split(",")[3];
			String password = passWord.split(",")[3];
			String firstname = firstName.split(",")[3];
			String lastname = lastName.split(",")[3];
			output = DatabaseService.oraclePreparedConnect(host, port, username, password, firstname, lastname);
		} else if (client.equals("oracle") && prepared.equals("no")) {
			String host = Host.split(",")[2];
			String port = Port.split(",")[1];
			String username = userName.split(",")[3];
			String password = passWord.split(",")[3];
			output = DatabaseService.oracleNonPrepConnect(host, port, username, password);
		} else if (client.equals("postgres")) {
			output = DatabaseService.postgresConnect();
		} else if (client.equals("lettuce")) {
			String host = Host.split(",")[3];
			output = DatabaseService.redisCall(host);
		} else if (client.equals("redis")) {
			output = DatabaseService.redisDataConnect();
		}
		model.addAttribute("client", client);
		model.addAttribute("output", output);
		return "callout";
	}

	@GetMapping("/threadcallout")
	public String processThread(Map<String, Object> request) {

		User model = new User();
		request.put("model", model);
		return "thread";
	}

	@PostMapping("/threadcallout")
	public String threadcallout(@ModelAttribute("request") User request, Model model) {
		String output = null;
		String client = request.getClient();
		if (client.equals("fork")) {
			output = ThreadCallService.callForkJoinThreadService();
		} else if (client.equals("simple")) {
			output = ThreadCallService.callSimpleThreadPoolExecutorService();
		} else if (client.equals("scheduledRunnable")) {
			output = ThreadCallService.callScheduledThreadPoolExecutorRunnableService();
		} else if (client.equals("scheduledCallable")) {
			output = ThreadCallService.callScheduledThreadPoolExecutorCallableService();
		}
		model.addAttribute("client", client);
		model.addAttribute("output", output);
		return "callout";
	}

	@GetMapping("/ldapcallout")
	public String processLdap(Map<String, Object> request) {
		User model = new User();
		request.put("model", model);
		return "ldap";
	}

	@PostMapping("/ldapcallout")
	public String ldapcallout(@ModelAttribute("request") User request, Model model) {

		String output = null;
		String client = "ldap";
		String employee_number = request.getEmpno();
		String firstname = request.getFirstname();
		String lastname = request.getLastname();
		String organisation = request.getOrg();
		String organisation_unit = request.getOrgunit();
		String port = request.getPort();
		String host = request.getHost();
		String button = request.getButton();
		try {

			if (button.equals("add")) {
				output = LDAPService.add(employee_number, firstname, lastname, host, port, organisation,
						organisation_unit);
			} else if (button.equals("fetch")) {
				output = LDAPService.fetch(employee_number, firstname, lastname, host, port, organisation,
						organisation_unit);
			} else if (button.equals("delete")) {
				output = LDAPService.delete(employee_number, firstname, lastname, host, port, organisation,
						organisation_unit);
			}
		} catch (Exception e) {
			System.err.println(e);
			output = e.toString();
		}

		model.addAttribute("client", client);
		model.addAttribute("output", output);
		return "callout";
	}
}