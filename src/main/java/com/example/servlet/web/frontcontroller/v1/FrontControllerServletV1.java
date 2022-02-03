package com.example.servlet.web.frontcontroller.v1;

import com.example.servlet.web.frontcontroller.v1.controller.MemberFromControllerV1;
import com.example.servlet.web.frontcontroller.v1.controller.MemberListContollerV1;
import com.example.servlet.web.frontcontroller.v1.controller.MemberSaveControllerV1;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@WebServlet(name="frontControllerServletV1", urlPatterns = "/front-controller/v1/*")
public class FrontControllerServletV1 extends HttpServlet {
    private final Map<String, ControllerV1> controllerMap = new HashMap<>();

    public FrontControllerServletV1() {
        this.controllerMap.put("/front-controller/v1/members/new-form", new MemberFromControllerV1());
        this.controllerMap.put("/front-controller/v1/members/save", new MemberSaveControllerV1());
        this.controllerMap.put("/front-controller/v1/members", new MemberListContollerV1());
    }

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        log.debug("FrontControllerServletV1.service");
        ControllerV1 controller = this.controllerMap.get(request.getRequestURI());
        if(controller == null){
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return;
        }
        controller.process(request, response);
    }
}
