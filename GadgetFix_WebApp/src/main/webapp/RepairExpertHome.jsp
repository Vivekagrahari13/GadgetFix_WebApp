<%@page import="java.util.HashMap"%>
<%@page import="java.util.ArrayList"%>
<%@page import="com.fix_gadget.models.DAO"%>
<%
String repair_expert_name = (String) session.getAttribute("repair_expert_name");
String repair_expert_email = (String) session.getAttribute("repair_expert_email");
if (repair_expert_name == null) {
	session.setAttribute("msg", "Please Login First!");
	response.sendRedirect("index.jsp");
} else {
	DAO db = new DAO();
	boolean result = db.checkRepairExpertPassword(repair_expert_email);

	if (result) {
		response.sendRedirect("RepairExpertChangePassword.jsp");
	}
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

<meta name="author" content="Rahul Chauhan" />
<meta name="description"
	content="This is a website for Gadget Repaire Service." />
<meta name="keywords" content="" />
</head>

<body>
	<%
	String msg = (String) session.getAttribute("msg");
	if (msg != null && msg.contains("Success")) {
	%>
	<div class="alert alert-success text-center" role="alert">
		<%=msg%>
	</div>
	<%
	session.setAttribute("msg", null);
	} else if (msg != null) {
	%>
	<div class="alert alert-danger text-center" role="alert">
		<%=msg%>
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
				<li><a href="RepairExpertHome.jsp">Home</a></li>
				<li><a href="RepairExpertChangePassword.jsp">ChangePassword</a>
				</li>
				<li>Welcome: <b><%=repair_expert_name%></b>
				</li>
				<li><a href="Logout" class="text-danger">Logout</a></li>
			</ul>
		</div>
	</nav>

	<div>
		<h5 class="bg-primary text-white p-3 text-center mt-2">All Repair
			Requests</h5>
		<%
		ArrayList<HashMap> gadgets = db.getAllRepairRequestsByEmail("repair_expert", repair_expert_email);

		for (HashMap gadget : gadgets) {
		%>
		<div class="bg-warning p-2 my-2">
			<p>
				ID: <b><%=gadget.get("id")%></b> Name: <b><%=gadget.get("name")%></b>
				Brand Name: <b><%=gadget.get("brand_name")%></b> Problem: <b><%=gadget.get("problem")%></b>
			</p>
			<p>
				Status: <b><%=gadget.get("status")%></b> Repair Amount: <b><%=gadget.get("repair_amount")%></b>
				User Email: <b><%=gadget.get("user_email")%></b> <img alt=""
					src="GetGadgetPhoto?photo_no=1&id=<%=gadget.get("id")%>"
					height="100px"> <img alt=""
					src="GetGadgetPhoto?photo_no=2&id=<%=gadget.get("id")%>"
					height="100px">
			</p>
			<%
			String status = (String) gadget.get("status");
			if (status.equalsIgnoreCase("pending")) {
			%>
			<form method="post" action="AddRepairAmount">
				<input name="amount" class="form-control p-2 my-2" type="number"
					min="100" placeholder="Repair Amount" required /> <input
					type="hidden" name="id" value="<%=gadget.get("id")%>" />
				<button class="btn btn-success my-2">Submit</button>
			</form>
			<%
			} else if (status.equalsIgnoreCase("accept")) {
			%>
			<a class="btn btn-success btn-sm"
				href="ChangeGadgetStatus?id=<%=gadget.get("id")%>&status=Repaired&type=repair_expert&email=<%=gadget.get("user_email")%>">Repaired</a>
			<%
			}
			%>
		</div>
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
	$("#top-button").click(function() {
		$("html, body").animate({
			scrollTop : 0
		}, 1000);
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
%><%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
