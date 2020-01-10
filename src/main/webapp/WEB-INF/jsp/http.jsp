<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">

<!-- Static content -->
<link rel="stylesheet" href="static/css/style.css">
<title>Spring Boot : HTTP</title>
</head>
<body>

	<ul>
		<li><a href="/">Home</a></li>
		<li><a class="active" href="http">HTTP</a></li>
		<li><a href="database">Databases</a></li>
		<li><a href="thread">Threads</a></li>
		<li><a href="ldap">LDAP</a></li>
	</ul>

	<div style="margin-left: 25%; padding: 1px 16px; height: 500px;">
		<form class="myForm" method="post"
			enctype="application/x-www-form-urlencoded" action="httpcallout">

			<fieldset>
				<legend>HTTP Services</legend>
				<p>
					<label class="choice"> <input type="radio" name="client"
						value="apache" checked="checked"> Apache Commons
						(org.apache.commons.httpclient.HttpClient)
					</label>
				</p>
				<p>
					<label class="choice"> <input type="radio" name="client"
						value="sun"> Sun (java.net.HttpURLConnection)
					</label>
				</p>
				<p>
					<label class="choice"> <input type="radio" name="client"
						value="okhttp"> OKHTTP (okhttp3.OkHttpClient)
					</label>
				</p>
				<p>
					<label class="choice"> <input type="radio" name="client"
						value="asyncHTTP"> Simple Apache HTTP Asynchronous
						Exchange (org.apache.http.impl.nio.client.HttpAsyncClients)
					</label>
				</p>
				<p>
					<label class="choice"> <input type="radio" name="client"
						value="asyncHTTPContent"> Apache HTTP Asynchronous
						Exchange using Content Exchange
						(org.apache.http.impl.nio.client.HttpAsyncClients)
					</label>
				</p>
			</fieldset>
			<p>
				<label>URL <input type="text" name="url"
					value="http://httpbin.org/get" required>
				</label>
			</p>
			<p>
				<button>Submit</button>
			</p>

		</form>
		<form class="myForm" method="post" action="asyncHTTPCallback">
			<fieldset>
				<legend>
					<b>Apache asynchronous HTTP exchange using a callback interface</b>
				</legend>
				<p>
					<label>URL <input type="text" name="url"
						value="http://httpbin.org/get" required>
					</label>
				</p>
				<p>
					<button>Submit</button>
				</p>
			</fieldset>
		</form>
	</div>
</body>
</html>