<%@ page import="java.util.List" %>
<%@ page import="MainPackage.orm.entity.Types" %>
<%@ page import="java.util.Comparator" %>
<%@ page import="java.util.stream.Collectors" %>
<%@ page import="java.util.Collections" %>
<%@ page import="MainPackage.orm.entity.Vehicles" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="MainPackage.DTO.VehicleDto" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title> Машины</title>
</head>
<body>


<a class="ml-20 vertical-center" href="/">
    На главную</a>
<a class="ml-20 vertical-center" href="/viewCars">
    Сбросить фильтры</a>
<br />
<br />
<% List<VehicleDto> vehiclesList= (List<VehicleDto>) request.getAttribute("vehicles"); %>
<table border="1">
    <tr>
        <td> Id <td>
        <td> TypeId <td>
        <td> modelName <td>
        <td> registrationNumber <td>
        <td> weight <td>
        <td> manufactureYear <td>
        <td> mileage <td>


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
        <td><a href="<%="/info?id=" +vehicles.getId()%>">&#x1F4C3; </a> </td>


    </tr>
    <%}%>
</table>

<form action="/viewCars">

        <label>Тип машины: </label>
        <select name="sortVehiclesByType">
            <option>not selected</option>
            <option>1</option>
            <option>2</option>
            <option>3</option>
        </select>

        <label>Двигатель: </label>
        <select name="sortVehiclesByEngine">
            <option>not selected</option>
            <option>gasoline</option>
            <option>electrical</option>
            <option>diesel</option>
        </select>

        <label>Цвет машины: </label>
        <select name="sortVehiclesByColor">
            <option>not selected</option>
            <option>BLACK</option>
            <option>WHITE</option>
            <option>BLUE</option>
            <option>RED</option>
        </select>

    <input type="submit" value="Выбрать" />
</form>
<%  boolean flag = false;
    //List<Vehicles> sortedVehicles = new ArrayList<>();
    /*if(request.getParameter("sortVehiclesByType")!=null && !request.getParameter("sortVehiclesByType").equals("not selected"))      {
        vehiclesList= vehiclesList.stream().filter(x -> x.getTypeId().toString().equals(request.getParameter("sortVehiclesByType"))).collect(Collectors.toList());
        flag = true;
    }
    if(request.getParameter("sortVehiclesByEngine")!=null && !request.getParameter("sortVehiclesByEngine").equals("not selected"))  {
        vehiclesList = vehiclesList.stream().filter(x -> x.getEngineType().equals(request.getParameter("sortVehiclesByEngine"))).collect(Collectors.toList());
        //System.out.println(vehiclesList);
        flag = true;
    }*/
    if(request.getParameter("sortVehiclesByColor")!=null && !request.getParameter("sortVehiclesByColor").equals("not selected"))    {
        vehiclesList = vehiclesList.stream().filter(x -> x.getColor().equals(request.getParameter("sortVehiclesByColor"))).collect(Collectors.toList());
        //System.out.println(vehiclesList);
        flag = true;
    }
%>

<% if(flag==true){%>

<table border="1">
    <tr>
        <td> Id <td>
        <td> TypeId <td>
        <td> modelName <td>
        <td> registrationNumber <td>
        <td> weight <td>
        <td> manufactureYear <td>
        <td> mileage <td>
        <td> color <td>
        <td> engineType <td>
        <td> taxCoefficient <td>
        <td> engineCapacity <td>

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
        <td><%= vehicles.getColor()%> <td>
        <td><%= vehicles.getEngineName()%>  <td>
        <td><%= vehicles.getTaxCoefficient()%> <td>
        <td><%= vehicles.getPer100km()%> <td>

    </tr>
    <%}%>
</table>
<%}%>
</body>
</html>
