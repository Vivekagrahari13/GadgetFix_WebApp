package com.fix_gadget.controllers;

import java.io.IOException;
import java.io.InputStream;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

import com.fix_gadget.models.DAO;

/**
 * Servlet implementation class AddRepairExpert
 */
@MultipartConfig
@WebServlet("/AddRepairExpert")
public class AddRepairExpert extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			String name = request.getParameter("name");
			String phone = request.getParameter("phone");
			String email = request.getParameter("email");
			String state = request.getParameter("state");
			String city = request.getParameter("city");
			String area = request.getParameter("area");
			Part part = request.getPart("photo");
			InputStream photo = part.getInputStream();
			DAO db = new DAO();
			String result = db.addRepairExpert(name, phone, email, state, city, area, photo);
			db.closeConnection();
			HttpSession session = request.getSession();
			session.setAttribute("msg", result);
			response.sendRedirect("RepairExperts.jsp");
		} catch (Exception e) {
			e.printStackTrace();
			response.sendRedirect("ExpPage.jsp");
		}
	}

}
