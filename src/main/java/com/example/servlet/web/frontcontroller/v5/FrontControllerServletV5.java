package com.example.servlet.web.frontcontroller.v5;

import com.example.servlet.web.frontcontroller.ModelView;
import com.example.servlet.web.frontcontroller.MyView;
import com.example.servlet.web.frontcontroller.v3.controller.MemberFromControllerV3;
import com.example.servlet.web.frontcontroller.v3.controller.MemberListContollerV3;
import com.example.servlet.web.frontcontroller.v3.controller.MemberSaveControllerV3;
import com.example.servlet.web.frontcontroller.v4.ControllerV4;
import com.example.servlet.web.frontcontroller.v4.controller.MemberFromControllerV4;
import com.example.servlet.web.frontcontroller.v4.controller.MemberListContollerV4;
import com.example.servlet.web.frontcontroller.v4.controller.MemberSaveControllerV4;
import com.example.servlet.web.frontcontroller.v5.adapter.ControllerV3HandlerAdapter;
import com.example.servlet.web.frontcontroller.v5.adapter.ControllerV4HandlerAdapter;

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

@WebServlet(name = "frontControllerServletV5", urlPatterns = "/front-controller/v5/*")
public class FrontControllerServletV5 extends HttpServlet {
    private final Map<String, Object> handlerMappingMap = new HashMap<>();
    private final List<MyHandlerAdapter> handlerAdapters = new ArrayList<>();

    public FrontControllerServletV5() {
        initHandlerMappingMap();
        initHandlerAdapters();
    }

    private void initHandlerAdapters() {
        this.handlerAdapters.add(new ControllerV3HandlerAdapter());
        this.handlerAdapters.add(new ControllerV4HandlerAdapter());
    }

    private void initHandlerMappingMap() {
        this.handlerMappingMap.put("/front-controller/v5/v3/members/new-form", new MemberFromControllerV3());
        this.handlerMappingMap.put("/front-controller/v5/v3/members/save", new MemberSaveControllerV3());
        this.handlerMappingMap.put("/front-controller/v5/v3/members", new MemberListContollerV3());

        this.handlerMappingMap.put("/front-controller/v5/v4/members/new-form", new MemberFromControllerV4());
        this.handlerMappingMap.put("/front-controller/v5/v4/members/save", new MemberSaveControllerV4());
        this.handlerMappingMap.put("/front-controller/v5/v4/members", new MemberListContollerV4());
    }


    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        final Object handler = getHandler(request);
        if (handler == null) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return;
        }
        final MyHandlerAdapter handlerAdapter = getHandlerAdapter(handler);

        final ModelView modelView = handlerAdapter.handle(request, response, handler);
        final MyView view = viewResolver(modelView.getViewName());
        view.render(modelView.getModel(), request, response);
    }

    private MyView viewResolver(final String viewName) throws ServletException, IOException {
        return new MyView("/WEB-INF/views/" + viewName + ".jsp");
    }

    private MyHandlerAdapter getHandlerAdapter(Object handler) {
        for (final MyHandlerAdapter adapter : handlerAdapters) {
            if (adapter.supports(handler)) {
                return adapter;
            }
        }
        throw new IllegalArgumentException("handler adapter를 찾을 수 없습니다. handler=" + handler);
    }

    private Object getHandler(HttpServletRequest request) {
        final Object controller = this.handlerMappingMap.get(request.getRequestURI());
        return controller;
    }
}
