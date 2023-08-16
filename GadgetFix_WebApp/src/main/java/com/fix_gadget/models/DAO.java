package com.fix_gadget.models;

import java.io.InputStream;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;

public class DAO {
	private Connection c;

	public DAO() throws Exception {
		Class.forName("com.mysql.cj.jdbc.Driver");
		c = DriverManager.getConnection("jdbc:mysql://localhost:3306/gadgetfix", "root", "Agrahari$@13");
	}

	public void closeConnection() throws SQLException {
		c.close();
	}

	public String adminLogin(String id, String password) throws SQLException {
		PreparedStatement p = c.prepareStatement("select * from admin_login where id=? and password=?");
		p.setString(1, id);
		p.setString(2, password);
		ResultSet rs = p.executeQuery();
		if (rs.next()) {
			return rs.getString("name");
		} else {
			return null;
		}
	}

	public String repairExpertLogin(String email, String password) throws SQLException {
		PreparedStatement p = c
				.prepareStatement("select * from repair_experts where email=? and password=? and status='Active'");
		p.setString(1, email);
		p.setString(2, password);
		ResultSet rs = p.executeQuery();
		if (rs.next()) {
			return rs.getString("name");
		} else {
			return null;
		}
	}

	public String userSignIn(String email, String password) throws SQLException {
		PreparedStatement p = c.prepareStatement("select * from users where email=? and password=?");
		p.setString(1, email);
		p.setString(2, password);
		ResultSet rs = p.executeQuery();
		if (rs.next()) {
			return rs.getString("name");
		} else {
			return null;
		}
	}

	public boolean checkRepairExpertPassword(String email) throws SQLException {
		PreparedStatement p = c.prepareStatement("select * from repair_experts where email=? and password='password'");
		p.setString(1, email);
		ResultSet rs = p.executeQuery();
		if (rs.next()) {
			return true;
		} else {
			return false;
		}
	}

	public void addEnquiry(String name, String phone) throws SQLException {
		PreparedStatement p = c.prepareStatement(
				"insert into enquiries (name,phone,status,e_date) values (?,?,'Pending',CURRENT_DATE)");
		p.setString(1, name);
		p.setString(2, phone);
		p.executeUpdate();
	}

	public void addRepairAmount(int id, int repair_amount) throws SQLException {
		PreparedStatement p = c
				.prepareStatement("update gadgets set repair_amount=? , status='WaitingApproval' where id=?");
		p.setInt(1, repair_amount);
		p.setInt(2, id);
		p.executeUpdate();
	}

	public String userSignUp(String name, String phone, String email, String password) throws SQLException {
		PreparedStatement p = c.prepareStatement("insert into users (email,name,phone,password) values (?,?,?,?)");
		p.setString(1, email);
		p.setString(2, name);
		p.setString(3, phone);
		p.setString(4, password);
		try {
			p.executeUpdate();
			return "success";
		} catch (SQLIntegrityConstraintViolationException e) {
			return "Email Already Exist !";
		}
	}

	public String addRepairExpert(String name, String phone, String email, String state, String city, String area,
			InputStream photo) throws SQLException {
		PreparedStatement p = c.prepareStatement(
				"insert into repair_experts (email,name,phone,state,city,area,photo,status,password) values (?,?,?,?,?,?,?,'Active','password')");
		p.setString(1, email);
		p.setString(2, name);
		p.setString(3, phone);
		p.setString(4, state);
		p.setString(5, city);
		p.setString(6, area);
		p.setBinaryStream(7, photo);
		try {
			p.executeUpdate();
			return "Registration Success !";
		} catch (SQLIntegrityConstraintViolationException e) {
			return "Email Already Exist !";
		}
	}

	public void addGadget(String name, String brand_name, String problem, InputStream photo1, InputStream photo2,
			String user_email, String repair_expert_email) throws SQLException {
		PreparedStatement p = c.prepareStatement(
				"insert into gadgets (name,brand_name,problem,photo1,photo2,repair_amount,status,user_email,repair_expert_email) values (?,?,?,?,?,0,'Pending',?,?)");
		p.setString(1, name);
		p.setString(2, brand_name);
		p.setString(3, problem);
		p.setBinaryStream(4, photo1);
		p.setBinaryStream(5, photo2);
		p.setString(6, user_email);
		p.setString(7, repair_expert_email);
		p.executeUpdate();
	}

	public void changeEnquiryStatus(int id, String status) throws SQLException {
		PreparedStatement p = c.prepareStatement("update enquiries set status=? where id=?");
		p.setString(1, status);
		p.setInt(2, id);
		p.executeUpdate();
	}

	public void deleteGadget(int id) throws SQLException {
		PreparedStatement p = c.prepareStatement("delete from gadgets where id=?");
		p.setInt(1, id);
		p.executeUpdate();
	}

	public void changeGadgetStatus(int id, String status) throws SQLException {
		PreparedStatement p = c.prepareStatement("update gadgets set status=? where id=?");
		p.setString(1, status);
		p.setInt(2, id);
		p.executeUpdate();
	}

	public void changeRepairExpertStatus(String email, String status) throws SQLException {
		PreparedStatement p = c.prepareStatement("update repair_experts set status=? where email=?");
		p.setString(1, status);
		p.setString(2, email);
		p.executeUpdate();
	}

	public boolean changePassword(String old_password, String new_password, String email, String type)
			throws SQLException {
		PreparedStatement p = null;
		if (type.equalsIgnoreCase("repair_expert")) {
			p = c.prepareStatement("update repair_experts set password=? where email=? and password=?");
		}
		p.setString(1, new_password);
		p.setString(2, email);
		p.setString(3, old_password);
		int x = p.executeUpdate();
		if (x == 0) {
			return false;
		} else {
			return true;
		}
	}

	public ArrayList<HashMap> getAllEnquiries() throws SQLException {
		PreparedStatement p = c.prepareStatement("select * from enquiries order by e_date DESC");
		ResultSet rs = p.executeQuery();
		ArrayList<HashMap> enquiries = new ArrayList<>();
		while (rs.next()) {
			HashMap<String, Object> enquiry = new HashMap<>();
			enquiry.put("id", rs.getInt("id"));
			enquiry.put("name", rs.getString("name"));
			enquiry.put("phone", rs.getString("phone"));
			enquiry.put("status", rs.getString("status"));
			enquiry.put("e_date", rs.getDate("e_date"));
			enquiries.add(enquiry);
		}
		return enquiries;
	}

	public ArrayList<HashMap> getAllRepairExperts() throws SQLException {
		PreparedStatement p = c.prepareStatement("select * from repair_experts order by name ASC");
		ResultSet rs = p.executeQuery();
		ArrayList<HashMap> repairExperts = new ArrayList<>();
		while (rs.next()) {
			HashMap<String, Object> repairExpert = new HashMap<>();
			repairExpert.put("name", rs.getString("name"));
			repairExpert.put("phone", rs.getString("phone"));
			repairExpert.put("email", rs.getString("email"));
			repairExpert.put("state", rs.getString("state"));
			repairExperts.add(repairExpert);
		}
		return repairExperts;
	}

	public ArrayList<HashMap> getAllRepairRequestsByEmail(String type, String email) throws SQLException {
		PreparedStatement p;
		if (type.equalsIgnoreCase("user")) {
			p = c.prepareStatement("select * from gadgets where user_email=? order by id DESC");
		} else {
			p = c.prepareStatement("select * from gadgets where repair_expert_email=? order by id DESC");
		}
		p.setString(1, email);
		ResultSet rs = p.executeQuery();
		ArrayList<HashMap> gadgets = new ArrayList<>();
		while (rs.next()) {
			HashMap<String, Object> gadget = new HashMap<>();
			gadget.put("id", rs.getInt("id"));
			gadget.put("name", rs.getString("name"));
			gadget.put("brand_name", rs.getString("brand_name"));
			gadget.put("problem", rs.getString("problem"));
			gadget.put("status", rs.getString("status"));
			gadget.put("repair_amount", rs.getInt("repair_amount"));
			gadget.put("user_email", rs.getString("user_email"));
			gadget.put("repair_expert_email", rs.getString("repair_expert_email"));
			gadgets.add(gadget);
		}
		return gadgets;
	}

	public ArrayList<HashMap> getAllRepairExpertsByStateCityArea(String state, String city, String area)
			throws SQLException {
		PreparedStatement p = c.prepareStatement(
				"select * from repair_experts where state=? and city=? and area like ? order by name ASC");
		p.setString(1, state);
		p.setString(2, city);
		p.setString(3, "%" + area + "%");
		ResultSet rs = p.executeQuery();
		ArrayList<HashMap> repairExperts = new ArrayList<>();
		while (rs.next()) {
			HashMap<String, Object> repairExpert = new HashMap<>();
			repairExpert.put("name", rs.getString("name"));
			repairExpert.put("phone", rs.getString("phone"));
			repairExpert.put("email", rs.getString("email"));
			repairExpert.put("state", rs.getString("state"));
			repairExpert.put("city", rs.getString("city"));
			repairExpert.put("area", rs.getString("area"));
			repairExpert.put("status", rs.getString("status"));
			repairExperts.add(repairExpert);
		}
		return repairExperts;
	}

	public HashMap getRepairExpertDetails(String email) throws SQLException {
		PreparedStatement p = c.prepareStatement("select * from repair_experts where email=?");
		p.setString(1, email);
		ResultSet rs = p.executeQuery();
		if (rs.next()) {
			HashMap<String, Object> repairExpert = new HashMap<>();
			repairExpert.put("name", rs.getString("name"));
			repairExpert.put("phone", rs.getString("phone"));
			repairExpert.put("email", rs.getString("email"));
			repairExpert.put("state", rs.getString("state"));
			repairExpert.put("city", rs.getString("city"));
			repairExpert.put("area", rs.getString("area"));
			repairExpert.put("status", rs.getString("status"));
			return repairExpert;
		} else {
			return null;
		}
	}

	public byte[] getPhoto(String type, String email) throws SQLException {
		PreparedStatement p = null;
		if (type.equalsIgnoreCase("repair_expert")) {
			p = c.prepareStatement("select photo from repair_experts where email=?");
			p.setString(1, email);
		}
		ResultSet rs = p.executeQuery();
		if (rs.next()) {
			return rs.getBytes("photo");
		} else {
			return null;
		}
	}

	public byte[] getGadgetPhoto(int photo_no, int id) throws SQLException {
		PreparedStatement p = c.prepareStatement("select photo1,photo2 from gadgets where id=?");
		p.setInt(1, id);
		ResultSet rs = p.executeQuery();
		if (rs.next()) {
			if (photo_no == 1)
				return rs.getBytes("photo1");
			else
				return rs.getBytes("photo2");
		} else {
			return null;
		}
	}
}
