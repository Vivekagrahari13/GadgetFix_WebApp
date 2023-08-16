package com.fix_gadget.controllers;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.fix_gadget.models.DAO;

/**
 * Servlet implementation class AdminLogin
 */
@WebServlet("/DeleteGadget")
public class DeleteGadget extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#service(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void service(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			int id = Integer.parseInt(request.getParameter("id"));
			DAO db = new DAO();
			db.deleteGadget(id);
			db.closeConnection();
			HttpSession session = request.getSession();
			session.setAttribute("msg", "Gadget Repair Request Deletion Success !");
			response.sendRedirect("UserHome.jsp");
		} catch (Exception e) {
			e.printStackTrace();
			response.sendRedirect("ExpPage.jsp");
		}
	}

}
