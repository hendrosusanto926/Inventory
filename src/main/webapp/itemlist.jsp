<%@ taglib uri="/struts-tags" prefix="s" %>
<!DOCTYPE html>
<html>
<head><title>Item List</title>
</head>
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
    <h2>Item List</h2>
<s:if test="hasActionErrors()">
    <ul style="color:red;">
        <s:actionerror/>
    </ul>
    </s:if>
    <form action="saveItem.action" method="post">
        <input type="hidden" name="item.id" value="<s:property value='item.id'/>" />
        Name: <input type="text" name="item.name" value="<s:property value='item.name'/>" required />
        Price: <input type="number" name="item.price" value="<s:property value='item.price'/>" required />
        <input type="submit" value="<s:if test='item.id == 0'>Add</s:if><s:else>Update</s:else>" />
    </form>

    <table>
        <tr><th>Name</th><th>Price</th><th>Stock</th><th>Action</th></tr>
        <s:iterator value="items">
            <tr>
<%--                 <td><s:property value="id"/></td> --%>
                <td><s:property value="name"/></td>
                <td><s:property value="price"/></td>
                <td><s:property value="remainingStock"/></td>
                <td>
                    <a href="editItem.action?id=<s:property value='id'/>">Edit</a> |
                    <a href="deleteItem.action?id=<s:property value='id'/>" onclick="return confirm('Delete this item?')">Delete</a>
                </td>
            </tr>
        </s:iterator>
    </table>

    <div style="margin-top:20px;">
        <s:if test="page > 1">
            <s:a href="item.action?page=%{page - 1}">Previous</s:a>
        </s:if>
        Page <s:property value="page"/> of <s:property value="totalPages"/>
        <s:if test="page < totalPages">
            <s:a href="item.action?page=%{page + 1}">Next</s:a>
        </s:if>
    </div>
</div>
</body>
</html>