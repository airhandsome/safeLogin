<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
    <head>
        <title>文件上传</title>

        <meta http-equiv="pragma" content="no-cache">
        <meta http-equiv="cache-control" content="no-cache">
        <meta http-equiv="expires" content="0">
    </head>

    <body>
        <!-- ${pageContext.request.contextPath}/upload/execute_upload.do -->
        <!-- ${pageContext.request.contextPath}/upload1/upload1.do -->
        <!-- ${pageContext.request.contextPath}/upload2/upload2.do -->
        <!--  -->
        <form action="multiupload.action" enctype="multipart/form-data" method="post">
          	  文件1:<input type="file" name="image"><br/>
          	  文件2:<input type="file" name="image"><br/>
           	 文件3:<input type="file" name="image"><br/>
                <input type="submit" value="上传" />
        </form>
    </body>
</html>