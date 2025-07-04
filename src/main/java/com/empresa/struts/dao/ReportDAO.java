package com.empresa.struts.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.empresa.struts.models.Report;

public class ReportDAO {
    public List<Report> getItemReport(Connection conn) throws SQLException {
        String sql = "SELECT i.id AS item_id, i.name AS item_name, i.price, " +
                     "ISNULL(SUM(o.qty), 0) AS total_order, " +
                     "(ISNULL((SELECT SUM(QTY) FROM INVENTORY WHERE ITEM_ID = i.ID AND TYPE = 'T'), 0) - " +
                     " ISNULL((SELECT SUM(QTY) FROM INVENTORY WHERE ITEM_ID = i.ID AND TYPE = 'W'), 0) - " +
                     " ISNULL((SELECT SUM(QTY) FROM [ORDER] WHERE ITEM_ID = i.ID), 0)) AS total_inventory, " +
                     "ISNULL(SUM(o.qty), 0) * i.price AS total_revenue " +
                     "FROM item i " +
                     "LEFT JOIN [order] o ON o.item_id = i.id " +
                     "GROUP BY i.id, i.name, i.price " +
                     "ORDER BY i.name";

        List<Report> reports = new ArrayList<>();
        try (PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Report r = new Report();
                r.setItemId(rs.getInt("item_id"));
                r.setItemName(rs.getString("item_name"));
                r.setPrice(rs.getDouble("price"));
                r.setTotalOrder(rs.getInt("total_order"));
                r.setTotalInventory(rs.getInt("total_inventory"));
                r.setTotalRevenue(rs.getDouble("total_revenue"));
                reports.add(r);
            }
        }
        return reports;
    }
}