<%@ page import="MainPackage.checkLocation.VehicleCollection" %>
<%@ page import="MainPackage.checkLocation.Workroom" %>
<%@ page import="MainPackage.orm.entity.Vehicles" %>
<%@ page import="java.util.stream.Collectors" %>
<%@ page import="MainPackage.threads.Collect" %>
<%@ page import="MainPackage.core.impl.ApplicationContext" %>
<%@ page import="MainPackage.DTO.VehicleDto" %>
<%@ page import="java.util.*" %><%--
  Created by IntelliJ IDEA.
  User: Asus
  Date: 30.12.2021
  Time: 14:57
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<body>
<a  href="/">
    На главную</a>

<%

    Set<Integer> badId;
    Collect c = (Collect) request.getAttribute("collect");
    badId = c.collect();
    boolean flag = true;
    List<VehicleDto> vehiclesList= (List<VehicleDto>) request.getAttribute("vehicles");
    List<VehicleDto> brokenVehicles = vehiclesList.stream().filter(x -> badId.contains(x.getId())).collect(Collectors.toList());
%>

<table border="1">
    <tr>
        <td> Id <td>
        <td> TypeId <td>
        <td> modelName <td>
        <td> registrationNumber <td>
        <td> weight <td>
        <td> manufactureYear <td>
        <td> mileage <td>
        <td> state <td>

    </tr>
    <%
        for(VehicleDto vehicles: vehiclesList ){%>
    <tr>
        <td><%= vehicles.getId()%> <td>
        <td><%= vehicles.getTypeId()%> <td>
        <td><%= vehicles.getModelName()%>  <td>
        <td><%= vehicles.getRegistrationNumber()%> <td>
        <td><%= vehicles.getWeight()%> <td>
        <td><%= vehicles.getManufactureYear()%>  <td>
        <td><%= vehicles.getMileage()%> <td>
        <% if(badId.contains(vehicles.getId())){
             flag = true;
        }
        else{
            flag = false;
        }
        %>
        <% if(flag){%>
        <td> <%="сломана"%> </td>
        <%}%>
        <% if(flag==false){%>
        <td> <%="исправна"%> </td>
        <%}%>
    </tr>
    <%}%>
</table>
</body>
</html>
