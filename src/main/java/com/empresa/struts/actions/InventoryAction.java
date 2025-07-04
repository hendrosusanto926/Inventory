package com.empresa.struts.actions;

import com.empresa.struts.dao.InventoryDAO;
import com.empresa.struts.dao.ItemDAO;
import com.empresa.struts.models.Inventory;
import com.empresa.struts.models.Item;
import com.empresa.struts.util.DBUtil;
import com.opensymphony.xwork2.ActionSupport;

import java.sql.Connection;
import java.util.List;

public class InventoryAction extends ActionSupport {
    private int page = 1;
    private int pageSize = 5;
    private int totalPages;
    private List<Inventory> inventories;
    private List<Item> items; // for dropdown
    private Inventory inventory = new Inventory();

    public String execute() {
        try (Connection conn = DBUtil.getConnection()) {
            InventoryDAO dao = new InventoryDAO();
            ItemDAO itemDao = new ItemDAO();
            int total = dao.countInventory(conn);
            totalPages = (int) Math.ceil((double) total / pageSize);
            inventories = dao.getInventoryPaginated(conn, (page - 1) * pageSize, pageSize);
            items = itemDao.getAllItems(conn); // for dropdown
            return SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
            return ERROR;
        }
    }

    public String saveInventory() {
        try (Connection conn = DBUtil.getConnection()) {
            InventoryDAO dao = new InventoryDAO();
            ItemDAO itemDAO = new ItemDAO();

            // Jika edit (id  0), ambil data lama dari DB
            Inventory existing = inventory.getId() != 0 ? dao.getInventoryById(conn, inventory.getId()) : null;

            if (inventory.getQty() <= 0) {
            	addActionError("Qty harus lebih dari 0."); //  INI WAJIB
            	inventories = dao.getInventoryPaginated(conn, (page - 1) * pageSize, pageSize);
                items = itemDAO.getAllItems(conn);
                int total = dao.countInventory(conn);
                totalPages = (int) Math.ceil((double) total / pageSize);
                setInventory(inventory);
                return INPUT;
            }
            if ("W".equals(inventory.getType())) {
                int currentStock = dao.getRemainingStock(conn, inventory.getItemId());

                // Jika edit, dan sebelumnya withdrawal, kurangi pengaruh qty lama
                if (existing != null && "W".equals(existing.getType())) {
                    currentStock += existing.getQty(); // rollback qty lama
                }

                // Validasi stok cukup untuk withdrawal baru
                if (inventory.getQty() > currentStock) {
                    addActionError("Withdrawal gagal: stok tidak mencukupi. Stok saat ini: " + currentStock);

                    inventories = dao.getInventoryPaginated(conn, (page - 1) * pageSize, pageSize);
                    items = itemDAO.getAllItems(conn);
                    int total = dao.countInventory(conn);
                    totalPages = (int) Math.ceil((double) total / pageSize);

                    return INPUT;
                }
            }

            // Save or update
            if (inventory.getId() == 0) {
                dao.saveInventory(conn, inventory);
            } else {
                dao.updateInventory(conn, inventory);
            }

            return SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
            return ERROR;
        }
    }




    public String editInventory() {
        try (Connection conn = DBUtil.getConnection()) {
            InventoryDAO dao = new InventoryDAO();
            inventory = dao.getInventoryById(conn, inventory.getId());
            return execute(); // reload page
        } catch (Exception e) {
            e.printStackTrace();
            return ERROR;
        }
    }

    public String deleteInventory() {
        try (Connection conn = DBUtil.getConnection()) {
            InventoryDAO dao = new InventoryDAO();

            // Ambil inventory by ID untuk dapatkan itemId dan type
            Inventory inv = dao.getInventoryById(conn, inventory.getId());

            // Jika type adalah Top Up, pastikan stok cukup setelah penghapusan
            if ("T".equals(inv.getType())) {
                int currentStock = dao.getRemainingStock(conn, inv.getItemId());
                if (currentStock - inv.getQty() < 0) {
                    addActionError("Hapus gagal: stok akan menjadi negatif. Stok saat ini: " + currentStock);
                    
                    // Isi ulang data agar pagination dan form tetap tampil
                    ItemDAO itemDAO = new ItemDAO();
                    inventories = dao.getInventoryPaginated(conn, (page - 1) * pageSize, pageSize);
                    items = itemDAO.getAllItems(conn);
                    int total = dao.countInventory(conn);
                    totalPages = (int) Math.ceil((double) total / pageSize);
                    
                    return INPUT;
                }
            }

            dao.deleteInventory(conn, inventory.getId());
            return SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
            return ERROR;
        }
    }

    // Getters and Setters
    public List<Inventory> getInventories() { return inventories; }
    public Inventory getInventory() { return inventory; }
    public void setInventory(Inventory inventory) { this.inventory = inventory; }
    public List<Item> getItems() { return items; }

    public int getPage() { return page; }
    public void setPage(int page) { this.page = page; }
    public int getTotalPages() { return totalPages; }
}