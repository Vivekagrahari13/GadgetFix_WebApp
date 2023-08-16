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
@WebServlet("/AdminLogin")
public class AdminLogin extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#service(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String id = req.getParameter("id");
		String password = req.getParameter("password");
		try {
			DAO db = new DAO();
			String name = db.adminLogin(id, password);
			db.closeConnection();
			HttpSession session = req.getSession();
			if (name == null) {
				session.setAttribute("msg", "Invalid Entries!");
				resp.sendRedirect("index.jsp");
			} else {
				session.setAttribute("admin_name", name);
				resp.sendRedirect("AdminHome.jsp");
			}
		} catch (Exception e) {
			e.printStackTrace();
			resp.sendRedirect("ExpPage.jsp");
		}
	}

}
