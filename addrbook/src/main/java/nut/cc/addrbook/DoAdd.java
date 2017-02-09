/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nut.cc.addrbook;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "DoAdd", urlPatterns = {"/doadd"})
public class DoAdd extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {


        /*response.setContentType("text/html;charset=UTF-8");

        try (PrintWriter out = response.getWriter()) {
        //    TODO output your page here. You may use following sample code.
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet DoAdd</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Запрос на добавление получен (в скобочках нет)</h1>");
            out.println("</body>");
            out.println("</html>");
        }*/
    }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("index.html");
        
      /*  processRequest(request, response);
        
        PrintWriter pw = response.getWriter();
        pw.write("Запрос (POST) на добавление получен (в скобочках нет)");*/
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
