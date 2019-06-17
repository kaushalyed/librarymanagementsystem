<html>
<head>
<style>
#user-list {
  font-family: "Trebuchet MS", Arial, Helvetica, sans-serif;
  border-collapse: collapse;
  width: 100%;
}

#user-list td, #user-list th {
  border: 1px solid #ddd;
  padding: 8px;
}

#user-list tr:nth-child(even){background-color: #f2f2f2;}

#user-list tr:hover {background-color: #ddd;}

#user-list th {
  padding-top: 12px;
  padding-bottom: 12px;
  text-align: left;
  background-color: #4CAF50;
  color: white;
}
</style>

    <script type="text/javascript">
        $(document).ready(function() {
           $.ajax({
            type: 'GET',
            contentType : "application/json",
            url: 'http://localhost:8082/users',
            dataType: "json",
            success: function(response) {
              var userList = response.users;
                  jQuery.each(userList, function(i,data) {
                        var role='';
                        jQuery.each(data.roles, function(i,data) {
                          role+= data.name+',' ;
                        });
                        role = role.replace(/,\s*$/, "");

                      $("#user-table").append("<tr><td>" + data.userName + "</td><td>" + data.firstName + "  "+data.lastName+"</td><td>" + data.email + "</td><td>" + role + "</td></tr>");
                  });
            }
          });
        });
        </script>
</head>

<body>
<h1>User List</h1>
<div class="panel-body" id="user-list">
		<!-- All Users List Grid -->
        <table id="user-table">
        <tr><th>User Id</th><th>User Name</th><th>Email</th><th>Role</th></tr>
		</table>
</div>
</body>

</html>
