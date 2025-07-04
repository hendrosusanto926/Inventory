package com.empresa.struts.dao;

import com.empresa.struts.models.Order;
import java.sql.*;
import java.util.*;

public class OrderDAO {

    public int countOrders(Connection conn) throws SQLException {
        String sql = "SELECT COUNT(*) FROM [ORDER]";
        try (PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            return rs.next() ? rs.getInt(1) : 0;
        }
    }

    public List<Order> getOrdersPaginated(Connection conn, int offset, int limit) throws SQLException {
        List<Order> list = new ArrayList<>();
        String sql = "SELECT o.ID, o.ITEM_ID, i.NAME AS ITEM_NAME, o.QTY " +
                     "FROM [ORDER] o JOIN ITEM i ON o.ITEM_ID = i.ID " +
                     "ORDER BY o.ID OFFSET ? ROWS FETCH NEXT ? ROWS ONLY";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, offset);
            ps.setInt(2, limit);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Order o = new Order();
                o.setId(rs.getInt("ID"));
                o.setItemId(rs.getInt("ITEM_ID"));
                o.setItemName(rs.getString("ITEM_NAME"));
                o.setQty(rs.getInt("QTY"));
                list.add(o);
            }
        }
        return list;
    }

    public Order getOrderById(Connection conn, int id) throws SQLException {
        String sql = "SELECT * FROM [ORDER] WHERE ID = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                Order o = new Order();
                o.setId(rs.getInt("ID"));
                o.setItemId(rs.getInt("ITEM_ID"));
                o.setQty(rs.getInt("QTY"));
                return o;
            }
        }
        return null;
    }

    public void saveOrder(Connection conn, Order o) throws SQLException {
        String sql = "INSERT INTO [ORDER] (ITEM_ID, QTY) VALUES (?, ?)";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, o.getItemId());
            ps.setInt(2, o.getQty());
            ps.executeUpdate();
        }
    }

    public void updateOrder(Connection conn, Order o) throws SQLException {
        String sql = "UPDATE [ORDER] SET ITEM_ID = ?, QTY = ? WHERE ID = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, o.getItemId());
            ps.setInt(2, o.getQty());
            ps.setInt(3, o.getId());
            ps.executeUpdate();
        }
    }

    public void deleteOrder(Connection conn, int id) throws SQLException {
        try (PreparedStatement ps = conn.prepareStatement("DELETE FROM [ORDER] WHERE ID = ?")) {
            ps.setInt(1, id);
            ps.executeUpdate();
        }
    }
}