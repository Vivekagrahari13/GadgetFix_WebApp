<%@page import="java.util.HashMap"%>
<%@page import="java.util.ArrayList"%>
<%@page import="com.fix_gadget.models.DAO"%>
<%
	String admin_name=(String)session.getAttribute("admin_name");
	if(admin_name==null){
		session.setAttribute("msg", "Please Login First!");
		response.sendRedirect("index.jsp");
	}else{
%>
<!DOCTYPE html>
<html>

<head>
<title>GadgetFix</title>
<link rel="icon" href="resources/logo.png" />

<meta name="viewport" content="width=device-width, initial-scale=1">

<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
<script
	src="https://cdn.jsdelivr.net/npm/bootstrap@4.6.1/dist/js/bootstrap.bundle.min.js"></script>
<link rel="stylesheet"
	href="https://cdn.jsdelivr.net/npm/bootstrap@4.6.1/dist/css/bootstrap.min.css" />

<!-- font-awesome  -->
<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.2.0/css/all.min.css">
<script
	src="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.2.0/js/all.min.js"></script>

<!-- Lightbox CSS & Script -->
<script src="resources/lightbox/ekko-lightbox.js"></script>
<link rel="stylesheet" href="resources/lightbox/ekko-lightbox.css" />
<!-- Lightbox END -->

<!-- AOS css and JS -->
<link rel="stylesheet" href="resources/aos/aos.css">
<script src="resources/aos/aos.js"></script>
<!-- AOS css and JS END-->

<!-- custom css -->
<link rel="stylesheet" href="resources/custom.css">

<meta name="author" content="Vivek Agrahari" />
<meta name="description"
	content="This is a website for Gadget Repair Service." />
<meta name="keywords" content="" />
</head>

<body>
	<%
		String msg=(String)session.getAttribute("msg");
		if(msg!=null && msg.contains("Success")){
	%>
	<div class="alert alert-success text-center" role="alert">
		<%= msg %>
	</div>
	<%		  
			session.setAttribute("msg", null);
		}else if(msg!=null){
	%>
	<div class="alert alert-danger text-center" role="alert">
		<%= msg %>
	</div>
	<%		  
			session.setAttribute("msg", null);
		}
	%>
	<section class="bg-dark" id="contact">
		<a id="contact-mail" href="mailto:info@gadgetfix.com"><i
			class="fa-solid fa-envelope"></i> info@gadgetfix.com</a> <a
			id="contact-phone" href="tel:8081860451"><i
			class="fa-solid fa-mobile-screen-button"></i> <strong>8081860451</strong></a>
	</section>
	<nav class="navbar navbar-expand-sm bg-light">
		<a href="index.jsp" id="logo" class="navbar-brand"> <img
			src="resources/logo.png" alt="">Gadget<span>Fix</span>
		</a>
		<button class="navbar-toggler" type="button" data-toggle="collapse"
			data-target="#my-navbar">
			<i class="fa-solid fa-bars"></i>
		</button>
		<div class="collapse navbar-collapse" id="my-navbar">
			<ul class="navbar-nav ml-auto">
				<!-- <ul class="navbar-nav mr-auto"> -->
				<!-- <ul class="navbar-nav mx-auto"> -->
				<li><a href="AdminHome.jsp">Home</a></li>
				<li><a href="RepairExperts.jsp">RepairExperts</a></li>
				<li>Welcome: <b><%= admin_name %></b>
				</li>
				<li><a href="Logout" class="text-danger">Logout</a></li>
			</ul>
		</div>
	</nav>
	<h4 class="bg-primary text-white p-2">All Enquiries</h4>
	<div>
		<%
  	DAO db=new DAO();
  	ArrayList<HashMap> enquiries=db.getAllEnquiries();
	db.closeConnection();
	for(HashMap enquiry:enquiries){
  %>
		<p class="bg-light p-2 m-2">
			Id: <b><%= enquiry.get("id") %></b> Name: <b><%= enquiry.get("name") %></b>
			Phone: <b><%= enquiry.get("phone") %></b> Status: <b><%= enquiry.get("status") %></b>
			Date: <b><%= enquiry.get("e_date") %></b>
			<%
  			if( ((String)enquiry.get("status")).equalsIgnoreCase("Pending")){
  		%>
			<a class="btn btn-success btn-sm"
				href="ChangeEnquiryStatus?id=<%= enquiry.get("id") %>&status=Done">Done</a>
			<%		
  			}else{
  		%>
			<a class="btn btn-danger btn-sm"
				href="ChangeEnquiryStatus?id=<%= enquiry.get("id") %>&status=Pending">Pending</a>
			<%		
  			}
  		%>

		</p>
		<%		
	}
  %>
	</div>


	<footer class="bg-dark p-2 text-white text-center">
		<p>&copy; Rights Reserved.</p>
	</footer>
	<a id="top-button"><i class="fa-solid fa-circle-up"></i></a>

</body>
<script>
    AOS.init();
    //script for scroll to top
    $("#top-button").click(function () {
        $("html, body").animate({scrollTop: 0}, 1000);
    });
    //script for light box
    $(document).on('click', '[data-toggle="lightbox"]', function(event) {
        event.preventDefault();
        $(this).ekkoLightbox();
    });
</script>
</html>
<%		
	}
%>