<html>
<head>
<link rel="stylesheet" href="jqGrid-4.7.0/css/ui.jqgrid.css" type="text/css" />

  	<script type="text/javascript" src="jqGrid-5.2.1/jquery.jqGrid.js"></script>
    <script type="text/javascript" src="jqGrid-5.2.1/grid.locale-en.js"></script>
    <script type="text/javascript" src="jqGrid-5.2.1/jszip.min.js"></script>

    <script type="text/javascript">
        $(document).ready(function() {
           $.ajax({
            type: 'GET',
            contentType : "application/json",
            url: 'http://localhost:8082/users',
            dataType: "json",
            success: function(response) {

                loadUsersListGrid(response);
            }
          });
        });

        function loadUsersListGrid(data){

        	//$.jgrid.gridUnload("#users-list-table");
        	//$("#users-list-table").trigger("reloadGrid");

        	/*grid starts from here*/
        	$("#users-list-table").jqGrid({
        		data :data,
        	    colNames: ['User Name', 'First Name','Last Name', 'Role', 'Email'],
        	    colModel: [
        				   {
        				        name: 'userName',
        				        index: 'userName',
        				        width: 70,
        				        sortable: false,
        				        resizable:false},
        				    {
        				        name: 'firstName',
        				        index: 'firstName',
        				        sortable: false,
        				        width: 50},
        				    {
        				        name: 'lastName',
        				        index: 'lastName',
        				        sortable: false,
        				        resizable:false,
        				        width: 50},
        				     /*{
        				        name: 'role',
        				        index: 'role',
        				        sortable: false,
        				        resizable:false,
        				        width: 60},*/
        			        {
        				        name: 'email',
        				        index: 'email',
        				        resizable:false,
        				        sortable: false},

        	    ],
        	    loadonce: true,
        		reloadAfterSubmit:true,
        	    multiselect: true,
        	    multiboxonly: true,
        	    rowNum: 30,
        	    height: '90%',
        		rownumWidth:50
        	});

        	//jQuery("#users-list-table").jqGrid('navGrid','',{edit:false,add:false,del:false});

        	/*This search filter is user for custom search. Loaded in loadComplete()*/
        	//$("#users-list-table").jqGrid('filterToolbar', {defaultSearch : "cn"});

        }



        </script>
</head>

<body>
<div class="panel-body" id="user-list">
					<!-- All Users List Grid -->
					<div class="panel-body" id="users-grid">
						<table id="users-list-table"></table>
						<div id="users-list-grid-pager"></div>
					</div>
				</div>
</body>

</html>
