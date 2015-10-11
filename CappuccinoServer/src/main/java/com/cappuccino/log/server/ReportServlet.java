package com.cappuccino.log.server;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/report")
public class ReportServlet extends BaseServlet {
	private static final long serialVersionUID = -7362510327516238856L;

	private static final String LOG_DIR = "C:\\Users\\Administrator\\Desktop\\Log 분석\\history\\cloudlet";

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		super.doPost(req, resp);

		String id = req.getParameter("id");
		String report = req.getParameter("report");

		FileWriter fw = new FileWriter(new File(LOG_DIR, id));
		fw.write(report);
		fw.close();
	}

}
