package com.cappuccino.log.server;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/main")
public class MainServlet extends BaseServlet {
	private static final long serialVersionUID = -7362510327516238856L;

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		super.doPost(req, resp);
		OutputStream os = resp.getOutputStream();
		BufferedOutputStream bos = new BufferedOutputStream(os);
		bos.write("{}".getBytes());
		bos.close();
	}

}
