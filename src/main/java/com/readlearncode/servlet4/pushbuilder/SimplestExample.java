package com.readlearncode.servlet4.pushbuilder;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * A simple POC use of the Server Push feature.
 *
 * Source code github.com/readlearncode
 *
 * @author Alex Theedom www.readlearncode.com
 * @version 1.0
 */
@WebServlet("/simplestexample")
public class SimplestExample extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        request.newPushBuilder()
                .path("resources/images/coffee-cup.jpg")
                .push();

        getServletContext().getRequestDispatcher("/coffee-cup.jsp").forward(request, response);

    }
}
