package com.empresa.struts.actions;

import com.empresa.struts.dao.ItemDAO;
import com.empresa.struts.dao.OrderDAO;
import com.empresa.struts.models.Item;
import com.empresa.struts.models.Order;
import com.empresa.struts.util.DBUtil;
import com.opensymphony.xwork2.ActionSupport;

import java.sql.Connection;
import java.util.List;

public class OrderAction extends ActionSupport {
    private int page = 1;
    private int pageSize = 5;
    private int totalPages;

    private Order order = new Order();
    private List<Order> orders;
    private List<Item> items;

    public String execute() {
        try (Connection conn = DBUtil.getConnection()) {
            OrderDAO dao = new OrderDAO();
            ItemDAO itemDao = new ItemDAO();
            int total = dao.countOrders(conn);
            totalPages = (int) Math.ceil((double) total / pageSize);
            orders = dao.getOrdersPaginated(conn, (page - 1) * pageSize, pageSize);
            items = itemDao.getAllItems(conn);
            return SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
            return ERROR;
        }
    }

    public String saveOrder() {
        try (Connection conn = DBUtil.getConnection()) {
            OrderDAO dao = new OrderDAO();
            ItemDAO itemDao = new ItemDAO();

            int itemId = order.getItemId();
            int requestedQty = order.getQty();
            int remainingStock = itemDao.getRemainingStock(conn, itemId);

            if (order.getQty() <= 0) {
                addActionError("Qty harus lebih dari 0.");
                int total = dao.countOrders(conn);
                totalPages = (int) Math.ceil((double) total / pageSize);
                orders = dao.getOrdersPaginated(conn, (page - 1) * pageSize, pageSize);
                items = itemDao.getAllItems(conn);
                setOrder(order);
                return INPUT;
            }
            // Jika update order lama, kita tambahkan kembali qty lama ke stok
            if (order.getId() != 0) {
                Order existingOrder = dao.getOrderById(conn, order.getId());
                remainingStock += existingOrder.getQty(); // restore
            }

            if (requestedQty > remainingStock) {
                addActionError("Stok tidak cukup. Sisa stok: " + remainingStock);
                int total = dao.countOrders(conn);
                totalPages = (int) Math.ceil((double) total / pageSize);
                orders = dao.getOrdersPaginated(conn, (page - 1) * pageSize, pageSize);
                items = itemDao.getAllItems(conn);
                return INPUT;
            }

            if (order.getId() == 0) {
                dao.saveOrder(conn, order);
            } else {
                dao.updateOrder(conn, order);
            }

            return SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
            return ERROR;
        }
    }

    public String editOrder() {
        try (Connection conn = DBUtil.getConnection()) {
            OrderDAO dao = new OrderDAO();
            order = dao.getOrderById(conn, order.getId());
            return execute();
        } catch (Exception e) {
            e.printStackTrace();
            return ERROR;
        }
    }

    public String deleteOrder() {
        try (Connection conn = DBUtil.getConnection()) {
            OrderDAO dao = new OrderDAO();
            dao.deleteOrder(conn, order.getId());
            return SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
            return ERROR;
        }
    }

    // Getters & Setters
    public List<Order> getOrders() { return orders; }
    public Order getOrder() { return order; }
    public void setOrder(Order order) { this.order = order; }
    public List<Item> getItems() { return items; }

    public int getPage() { return page; }
    public void setPage(int page) { this.page = page; }
    public int getTotalPages() { return totalPages; }
}
