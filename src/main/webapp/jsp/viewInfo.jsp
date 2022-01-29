<%@ page import="MainPackage.orm.entity.Vehicles" %>
<%@ page import="java.util.List" %>
<%@ page import="MainPackage.orm.entity.Rents" %>
<%@ page import="MainPackage.orm.entity.Types" %>
<%@ page import="java.util.stream.Collectors" %>
<%@ page import="java.sql.*" %>
<%@ page import="MainPackage.DTO.VehicleDto" %>
<%@ page import="MainPackage.PropertiesVehicle.Rent" %>
<%@ page import="MainPackage.PropertiesVehicle.VehicleType" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>

<body>
<%  List<VehicleDto> vehiclesList = (List<VehicleDto>) request.getAttribute("vehicles");
    VehicleDto vehicle = vehiclesList.stream().filter(x ->x.getId() == Long.valueOf(request.getParameter("id"))).findFirst().get();
    List<Rent> rentsList = ((List<Rent>)  request.getAttribute("rents")).stream().filter(x ->x.getId()==Long.valueOf(request.getParameter("id"))).collect(Collectors.toList());
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
            <td> color <td>
            <td> engineType <td>
            <td> taxCoefficient <td>
            <td> engineCapacity <td>

        </tr>
        <tr>
        <td><%= vehicle.getId()%> <td>
        <td><%= vehicle.getTypeId()%> <td>
        <td><%= vehicle.getModelName()%>  <td>
        <td><%= vehicle.getRegistrationNumber()%> <td>
        <td><%= vehicle.getWeight()%> <td>
        <td><%= vehicle.getManufactureYear()%>  <td>
        <td><%= vehicle.getMileage()%> <td>
        <td><%= vehicle.getColor()%> <td>
        <td><%= vehicle.getEngineName()%>  <td>
        <td><%= vehicle.getTaxCoefficient()%> <td>
        <td><%= vehicle.getPer100km()%>  <td>
        </tr>
    </table>
<br>
    <%
        List<VehicleType> types = (List<VehicleType>) request.getAttribute("types");

        Double taxc = types.stream().filter(x -> x.getTypeId()==vehicle.getTypeId()).findFirst().get().getTaxCoefficient();
        Double tax = vehicle.getWeight() *0.013 + taxc * vehicle.getTaxCoefficient() * 30 + 5;


    %>
        <td><%="Налог за месяц: " + tax  %>  <td>
<br>
<table border="1">
    <tr>

        <td> Дата <td>
        <td> Стоимость <td>

    </tr>
    <% for(Rent type : rentsList ){%>
    <tr>

        <td><%= type.getDate()%> <td>
        <td><%= type.getCost()%> <td>

    </tr>
    <%}%>

</table>
    <br>
        <%
        Double sum = rentsList.stream().collect(Collectors.summingDouble( x -> x.getCost()));
        Double income = sum - tax;
    %>
    <td><%="Cумма: " + sum  %>  <td>
    <br>
    <td><%="Доход: " + income %>  <td>
</body>
</html>
