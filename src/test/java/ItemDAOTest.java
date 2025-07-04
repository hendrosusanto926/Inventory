import com.empresa.struts.dao.ItemDAO;
import com.empresa.struts.models.Item;
import com.empresa.struts.util.DBUtil;
import org.junit.Assert;
import org.junit.Test;

import java.sql.Connection;
import java.util.List;

public class ItemDAOTest {

    @Test
    public void testGetAllItems() throws Exception {
        try (Connection conn = DBUtil.getConnection()) {
            ItemDAO dao = new ItemDAO();
            List<Item> items = dao.getAllItems(conn);
            Assert.assertNotNull(items);
        }
    }

    @Test
    public void testSaveAndDeleteItem() throws Exception {
        try (Connection conn = DBUtil.getConnection()) {
            ItemDAO dao = new ItemDAO();

            // Simpan item baru
            Item item = new Item();
            item.setName("Test Item");
            item.setPrice(99);
            dao.saveItem(conn, item);

            // Ambil semua item dan cari item dengan nama Test Item
            List<Item> items = dao.getAllItems(conn);
            Item saved = items.stream().filter(i -> "Test Item".equals(i.getName())).findFirst().orElse(null);

            Assert.assertNotNull("Saved item should exist", saved);

            // Hapus item
            dao.deleteItem(conn, saved.getId());
        }
    }
}