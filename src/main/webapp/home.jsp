<html>
<head><title>Home</title></head>
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

    <h2>Welcome to Inventory Management App</h2>
    <p>Gunakan menu di atas untuk mengelola item, inventory, order, dan laporan.</p>
</body>
</html>