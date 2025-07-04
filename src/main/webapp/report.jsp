<%@ taglib uri="/struts-tags" prefix="s" %>
<html>
<head><title>Report</title></head>
<style>
    body {
        font-family: Arial, sans-serif;
    }
    .navbar {
        background-color: #d40000; /* Merah */
        overflow: hidden;
        padding: 10px 20px;
        display: flex;
        align-items: center;
    }
    .navbar img {
        height: 40px;
        margin-right: 20px;
    }
    .navbar a {
        color: white;
        padding: 12px 16px;
        text-decoration: none;
        margin-right: 10px;
        font-weight: bold;
    }
    .navbar a:hover {
        background-color: #fff;
        color: #d40000;
        border-radius: 4px;
    }
    .container {
        margin: 20px;
    }
    table {
        border-collapse: collapse;
        width: 80%;
    }
    th, td {
        border: 1px solid #ccc;
        padding: 8px;
    }
    th {
        background-color: #f4f4f4;
    }
</style>
<div class="navbar">
    <img src="images/logo2.png" alt="Company Logo" />
    <a href="home.jsp">Home</a>
    <a href="item.action">Item</a>
    <a href="inventory.action">Inventory</a>
    <a href="order.action">Order</a>
    <a href="report.action">Report</a>
</div>

<body>

<h2>Laporan Item</h2>
<table>
    <tr>
        <th>No</th>
        <th>Item</th>
        <th>Price</th>
        <th>Total Order</th>
        <th>Total Inventory</th>
        <th>Total Revenue</th>
    </tr>
    <s:iterator value="reports" status="stat">
        <tr>
            <td><s:property value="#stat.index + 1"/></td>
            <td><s:property value="itemName"/></td>
            <td><s:property value="price"/></td>
            <td><s:property value="totalOrder"/></td>
            <td><s:property value="totalInventory"/></td>
            <td><s:property value="totalRevenue"/></td>
        </tr>
    </s:iterator>
</table>
</body>
</html>