package cz.cvut.kbss.ear.eshop;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@WebServlet(name = "HelloWorldServlet", urlPatterns = {"/*"})
public class HelloWorld extends HttpServlet {

    private static final Logger LOG = LoggerFactory.getLogger(HelloWorld.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        LOG.debug("Serving request from {}.", req.getRemoteHost());
        resp.getOutputStream().print("<html><head><title>EAR 2020</title></head><body><h1>Hello, world!</h1></body></html>");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        doGet(req, resp);
    }
}
