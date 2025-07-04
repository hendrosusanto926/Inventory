package com.empresa.struts.dao;

import com.empresa.struts.models.Item;

import java.sql.*;
import java.util.*;

public class ItemDAO {

    public int countItems(Connection conn) throws SQLException {
        String sql = "SELECT COUNT(*) FROM ITEM";
        try (PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            return rs.next() ? rs.getInt(1) : 0;
        }
    }

    public List<Item> getItemsWithStockPaginated(Connection conn, int offset, int limit) throws SQLException {
        List<Item> list = new ArrayList<>();
        String sql = "SELECT i.ID, i.NAME, i.PRICE, " +
                     "(ISNULL((SELECT SUM(QTY) FROM INVENTORY WHERE ITEM_ID = i.ID AND TYPE = 'T'), 0) - " +
                     " ISNULL((SELECT SUM(QTY) FROM INVENTORY WHERE ITEM_ID = i.ID AND TYPE = 'W'), 0) - " +
                     " ISNULL((SELECT SUM(QTY) FROM [ORDER] WHERE ITEM_ID = i.ID), 0)) AS remainingStock " +
                     "FROM ITEM i ORDER BY i.ID OFFSET ? ROWS FETCH NEXT ? ROWS ONLY";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, offset);
            ps.setInt(2, limit);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Item item = new Item();
                item.setId(rs.getInt("ID"));
                item.setName(rs.getString("NAME"));
                item.setPrice(rs.getInt("PRICE"));
                item.setRemainingStock(rs.getInt("remainingStock"));
                list.add(item);
            }
        }
        return list;
    }

    public Item getItemById(Connection conn, int id) throws SQLException {
        String sql = "SELECT ID, NAME, PRICE FROM ITEM WHERE ID = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                Item item = new Item();
                item.setId(rs.getInt("ID"));
                item.setName(rs.getString("NAME"));
                item.setPrice(rs.getInt("PRICE"));
                return item;
            }
        }
        return null;
    }

    public void saveItem(Connection conn, Item item) throws SQLException {
        String sql = "INSERT INTO ITEM (NAME, PRICE) VALUES (?, ?)";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, item.getName());
            ps.setInt(2, item.getPrice());
            ps.executeUpdate();
        }
    }

    public void updateItem(Connection conn, Item item) throws SQLException {
        String sql = "UPDATE ITEM SET NAME = ?, PRICE = ? WHERE ID = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, item.getName());
            ps.setInt(2, item.getPrice());
            ps.setInt(3, item.getId());
            ps.executeUpdate();
        }
    }

    public void deleteItem(Connection conn, int id) throws SQLException {
        try (PreparedStatement ps = conn.prepareStatement("DELETE FROM ITEM WHERE ID = ?")) {
            ps.setInt(1, id);
            ps.executeUpdate();
        }
    }
    
    public List<Item> getAllItems(Connection conn) throws SQLException {
        List<Item> items = new ArrayList<>();
        String sql = "SELECT ID, NAME FROM ITEM ORDER BY NAME";

        try (PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Item item = new Item();
                item.setId(rs.getInt("ID"));
                item.setName(rs.getString("NAME"));
                items.add(item);
            }
        }

        return items;
    }
    
    public int getRemainingStock(Connection conn, int itemId) throws SQLException {
        String sql = 
            "SELECT " +
            "ISNULL((SELECT SUM(QTY) FROM INVENTORY WHERE ITEM_ID = ? AND TYPE = 'T'), 0) - " +
            "ISNULL((SELECT SUM(QTY) FROM INVENTORY WHERE ITEM_ID = ? AND TYPE = 'W'), 0) - " +
            "ISNULL((SELECT SUM(QTY) FROM [ORDER] WHERE ITEM_ID = ?), 0) AS remainingStock";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, itemId);
            ps.setInt(2, itemId);
            ps.setInt(3, itemId);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("remainingStock");
                }
            }
        }

        return 0; // default jika tidak ditemukan
    }
}