package com.cavisson.ata.service;
/**
 * @author Vishal Singh
 *
 */

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.AsyncContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.http.HttpResponse;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.concurrent.FutureCallback;
import org.apache.http.impl.nio.client.CloseableHttpAsyncClient;
import org.apache.http.impl.nio.client.HttpAsyncClients;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

//apache http async exchange using callback interface
@EnableAsync
@WebServlet(name = "myServlet", urlPatterns = { "/asyncHTTPCallback" }, asyncSupported = true)
public class AsyncService extends HttpServlet {

	private static final long serialVersionUID = 1L;
	String url = null;
	PrintWriter out;

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		url = request.getParameter("url");
		out = response.getWriter();
		AsyncContext ac = request.startAsync();
		apacheAsyncServlet(url, ac);
	}

	public void apacheAsyncServlet(String url, AsyncContext ac) {

		try {
			// out.println("<html>" + "<head>" + "<link rel=\"stylesheet\"
			// href=\"/resources/css/style.css\">"
			// + "<title>Spring Boot : Output</title>" + "</head>" + "<body>" + "<ul>"
			// + "<li><a class=\"active\">Home</a></li>" + "<li><a href=\"http\">Http
			// Clients</a></li>"
			// + "<li><a href=\"database\">Database Clients</a></li>" + "<li><a
			// href=\"jms\">JMS Clients</a></li>"
			// + "<li><a href=\"webservice\">Web Services</a></li>" + "<li><a
			// href=\"thread\">Threads</a></li>"
			// + "<li><a href=\"other\">Heap & Others</a></li>" + "</ul>"
			// + "<div style=\"margin-left: 25%; padding: 1px 16px; height: 500px;\">");
			out.println("<html>" + "<head>" + "<link rel=\"stylesheet\" href=\"static/css/style.css\">"
					+ "<title>Spring Boot : Output</title>" + "</head>" + "<body>" + "<ul>"
					+ "<li><a href=\"/\">Home</a></li>" + "<li><a class=\"active\"href=\"http\">HTTP</a></li>"
					+ "<li><a href=\"database\">Databases</a></li>" + "<li><a href=\"thread\">Threads</a></li>"
					+ "<li><a href=\"ldap\">LDAP</a></li>" + "</ul>"
					+ "<div style=\"margin-left: 25%; padding: 1px 16px; height: 500px;\">");
			out.println("<h2>" + "Asynchronous HTTP exchange using a callback interface and Servlet 3.0" + "</h2>");

			RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(3000).setConnectTimeout(3000).build();
			CloseableHttpAsyncClient httpclient2 = HttpAsyncClients.custom().setDefaultRequestConfig(requestConfig)
					.build();

			httpclient2.start();

			long startTime = System.currentTimeMillis();
			HttpGet request2 = new HttpGet(url);
			out.println("<p>" + "Response Time for get:" + (System.currentTimeMillis() - startTime) + "</p>");

			out.println("<p>" + "Thread id : " + Thread.currentThread().getId() + "Thread Name : "
					+ Thread.currentThread().getName() + "</p>");
			long startExeTime = System.currentTimeMillis();
			out.println("<p>" + "Before Execute" + "</p>");
			httpclient2.execute(request2, new FutureCallback<HttpResponse>() {

				public void completed(final HttpResponse response) {

					out.println("<p>" + "Completing the Async request" + "</p>");
					ac.complete();
					out.println("<p>" + "Thread id : " + Thread.currentThread().getId() + "Thread Name : "
							+ Thread.currentThread().getName() + "</p>");
					// out.println("<h3>" + " Response Time for execute :" +
					// (System.currentTimeMillis() - startExeTime) + "</h3>");
					out.println("<p>" + " Response Time for execute :" + (System.currentTimeMillis() - startExeTime)
							+ " " + request2.getRequestLine() + "->" + response.getStatusLine() + "</p>");

					out.println("</div>" + "</body>" + "</html>");
					Throwable throwable = new IllegalArgumentException("Blah");
					out.println(getStackTrace(throwable));
				}

				public void failed(final Exception ex) {
					out.println("<p>" + "Thread id : " + Thread.currentThread().getId() + "Thread Name : "
							+ Thread.currentThread().getName() + "</p>");
					// out.println("<h3>" + " Response Time for execute :" +
					// (System.currentTimeMillis() - startExeTime) + "</h3>");
					out.println("<p>" + " Response Time for execute :" + (System.currentTimeMillis() - startExeTime)
							+ " " + request2.getRequestLine() + "->" + ex + "</p>");
					Throwable throwable = new IllegalArgumentException("Blah");
					out.println(getStackTrace(throwable));
				}

				public void cancelled() {
					out.println("<p>" + "Thread id : " + Thread.currentThread().getId() + "Thread Name : "
							+ Thread.currentThread().getName() + "</p>");
					// out.println("<h3>" + " Response Time for execute :" +
					// (System.currentTimeMillis() - startExeTime) + "</h3>");
					out.println("<p>" + " Response Time for execute :" + (System.currentTimeMillis() - startExeTime)
							+ " " + request2.getRequestLine() + " cancelled" + "</p>");
					Throwable throwable = new IllegalArgumentException("Blah");
					out.println(getStackTrace(throwable));
				}

				public String getStackTrace(Throwable aThrowable) {
					java.io.Writer result = new java.io.StringWriter();
					PrintWriter printWriter = new PrintWriter(result);
					aThrowable.printStackTrace(printWriter);
					return result.toString();
				}

			});
			out.println("<p>" + "After Execute" + "</p>");
			System.out.println("Apache Async Http Exchange Using Callback called...");
		} catch (Exception e) {
			System.err.println(e);
		}
	}
}
