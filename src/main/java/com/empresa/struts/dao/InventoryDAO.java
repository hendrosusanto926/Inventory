package com.empresa.struts.dao;

import com.empresa.struts.models.Inventory;
import java.sql.*;
import java.util.*;

public class InventoryDAO {

    public int countInventory(Connection conn) throws SQLException {
        String sql = "SELECT COUNT(*) FROM INVENTORY";
        try (PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            return rs.next() ? rs.getInt(1) : 0;
        }
    }

    public List<Inventory> getInventoryPaginated(Connection conn, int offset, int limit) throws SQLException {
        List<Inventory> list = new ArrayList<>();
        String sql = "SELECT i.ID, i.ITEM_ID, it.NAME AS ITEM_NAME, i.QTY, i.TYPE " +
                     "FROM INVENTORY i JOIN ITEM it ON i.ITEM_ID = it.ID " +
                     "ORDER BY i.ID OFFSET ? ROWS FETCH NEXT ? ROWS ONLY";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, offset);
            ps.setInt(2, limit);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Inventory inv = new Inventory();
                inv.setId(rs.getInt("ID"));
                inv.setItemId(rs.getInt("ITEM_ID"));
                inv.setItemName(rs.getString("ITEM_NAME"));
                inv.setQty(rs.getInt("QTY"));
                inv.setType(rs.getString("TYPE"));
                list.add(inv);
            }
        }
        return list;
    }

    public Inventory getInventoryById(Connection conn, int id) throws SQLException {
        String sql = "SELECT * FROM INVENTORY WHERE ID = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                Inventory inv = new Inventory();
                inv.setId(rs.getInt("ID"));
                inv.setItemId(rs.getInt("ITEM_ID"));
                inv.setQty(rs.getInt("QTY"));
                inv.setType(rs.getString("TYPE"));
                return inv;
            }
        }
        return null;
    }

    public void saveInventory(Connection conn, Inventory inv) throws SQLException {
        String sql = "INSERT INTO INVENTORY (ITEM_ID, QTY, TYPE) VALUES (?, ?, ?)";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, inv.getItemId());
            ps.setInt(2, inv.getQty());
            ps.setString(3, inv.getType());
            ps.executeUpdate();
        }
    }
    
    public int getRemainingStock(Connection conn, int itemId) throws SQLException {
        String sql =
            "SELECT " +
            " ISNULL((SELECT SUM(QTY) FROM INVENTORY WHERE ITEM_ID = ? AND TYPE = 'T'), 0) - " +
            " ISNULL((SELECT SUM(QTY) FROM INVENTORY WHERE ITEM_ID = ? AND TYPE = 'W'), 0) - " +
            " ISNULL((SELECT SUM(QTY) FROM [ORDER] WHERE ITEM_ID = ?), 0) AS remainingStock";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, itemId);
            ps.setInt(2, itemId);
            ps.setInt(3, itemId);
            ResultSet rs = ps.executeQuery();
            return rs.next() ? rs.getInt("remainingStock") : 0;
        }
    }


    public void updateInventory(Connection conn, Inventory inv) throws SQLException {
        String sql = "UPDATE INVENTORY SET ITEM_ID=?, QTY=?, TYPE=? WHERE ID=?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, inv.getItemId());
            ps.setInt(2, inv.getQty());
            ps.setString(3, inv.getType());
            ps.setInt(4, inv.getId());
            ps.executeUpdate();
        }
    }

    public void deleteInventory(Connection conn, int id) throws SQLException {
        try (PreparedStatement ps = conn.prepareStatement("DELETE FROM INVENTORY WHERE ID = ?")) {
            ps.setInt(1, id);
            ps.executeUpdate();
        }
    }
}