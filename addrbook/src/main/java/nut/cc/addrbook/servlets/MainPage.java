package nut.cc.addrbook.servlets;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.annotation.WebServlet;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Все обращения на адреса ../main и ../index.html будут обрабатываться данным сервлетом
 */
@WebServlet({"/main", "/index.html"})
public class MainPage extends HttpServlet {
    
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res) 
                throws ServletException, IOException {
        req.getRequestDispatcher("main.jsp").forward(req, res);
    }
}
