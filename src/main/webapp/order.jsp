<%@ taglib uri="/struts-tags" prefix="s" %>
<html>
<head><title>Order</title></head>
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

<div class="container">
    <h2>Order</h2>

    <s:if test="hasActionErrors()">
        <div style="color:red;">
            <s:actionerror/>
        </div>
    </s:if>

    <form action="saveOrder.action" method="post">
        <input type="hidden" name="order.id" value="<s:property value='order.id'/>" />
        Item:
        <select name="order.itemId">
            <s:iterator value="items">
                <option value="<s:property value='id'/>"
                    <s:if test="order.itemId == id">selected</s:if>>
                    <s:property value="name"/>
                </option>
            </s:iterator>
        </select>
        Qty: <input type="number" name="order.qty" required value="<s:property value='order.qty'/>"/>
        <input type="submit" value="Save"/>
    </form>

    <table border="1" cellpadding="5">
        <tr><th>Item</th><th>Qty</th><th>Action</th></tr>
        <s:iterator value="orders">
            <tr>
               
                <td><s:property value="itemName"/></td>
                <td><s:property value="qty"/></td>
                <td>
                    <a href="editOrder.action?order.id=<s:property value='id'/>">Edit</a> |
                    <a href="deleteOrder.action?order.id=<s:property value='id'/>"
                       onclick="return confirm('Delete order?')">Delete</a>
                </td>
            </tr>
        </s:iterator>
    </table>

    <div>
        <s:if test="page > 1">
            <s:a href="order.action?page=%{page - 1}">Prev</s:a>
        </s:if>
        Page <s:property value="page"/> of <s:property value="totalPages"/>
        <s:if test="page < totalPages">
            <s:a href="order.action?page=%{page + 1}">Next</s:a>
        </s:if>
    </div>
</div>
</body>
</html>