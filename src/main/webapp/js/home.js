$(function() { // when DOM is ready
    $("#btncreateuser").click(function(){ // when #showhidecomment is clicked
        $("#content").load("create-user.jsp"); // load the sample.jsp page in the #chkcomments element
    });
});