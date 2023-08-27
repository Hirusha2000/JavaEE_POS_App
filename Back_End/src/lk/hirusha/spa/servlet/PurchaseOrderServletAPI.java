
/**
 * @author: Hirusha Geeganage
 * created :8/26/2023--11:54 AM
 **/
package lk.hirusha.spa.servlet;

import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObjectBuilder;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.*;


@WebServlet(urlPatterns = {"/pages/purchase"})
public class PurchaseOrderServletAPI extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/pos", "root", "1234");
            PreparedStatement pstm = connection.prepareStatement("SELECT oid FROM Orders ORDER BY oid DESC LIMIT 1");
            ResultSet rst = pstm.executeQuery();

            resp.addHeader("Access-Control-Allow-Origin", "*");

            resp.addHeader("Content-Type", "application/json");


            JsonArrayBuilder allOrders = Json.createArrayBuilder();
            while (rst.next()) {
                String oId = rst.getString(1);

                JsonObjectBuilder orderIdObject = Json.createObjectBuilder();
                orderIdObject.add("oid", oId);


                allOrders.add(orderIdObject.build());
            }

            resp.getWriter().print(allOrders.build());

        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
}
