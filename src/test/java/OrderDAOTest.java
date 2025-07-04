import com.empresa.struts.dao.ItemDAO;
import com.empresa.struts.dao.OrderDAO;
import com.empresa.struts.models.Item;
import com.empresa.struts.models.Order;
import com.empresa.struts.util.DBUtil;
import org.junit.Assert;
import org.junit.Test;

import java.sql.Connection;
import java.util.List;

public class OrderDAOTest {

    @Test
    public void testSaveOrder() throws Exception {
        try (Connection conn = DBUtil.getConnection()) {
            ItemDAO itemDAO = new ItemDAO();
            OrderDAO orderDAO = new OrderDAO();

            List<Item> items = itemDAO.getAllItems(conn);
            Assert.assertFalse("Items must exist", items.isEmpty());

            Item item = items.get(0);

            Order order = new Order();
            order.setItemId(item.getId());
            order.setQty(1);

            orderDAO.saveOrder(conn, order);
            Assert.assertTrue(true); // sukses jika tidak exception
        }
    }
}