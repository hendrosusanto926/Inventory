package com.empresa.struts.actions;

import com.empresa.struts.dao.ItemDAO;
import com.empresa.struts.dao.ReportDAO;
import com.empresa.struts.models.Report;
import com.empresa.struts.util.DBUtil;
import com.opensymphony.xwork2.ActionSupport;

import java.sql.Connection;
import java.util.List;

public class ReportAction extends ActionSupport {
    private List<Report> reports;

    public String execute() {
        try (Connection conn = DBUtil.getConnection()) {
            ReportDAO dao = new ReportDAO();
            reports = dao.getItemReport(conn);
            return SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
            return ERROR;
        }
    }

    public List<Report> getReports() {
        return reports;
    }
}
