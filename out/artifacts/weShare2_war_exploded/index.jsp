<%--
  Created by IntelliJ IDEA.
  User: lenovo
  Date: 2018/6/28
  Time: 16:59
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
  <head>
    <title>$Title$</title>
  </head>
  <body>
  <form action="/user/change_photo" method="post" enctype="multipart/form-data">
    <input name="photo" type="file"/>
    <input type="submit" value="提交"/>
  </form>
  </body>
</html>
