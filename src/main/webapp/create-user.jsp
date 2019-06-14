 <style>
 .container {
   width:1000px;
 }
 </style>


 <div class="container">

<form class="well form-horizontal"    id="create_user_form">

<fieldset>

<!-- Form Name -->
<legend><center><h2><b>Create User</b></h2></center></legend><br>

<!-- Text input-->

<div class="form-group">
  <label class="col-md-4 control-label">First Name</label>
  <div class="col-md-4 inputGroupContainer">
  <div class="input-group">
  <span class="input-group-addon"><i class="glyphicon glyphicon-user"></i></span>
  <input  name="first_name" id="first_name" placeholder="First Name" class="form-control"  type="text">
    </div>
  </div>
</div>

<!-- Text input-->

<div class="form-group">
  <label class="col-md-4 control-label" >Last Name</label>
    <div class="col-md-4 inputGroupContainer">
    <div class="input-group">
  <span class="input-group-addon"><i class="glyphicon glyphicon-user"></i></span>
  <input name="last_name" id="last_name" placeholder="Last Name" class="form-control"  type="text">
    </div>
  </div>
</div>

  <div class="form-group">
  <label class="col-md-4 control-label">Role</label>
    <div class="col-md-4 selectContainer">
    <div class="input-group">
        <span class="input-group-addon"><i class="glyphicon glyphicon-list"></i></span>
    <select name="role" class="form-control selectpicker" id="role_dropdown">
      <option value="">Assign Role</option>

    </select>
  </div>
</div>
</div>

<!-- Text input-->

<div class="form-group">
  <label class="col-md-4 control-label">Username</label>
  <div class="col-md-4 inputGroupContainer">
  <div class="input-group">
  <span class="input-group-addon"><i class="glyphicon glyphicon-user"></i></span>
  <input  name="user_name" id="user_name" placeholder="Username" class="form-control"  type="text">
    </div>
  </div>
</div>

<!-- Text input-->

<div class="form-group">
  <label class="col-md-4 control-label" >Password</label>
    <div class="col-md-4 inputGroupContainer">
    <div class="input-group">
  <span class="input-group-addon"><i class="glyphicon glyphicon-user"></i></span>
  <input name="user_password" id="user_password" placeholder="Password" class="form-control"  type="password">
    </div>
  </div>
</div>

<!-- Text input-->

<div class="form-group">
  <label class="col-md-4 control-label" >Confirm Password</label>
    <div class="col-md-4 inputGroupContainer">
    <div class="input-group">
  <span class="input-group-addon"><i class="glyphicon glyphicon-user"></i></span>
  <input name="confirm_password" id="confirm_password" placeholder="Confirm Password" class="form-control"  type="password">
    </div>
  </div>
</div>

<!-- Text input-->
       <div class="form-group">
  <label class="col-md-4 control-label">E-Mail</label>
    <div class="col-md-4 inputGroupContainer">
    <div class="input-group">
        <span class="input-group-addon"><i class="glyphicon glyphicon-envelope"></i></span>
  <input name="email" id="email" placeholder="E-Mail Address" class="form-control"  type="text">
    </div>
  </div>
</div>


<!-- Text input

<div class="form-group">
  <label class="col-md-4 control-label">Contact No.</label>
    <div class="col-md-4 inputGroupContainer">
    <div class="input-group">
        <span class="input-group-addon"><i class="glyphicon glyphicon-earphone"></i></span>
  <input name="contact_no" placeholder="(639)" class="form-control" type="text">
    </div>
  </div>
</div>-->

<!-- Select Basic -->

<!-- Success message -->
<!--
<div class="alert alert-success" role="alert" id="success_message">Success <i class="glyphicon glyphicon-thumbs-up"></i> Success!.</div>
-->
<!-- Button -->
<div class="form-group">
  <label class="col-md-4 control-label"></label>
  <div class="col-md-4"><br>
    &nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp<button type="submit" class="btn btn-warning" >&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbspSUBMIT <span class="glyphicon glyphicon-send"></span>&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp</button>
  </div>
</div>

</fieldset>
</form>
</div>


<script type="text/javascript">
$(document).ready(function() {
    $.ajax({
        type: 'GET',
        contentType : "application/json",
        url: 'http://localhost:8082/users/roles',
        dataType: "json",
        success: function(response) {
            response.forEach(function(data) {
                     var div_data="<option value="+data.id+">"+data.name+"</option>";
                     $(div_data).appendTo('#role_dropdown');
             });
        }
      });
    });


$("#create_user_form").submit(function(e) {

     var firstName = $('#first_name').val();
     var lastName = $('#last_name').val();
     var userName = $('#user_name').val();
     var userPassword = $('#user_password').val();
     var confirmUserPassword = $('#confirm_password').val();
     var email = $('#email').val();
     var roleId= $('#role_dropdown').val();
        var userData = {firstName: firstName,lastName:lastName,userName:userName,userPass:userPassword,email:email,
         roles:[
                {
                  id : roleId
                }
           ]
         };
         var userJsonData = JSON.stringify(userData);


          $.ajax({
              type: "POST",
              url: 'http://localhost:8082/users',
              data: JSON.stringify(userData),
              contentType: "application/json; charset=utf-8",
              dataType: "json",
              success: function(msg) {
                  alert("user created successfully");
                  //alert(msg.d);
              },
              error: function(msg) {
              alert('error');
              }
          });

     /*if(userPassword!=confirmUserPassword){
                 alert("Your password and confirmation password do not match.");
                 return false;
           }*/
   /* alert("firstName: "+firstName);
    alert("lastName: "+lastName);
    alert("userName: "+userName);
    alert("userPassword: "+userPassword);
    alert("confirmUserPassword: "+confirmUserPassword);
    alert("Email: "+email);*/
    //alert("roleId: "+roleId);


     //return false;
});


</script>
