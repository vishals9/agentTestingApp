<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">

<!-- Static content -->
<link rel="stylesheet" href="static/css/style.css">
<title>Spring Boot : LDAP</title>
</head>
<body>
	<ul>
		<li><a href="/">Home</a></li>
		<li><a href="http">HTTP</a></li>
		<li><a href="database">Databases</a></li>
		<li><a href="thread">Threads</a></li>
		<li><a class="active" href="ldap">LDAP</a></li>
	</ul>

	<div style="margin-left: 25%; padding: 1px 16px; height: 500px;">
		<form class="myForm" method="post"
			enctype="application/x-www-form-urlencoded" action="ldapcallout">

			<fieldset>
				<legend>LDAP Services</legend>
				<p>
					<label>host :<input type="text" name="host"
						value="localhost">
					</label>
				</p>

				<p>
					<label>port :<input type="text" name="port" value="10389">
					</label>
				</p>
				<p>
					<label>organisation :<input type="text" name="org"
						value="Company">
					</label>
				</p>
				<p>
					<label>organisation unit :<input type="text" name="orgunit"
						value="users">
					</label>
				</p>
				<p>
					<label>Employee No. :<input type="text" name="empno"
						placeholder="Employee No.">
					</label>
				</p>
				<p>
					<label>First Name :<input type="text" name="firstname"
						placeholder="First Name">
					</label>
				</p>
				<p>
					<label>Last Name :<input type="text" name="lastname"
						placeholder="Surname">
					</label>
				</p>
			</fieldset>
			<p>
			<pre> <input type="submit" name="button" value="add">    <input
					type="submit" name="button" value="fetch">    <input
					type="submit" name="button" value="delete"> </pre>
		</form>
	</div>

</body>
</html>