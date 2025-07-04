import com.empresa.struts.dao.InventoryDAO;
import com.empresa.struts.dao.ItemDAO;
import com.empresa.struts.models.Inventory;
import com.empresa.struts.models.Item;
import com.empresa.struts.util.DBUtil;
import org.junit.Assert;
import org.junit.Test;

import java.sql.Connection;
import java.util.List;

public class InventoryDAOTest {

    @Test
    public void testSaveInventory() throws Exception {
        try (Connection conn = DBUtil.getConnection()) {
            InventoryDAO dao = new InventoryDAO();
            ItemDAO itemDAO = new ItemDAO();

            // Ambil item pertama dari DB
            List<Item> items = itemDAO.getAllItems(conn);
            Assert.assertTrue("There must be at least one item", items.size() > 0);
            Item item = items.get(0);

            // Tambah top-up inventaris
            Inventory inv = new Inventory();
            inv.setItemId(item.getId());
            inv.setQty(10);
            inv.setType("T");

            dao.saveInventory(conn, inv);
            Assert.assertTrue(true); // jika tidak error, sukses
        }
    }
}