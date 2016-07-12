<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>


<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	
  <head>
   
    <title>My JSP 'index.jsp' starting page</title>
	
  </head>
  
  <body>
  <form action="fileUpload.action" method="post" enctype="multipart/form-data">
    	name: <input type="text" name="name"><br>
    	password<input type="password" name="pass"><br>
        type: <input type="text" name="type"><br>
        文件1: <input type="file" name="file"><br>
   		     文件2:<input type="file" name="file"><br/>
        	文件3:<input type="file" name="file"><br/>
        <input type="submit" value="submit">
    </form>
  </body>
</html>
