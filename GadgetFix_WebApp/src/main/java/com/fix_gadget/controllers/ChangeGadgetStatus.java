package com.fix_gadget.controllers;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.fix_gadget.models.DAO;
import com.fix_gadget.models.SendMail;

/**
 * Servlet implementation class AdminLogin
 */
@WebServlet("/ChangeGadgetStatus")
public class ChangeGadgetStatus extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#service(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void service(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			int id = Integer.parseInt(request.getParameter("id"));
			String status = request.getParameter("status");
			String email = request.getParameter("email");
			DAO db = new DAO();
			db.changeGadgetStatus(id, status);
			db.closeConnection();

			String body = "";
			if (status.equalsIgnoreCase("accept")) {
				body = "Your repair amount has been approved.";
			} else if (status.equalsIgnoreCase("decline")) {
				body = "Your repair amount has been declined.";
			} else if (status.equalsIgnoreCase("repaired")) {
				body = "Your Gadget has been Repaired.";
			}
			// mail send code
			SendMail.sendMail(email, "Gadget Repair Request Status", body);
			// mail send code --end

			HttpSession session = request.getSession();
			session.setAttribute("msg", "Status Updation Success !");
			String type = request.getParameter("type");
			if (type.equalsIgnoreCase("user")) {
				response.sendRedirect("UserHome.jsp");
			} else {
				response.sendRedirect("RepairExpertHome.jsp");
			}

		} catch (Exception e) {
			e.printStackTrace();
			response.sendRedirect("ExpPage.jsp");
		}
	}

}
