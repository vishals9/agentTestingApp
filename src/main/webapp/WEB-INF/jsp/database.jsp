<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">

<!-- Static content -->
<link rel="stylesheet" href="static/css/style.css">
<script type="text/javascript" src="static/js/app.js"></script>
<title>Spring Boot : DB</title>
</head>
<body>

	<ul>
		<li><a href="/">Home</a></li>
		<li><a href="http">HTTP</a></li>
		<li><a class="active" href="database">Databases</a></li>
		<li><a href="thread">Threads</a></li>
		<li><a href="ldap">LDAP</a></li>
	</ul>

	<div style="margin-left: 25%; padding: 1px 16px;">
		<form class="myForm" method="post" action="dbcallout">

			<fieldset>
				<legend>Database Services</legend>
				<p>
					<label class="choice"> <input type="radio" name="client"
						value="cloudant" checked="checked" onchange="cloudant()" />IBM
						Cloudant
					</label>
				</p>
				<p>
					<label class="choice"> <input type="radio" name="client"
						value="db2" onchange="db2()" /> IBM DB2
					</label>
				</p>
				<p>
					<label class="choice"> <input type="radio" name="client"
						value="mssql" onchange="mssql()" /> SQL Server
					</label>
				</p>
				<p>
					<label class="choice"> <input type="radio" name="client"
						value="mysql" onchange="mysql()" /> Mysql
					</label>
				</p>
				<p>
					<label class="choice"> <input type="radio" name="client"
						value="oracle" onchange="oracle()" /> Oracle Database
					</label>
				</p>
				<p>
					<label class="choice"> <input type="radio" name="client"
						value="postgres" onchange="postgres()" /> PostgreSQL
					</label>
				</p>
				<p>
					<label class="choice"> <input type="radio" name="client"
						value="redis" onchange="redis()" /> Spring Data Redis
					</label>
				</p>
				<p>
					<label class="choice"> <input type="radio" name="client"
						value="lettuce" onchange="lettuce()" /> Redis Lettuce
					</label>
				</p>
			</fieldset>

			<div id="cloudant">
				<p>
					<label>Enter the Cloudant DB name : <input type="text"
						name="dbname" value="cavisson" />
					</label>
				</p>
			</div>

			<div id="db2" style="display: none;">
				<p>
					* Driver class : com.ibm.db2.jcc.DB2Driver<br>* Table by
					default : people(firstname,lastname)<br>
				</p>

				<p>
					<label>Database Server Url : <input type="text" name="url"
						value="127.0.0.1:50000" />
					</label> <label>Database Name : <input type="text" name="dbname" /></label>
				</p>
				<p>
					<label>Database Username : <input type="text"
						name="username" />
					</label> <label>Database Password : <input type="text"
						name="password" />
					</label>
				</p>
				<p>
					<label>Firstname : <input type="text" name="firstname"
						value="Sam" />
					</label> <label>Lastname : <input type="text" name="lastname"
						value="Wise" />
					</label>
				</p>
			</div>
			<div id="mssql" style="display: none;">
				<p>
					* Driver Class : com.microsoft.sqlserver.jdbc.SQLServerDriver<br>*
					Table by default : people(firstname,lastname)<br>
				</p>

				<p>
					<label class="choice"> <input type="radio" name="prepared"
						value="yes" checked="checked" />&nbsp;Prepared&nbsp;&nbsp;&nbsp;&nbsp;
					</label> <label class="choice"> <input type="radio" name="prepared"
						value="no" />&nbsp;Non-Prepared&nbsp;&nbsp;&nbsp;
					</label>
				</p>

				<p>
					<label>Database Server Host : <input type="text"
						name="host" value="127.0.0.1" />
					</label> <label>Database Server Port : <input type="text"
						name="port" value="1433" />
					</label> <label>Database Name : <input type="text" name="dbname"
						value="user" />
					</label>
				</p>
				<p>
					<label>Database Username : <input type="text"
						name="username" value="root" />
					</label> <label>Database Password : <input type="text"
						name="password" value="root" />
					</label>
				</p>
				<p>
					<label>Firstname : <input type="text" name="firstname"
						value="Sam" />
					</label> <label>Lastname : <input type="text" name="lastname"
						value="Wise" />
					</label>
				</p>
			</div>

			<div id="mysql" style="display: none;">
				<p>
					* Ex. Port : 3306<br>* Driver Class : com.mysql.cj.jdbc.Driver<br>*
					Table by default : people(firstname,lastname)<br>
				</p>

				<p>
					<label class="choice"> <input type="radio" name="prepared"
						value="yes" checked="checked" />&nbsp;Prepared&nbsp;&nbsp;&nbsp;&nbsp;
					</label> <label class="choice"> <input type="radio" name="prepared"
						value="no" />&nbsp;Non-Prepared&nbsp;&nbsp;&nbsp;
					</label>
				</p>

				<p>
					<label>Database Server Host : <input type="text"
						name="host" value="127.0.0.1" />
					</label> <label>Database Name : <input type="text" name="dbname"
						value="user" />
					</label>
				</p>
				<p>
					<label>Database Username : <input type="text"
						name="username" value="root" />
					</label> <label>Database Password : <input type="text"
						name="password" value="admin" />
					</label>
				</p>
				<p>
					<label>Firstname : <input type="text" name="firstname"
						value="Sam" />
					</label> <label>Lastname : <input type="text" name="lastname"
						value="Wise" />
					</label>
				</p>
			</div>

			<div id="oracle" style="display: none;">
				<p>
					* Driver Class : oracle.jdbc.driver.OracleDriver<br>* Table by
					default : student(firstname,lastname)<br>
				</p>

				<p>
					<label class="choice"> <input type="radio" name="prepared"
						value="yes" checked="checked" />&nbsp;Prepared&nbsp;&nbsp;&nbsp;&nbsp;
					</label> <label class="choice"> <input type="radio" name="prepared"
						value="no" />&nbsp;Non-Prepared&nbsp;&nbsp;&nbsp;
					</label>
				</p>

				<p>
					<label>Database Server Host : <input type="text"
						name="host" value="10.10.40.12" />
					</label> <label>Database Server Port : <input type="text"
						name="port" value="49161" />
					</label>
				</p>
				<p>
					<label>Database Username : <input type="text"
						name="username" value="system" />
					</label> <label>Database Password : <input type="text"
						name="password" value="oracle" />
					</label>
				</p>
				<p>
					<label>Firstname : <input type="text" name="firstname"
						value="Sam" />
					</label> <label>Lastname : <input type="text" name="lastname"
						value="Wise" />
					</label>
				</p>

			</div>

			<div id="postgres" style="display: none;">Coming Soon!!</div>
			<div id="redis" style="display: none;">Coming Soon!!</div>
			<div id="lettuce" style="display: none;">
				<p>
					* Ex. Port : 6379<br>* Driver Class :
					com.lambdaworks.redis.RedisClient<br>
				</p>
				<p>
					<label>Database Server Host : <input type="text"
						name="host" value="127.0.0.1" />
					</label>
				</p>
			</div>
			<p>
				<button>Submit</button>
			</p>
		</form>
	</div>
</body>
</html>