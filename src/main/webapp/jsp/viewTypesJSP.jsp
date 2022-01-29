<%@ page import="java.util.List" %>
<%@ page import="MainPackage.orm.entity.Types" %>
<%@ page import="java.util.Comparator" %>
<%@ page import="java.util.stream.Collectors" %>
<%@ page import="java.util.Collections" %>
<%@ page import="MainPackage.PropertiesVehicle.VehicleType" %>
<%@ page import="MainPackage.servlets.EntityLoader" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Типы машин</title>
</head>
<body>


<a class="ml-20 vertical-center" href="/">
    На главную</a>

<br />
<br />
<% List<VehicleType> typesList= ((List<VehicleType>) request.getAttribute("types")).stream().sorted(Comparator.comparing(x -> x.getTypeId())).collect(Collectors.toList()); %>
<table border="1">
    <tr>
    <td> Id <td>
    <td> Name <td>
    <td> coefTaxes <td>
    </tr>
    <% for(VehicleType type : typesList ){%>
    <tr>
        <td><%= type.getTypeId()%> <td>
        <td><%= type.getTypeName()%> <td>
        <td><%= type.getTaxCoefficient()%>  <td>
    </tr>
    <%}%>
</table>
<br />
<a > Минимальный коэффициент: <%= ((List<VehicleType>) request.getAttribute("types")).stream().min(Comparator.comparing(x -> x.getTaxCoefficient())).get().getTaxCoefficient()%> </a>
<br />
<a > Максимальный коэффициент: <%= ((List<VehicleType>) request.getAttribute("types")).stream().max(Comparator.comparing(x -> x.getTaxCoefficient())).get().getTaxCoefficient()%> </a>
<br />
<a class="ml-20 vertical-center" href="/viewTypes?sort=Yes">
    Сортировка по коэффициенту</a>


<%
    String criterion= request.getParameter("sort");
    System.out.println("Критерий" +criterion);%>

    <%if(criterion!= null && criterion.equals("Yes")){ %>
        <br />
        <a class="ml-20 vertical-center" href="/viewTypes?sort=1">
            Сортировка по возрастанию</a>

        <a class="ml-20 vertical-center" href="/viewTypes?sort=2">
            Сортировка по убыванию </a>
    <%}%>

    <% criterion= request.getParameter("sort");
          if(criterion!= null && criterion.equals("1")){%>
            <table border="1">
                <tr>
                    <td> Id <td>
                    <td> Name <td>
                    <td> coefTaxes <td>
                </tr>
            <%for(VehicleType type : typesList.stream().sorted(Comparator.comparing(x -> x.getTaxCoefficient())).collect(Collectors.toList()) ){%>
                <tr>
                    <td><%= type.getTypeId()%> <td>
                    <td><%= type.getTypeName()%> <td>
                    <td><%= type.getTaxCoefficient()%>  <td>
                </tr>
                <%}%>
            </table>
       <%}%>

       <% criterion= request.getParameter("sort");
           if(criterion!= null && criterion.equals("2")){%>
                <table border="1">
                    <tr>
                        <td> Id <td>
                        <td> Name <td>
                        <td> coefTaxes <td>
                    </tr>
                    <% Collections.sort(typesList, Collections.reverseOrder(Comparator.comparing(x -> x.getTaxCoefficient())));
                        for(VehicleType type : typesList){%>
                    <tr>
                        <td><%= type.getTypeId()%> <td>
                        <td><%= type.getTypeName()%> <td>
                        <td><%= type.getTaxCoefficient()%>  <td>
                    </tr>
                    <%}%>
                </table>
        <%}%>

<form action="/viewTypes" method="post">

<label>Id: </label>
<input name="id">

<label>Имя: </label>
<input name="name">

<label>Коэффициент: </label>
<input name="coef">

<input type="submit" value="Выбрать" />
</form>

</body>
</html>
