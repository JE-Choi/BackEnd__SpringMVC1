package com.example.servlet.web.frontcontroller.v4;

import com.example.servlet.web.frontcontroller.MyView;
import com.example.servlet.web.frontcontroller.v4.controller.MemberFromControllerV4;
import com.example.servlet.web.frontcontroller.v4.controller.MemberListContollerV4;
import com.example.servlet.web.frontcontroller.v4.controller.MemberSaveControllerV4;
import lombok.NonNull;
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
@WebServlet(name = "frontControllerServletV4", urlPatterns = "/front-controller/v4/*")
public class FrontControllerServletV4 extends HttpServlet {
    private final Map<String, ControllerV4> controllerMap = new HashMap<>();

    public FrontControllerServletV4() {
        this.controllerMap.put("/front-controller/v4/members/new-form", new MemberFromControllerV4());
        this.controllerMap.put("/front-controller/v4/members/save", new MemberSaveControllerV4());
        this.controllerMap.put("/front-controller/v4/members", new MemberListContollerV4());
    }

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        final ControllerV4 controller = this.controllerMap.get(request.getRequestURI());
        if (controller == null) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return;
        }
        final Map<String, String> paramMap = createParamMap(request);
        final Map<String, Object> model = new HashMap<>();
        final String viewName = controller.process(paramMap, model);
        final MyView view = viewResolver(viewName);
        view.render(model, request, response);
    }

    /**
     * view의 논리경로를 실제경로로 변환한다.
     */
    private MyView viewResolver(String viewName) throws ServletException, IOException {
        return new MyView("/WEB-INF/views/" + viewName + ".jsp");
    }

    @NonNull
    private Map<String, String> createParamMap(HttpServletRequest request) {
        final Map<String, String> paramMap = new HashMap<>();
        request.getParameterNames().asIterator().forEachRemaining(paramName -> paramMap.put(paramName, request.getParameter(paramName)));
        return paramMap;
    }
}
