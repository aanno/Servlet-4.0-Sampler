package com.readlearncode.servlet4.mapping;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletMapping;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

/**
 * Source code github.com/readlearncode
 *
 * @author Alex Theedom www.readlearncode.com
 * @version 1.0
 */
//@WebServlet({"/path/*", "*.ext"})
public class ServletMapping extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpServletMapping servletMapping = request.getHttpServletMapping();
        response.getWriter()
                .append("<html><body>")
                .append("Value Matched: ").append(servletMapping.getMatchValue())
                .append("<br/>")
                .append("Pattern Used: ").append(servletMapping.getPattern())
                .append("<br/>")
                .append("Mapping Matched: ").append(servletMapping.getMappingMatch().name())
                .append("<br/>")
                .append("</body></html>");
    }
}
