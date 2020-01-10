package com.cavisson.ata.service;
/**
 * @author Vishal Singh
 *
 */

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.CharBuffer;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.nio.client.CloseableHttpAsyncClient;
import org.apache.http.impl.nio.client.HttpAsyncClients;
import org.apache.http.nio.IOControl;
import org.apache.http.nio.client.methods.AsyncCharConsumer;
import org.apache.http.nio.client.methods.HttpAsyncMethods;
import org.apache.http.protocol.HttpContext;
import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class HTTPCallService {

	// okhttp callout
	public static String callOkhttpClientService(String url) {

		Response response = null;
		String body = null;
		OkHttpClient client = new OkHttpClient();
		Request request = new Request.Builder().url(url).addHeader("Person", "HASH").build();

		Call call = client.newCall(request);
		try {
			response = call.execute();
			body = response.body().string();
			System.out.println("\nOkhttp : Sending 'GET' request to URL : " + url);
			System.out.println("Okhttp : Response Code : " + response.code());
		} catch (IOException e) {
			System.err.println(e);
		}
		return body;
	}

	// sun http callout
	public static String callSunHttpClientService(String url) {
		StringBuffer response = new StringBuffer();
		String USER_AGENT = "Mozilla/5.0";

		try {
			URL obj = new URL(url);
			HttpURLConnection con = (HttpURLConnection) obj.openConnection();

			// optional default is GET
			con.setRequestMethod("GET");

			// add request header
			con.setRequestProperty("User-Agent", USER_AGENT);

			int responseCode = con.getResponseCode();
			System.out.println("\nSun Http : Sending 'GET' request to URL : " + url);
			System.out.println("Sun Http : Response Code : " + responseCode);

			BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
			String inputLine;

			while ((inputLine = in.readLine()) != null) {
				response.append(inputLine);
			}
			in.close();

		} catch (IOException e) {
			System.err.println(e);
		}
		return response.toString();

	}

	// apache http callout
	public static String callApacheHttpClientService(String url) {

		HttpClient client = new HttpClient();
		client.getParams().setParameter("http.useragent", "Test Client");
		client.getParams().setParameter("http.connection.timeout", new Integer(5000));

		GetMethod method = new GetMethod();
		FileOutputStream fos = null;
		String body = null;
		try {
			method.setURI(new org.apache.commons.httpclient.URI(url, true));
			int code = client.executeMethod(method);
			body = method.getResponseBodyAsString();
			System.out.println("\nApache Commons : Sending 'GET' request to URL : " + url);
			System.out.println("Apache Commons : Response Code : " + code);

		} catch (IOException ie) {
			System.err.println(ie);

		} finally {
			method.releaseConnection();
			if (fos != null)
				try {
					fos.close();
				} catch (Exception fe) {
				}
		}
		return body;
	}

	// apache http async exchange
	public static String callApacheHttpAsyncForExchange(String url) {
		try {
			CloseableHttpAsyncClient httpclient = HttpAsyncClients.createDefault();

			System.out.println("Apache Async Exchange : asynchronous HTTP exchange called...");

			httpclient.start();

			long startTime = System.currentTimeMillis();
			HttpGet request1 = new HttpGet(url);
			System.out.println(
					"Apache Async Exchange : Response Time for get : " + (System.currentTimeMillis() - startTime));

			System.out.println("Apache Async Exchange : Request :" + request1.getRequestLine());

			startTime = System.currentTimeMillis();
			Future<HttpResponse> future = httpclient.execute(request1, null);
			System.out.println(
					"Apache Async Exchange : Response Time  for execute :" + (System.currentTimeMillis() - startTime));

			startTime = System.currentTimeMillis();
			HttpResponse response1;

			response1 = future.get();

			System.out.println("Apache Async Exchange : Response Time  for future.get = "
					+ (System.currentTimeMillis() - startTime));

			System.out.println("Apache Async Exchange : Response = " + response1.getStatusLine());
			httpclient.close();

		} catch (IOException e) {
			System.err.println(e);

		} catch (InterruptedException e) {

			System.err.println(e);
		} catch (ExecutionException e) {

			System.err.println(e);
		}
		return "asynchronous HTTP exchange called...";
	}

	// apache http async exchange for content streaming
	public static String callApacheHttpAsyncExchangeForContentStreaming(String url) {
		CloseableHttpAsyncClient httpclient = HttpAsyncClients.createDefault();
		try {
			System.out.println(
					"Apache Async Content Streaming Exchange : Asynchronous HTTP exchange with content streaming");
			httpclient.start();

			long startTime = System.currentTimeMillis();
			Future<Boolean> future = httpclient.execute(HttpAsyncMethods.createGet(url), new MyResponseConsumer(),
					null);
			System.out.println("Apache Async Content Streaming Exchange : Response Time  for execute : "
					+ (System.currentTimeMillis() - startTime));

			startTime = System.currentTimeMillis();
			Boolean result = future.get();
			System.out.println("Apache Async Content Streaming Exchange : Response Time  for future.get : "
					+ (System.currentTimeMillis() - startTime));

			if (result != null && result.booleanValue()) {
				System.out.println("Apache Async Content Streaming Exchange : Request successfully executed");
			} else {
				System.out.println("Apache Async Content Streaming Exchange : Request failed");
			}
			System.out.println("Apache Async Content Streaming Exchange : Shutting down");
			httpclient.close();
		} catch (IOException e) {
			System.err.println(e);
		} catch (InterruptedException e) {
			System.err.println(e);
		} catch (ExecutionException e) {
			System.err.println(e);
		}

		return "asynchronous HTTP exchange for content streaming called...";
	}

	static class MyResponseConsumer extends AsyncCharConsumer<Boolean> {

		protected void onResponseReceived(final HttpResponse response) {
			System.out.println("Apache Async Content Streaming Exchange : Response:" + response.getStatusLine());
		}

		protected void onCharReceived(final CharBuffer buf, final IOControl ioctrl) throws IOException {
			while (buf.hasRemaining()) {
				buf.get();
			}
		}

		protected void releaseResources() {
		}

		protected Boolean buildResult(final HttpContext context) {
			return Boolean.TRUE;
		}
	}
}
