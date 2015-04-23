# SimpleJspServletDB

Simple CRUD (Create Retrieve Update Delete) Application using JSP, Servlet.

This application can be use as a simple example of java web application. 
I usually use this application for testing purpose.

Some modification that has been done:
- Convert to Maven project.
- Connection to database can be direct JDBC or using application server connection pool via data source.
- Connection, Statament, ResultSet object are trying to be closed after it was used.
- Ready to deploy on JBoss EAP/Wildfly using embedded H2 database.
-  Table can be created by single click from the front page.

Application originally created by Daniel Niko. 
You can read original [article](http://danielniko.com/?p=16) explaining the source code.



