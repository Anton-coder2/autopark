package MainPackage.servlets;

import MainPackage.PropertiesVehicle.VehicleType;
import MainPackage.checkLocation.VehicleCollection;
import MainPackage.core.annotations.Autowired;
import MainPackage.core.impl.ApplicationContext;
import MainPackage.orm.EntityManager;
import MainPackage.orm.entity.Types;
import MainPackage.orm.impl.EntityManagerImpl;
import MainPackage.orm.service.TypesService;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebServlet("/viewTypes")
public class ViewCarTypesServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            request.setAttribute("types",EntityLoader.getTypes());
        } catch (Exception e) {
            e.printStackTrace();
        }
        RequestDispatcher dispatcher = this.getServletContext().getRequestDispatcher("/jsp/viewTypesJSP.jsp");
        dispatcher.forward(request,response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse resp) throws ServletException, IOException {

        List<VehicleType> typesList = null;
        try {
              typesList = EntityLoader.getTypes();
        } catch (Exception e) {
            e.printStackTrace();
        }

        if(request.getParameter("id")!=null && request.getParameter("name")!=null && request.getParameter("coef")!=null) {
            typesList.add(new VehicleType(Integer.parseInt(request.getParameter("id")), request.getParameter("name"), Double.parseDouble(request.getParameter("coef"))));
            try {

                EntityLoader.typesService.save(new Types(Long.valueOf(request.getParameter("id")),request.getParameter("name"),Double.parseDouble(request.getParameter("coef"))));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        request.setAttribute("types",typesList);
        RequestDispatcher dispatcher = this.getServletContext().getRequestDispatcher("/jsp/viewTypesJSP.jsp");
        dispatcher.forward(request,resp);
    }
}
