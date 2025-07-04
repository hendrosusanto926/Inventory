package com.empresa.struts.actions;

import com.empresa.struts.dao.ItemDAO;
import com.empresa.struts.models.Item;
import com.empresa.struts.util.DBUtil;
import com.opensymphony.xwork2.ActionSupport;

import java.sql.Connection;
import java.util.List;

public class ItemAction extends ActionSupport {
    private int page = 1;
    private int pageSize = 5;
    private int totalItems;
    private int totalPages;

    private Item item = new Item();
    private List<Item> items;
    private int id;

    public String execute() {
        try (Connection conn = DBUtil.getConnection()) {
            ItemDAO dao = new ItemDAO();
            totalItems = dao.countItems(conn);
            totalPages = (int) Math.ceil((double) totalItems / pageSize);
            int offset = (page - 1) * pageSize;
            items = dao.getItemsWithStockPaginated(conn, offset, pageSize);
            return SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
            return ERROR;
        }
    }

    public String editItem() {
        try (Connection conn = DBUtil.getConnection()) {
            ItemDAO dao = new ItemDAO();
            item = dao.getItemById(conn, id);
            return SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
            return ERROR;
        }
    }

    public String saveItem() {
        try (Connection conn = DBUtil.getConnection()) {
        	if (item.getPrice() <= 0) {
                addActionError("Harga item harus lebih dari 0.");
                ItemDAO dao = new ItemDAO();
                totalItems = dao.countItems(conn);
                totalPages = (int) Math.ceil((double) totalItems / pageSize);
                int offset = (page - 1) * pageSize;
                items = dao.getItemsWithStockPaginated(conn, offset, pageSize);
                setItem(item);
                return INPUT; // agar item list tetap muncul
            }
            ItemDAO dao = new ItemDAO();
            if (item.getId() == 0) {
                dao.saveItem(conn, item);
            } else {
                dao.updateItem(conn, item);
            }
            return SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
            return ERROR;
        }
    }

    public String deleteItem() {
        try (Connection conn = DBUtil.getConnection()) {
            ItemDAO dao = new ItemDAO();
            dao.deleteItem(conn, id); // pakai id langsung
            return SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
            return ERROR;
        }
    }

    // Getters and setters
    public List<Item> getItems() { return items; }
    public Item getItem() { return item; }
    public void setItem(Item item) { this.item = item; }

    public int getPage() { return page; }
    public void setPage(int page) { this.page = page; }
    public int getTotalItems() { return totalItems; }
    public int getTotalPages() { return totalPages; }

	public void setId(int id) {
	    this.id = id;
	}
}