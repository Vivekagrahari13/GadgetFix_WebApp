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
 * Servlet implementation class AddGadget
 */
@MultipartConfig
@WebServlet("/AddGadget")
public class AddGadget extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#service(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void service(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			String user_email = request.getParameter("user_email");
			String repair_expert_mail = request.getParameter("repair_expert_email");
			String gname = request.getParameter("gadget_name");
			String g_brand = request.getParameter("gadget_brand_name");
			String problem = request.getParameter("problem");
			Part part1 = request.getPart("photo1");
			InputStream photo1 = part1.getInputStream();
			Part part2 = request.getPart("photo2");
			InputStream photo2 = part2.getInputStream();
			DAO db = new DAO();
			db.addGadget(gname, g_brand, problem, photo1, photo2, user_email, repair_expert_mail);
			db.closeConnection();
			HttpSession session = request.getSession();
			session.setAttribute("msg", "Gadget Repair Request Send Success !");
			response.sendRedirect("UserHome.jsp");
		} catch (Exception e) {
			e.printStackTrace();
			response.sendRedirect("ExpPage.jsp");
		}
	}

}
