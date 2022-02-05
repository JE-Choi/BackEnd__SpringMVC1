package com.example.servlet.web.frontcontroller.v5.adapter;

import com.example.servlet.web.frontcontroller.ModelView;
import com.example.servlet.web.frontcontroller.MyView;
import com.example.servlet.web.frontcontroller.v2.ControllerV2;
import com.example.servlet.web.frontcontroller.v5.MyHandlerAdapter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ControllerV2HandlerAdapter implements MyHandlerAdapter {
    @Override
    public boolean supports(Object handler) {
        return (handler instanceof ControllerV2);
    }

    @Override
    public ModelView handle(HttpServletRequest request, HttpServletResponse response, Object handler) throws ServletException, IOException {
        final ControllerV2 controller = (ControllerV2) handler;
        final MyView view = controller.process(request, response);
        final String viewName = parsingViewName(view);
        return new ModelView(viewName);
    }

    private String parsingViewName(MyView view) {
        final String jspPath = view.getViewPath().split("/WEB-INF/views/")[1];
        final String viewName = jspPath.split(".jsp")[0];
        return viewName;
    }
}
