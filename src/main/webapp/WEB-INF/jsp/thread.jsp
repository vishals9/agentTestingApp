<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">

<!-- Static content -->
<link rel="stylesheet" href="static/css/style.css">
<title>Spring Boot : THREAD</title>
</head>
<body>

	<ul>
		<li><a href="/">Home</a></li>
		<li><a href="http">HTTP</a></li>
		<li><a href="database">Databases</a></li>
		<li><a class="active" href="thread">Threads</a></li>
		<li><a href="ldap">LDAP</a></li>
	</ul>

	<div style="margin-left: 25%; padding: 1px 16px; height: 500px;">
		<form class="myForm" method="post"
			enctype="application/x-www-form-urlencoded" action="threadcallout">

			<fieldset>
				<legend>Thread Services</legend>
				<p>
					<label class="choice"> <input type="radio" name="client"
						value="fork" checked="checked"> Fork Join Thread
					</label>
				</p>
				<p>
					<label class="choice"> <input type="radio" name="client"
						value="simple"> Simple Thread Pool Executor
					</label>
				</p>
				<p>
					<label class="choice"> <input type="radio" name="client"
						value="scheduledRunnable"> Scheduled Thread Pool Executor
						Using Runnable Interface
					</label>
				</p>
				<p>
					<label class="choice"> <input type="radio" name="client"
						value="scheduledCallable"> Scheduled Thread Pool Executor
						Using Callable Interface
					</label>
				</p>
			</fieldset>
			<br>
			<p>
				<button>Submit</button>
			</p>

		</form>
	</div>
</body>
</html>