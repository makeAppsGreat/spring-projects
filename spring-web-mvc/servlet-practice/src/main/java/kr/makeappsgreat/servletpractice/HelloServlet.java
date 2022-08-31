package kr.makeappsgreat.servletpractice;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class HelloServlet extends HttpServlet {

    @Override
    public void init() throws ServletException {
        System.out.println(">> init");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println(">> doGet");

        resp.setCharacterEncoding("UTF-8");
        resp.setContentType("text/plain; charset=utf-8");
        resp.getWriter().write("Hello, servlet!\n");
        resp.getWriter().write("Hello, " + getServletContext().getAttribute("name") + "!");
    }

    @Override
    public void destroy() {
        System.out.println(">> destroy");
    }
}
