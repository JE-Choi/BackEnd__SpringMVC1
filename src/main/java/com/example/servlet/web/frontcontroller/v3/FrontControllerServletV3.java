package com.example.servlet.web.frontcontroller.v3;

import com.example.servlet.web.frontcontroller.ModelView;
import com.example.servlet.web.frontcontroller.MyView;
import com.example.servlet.web.frontcontroller.v3.controller.MemberFromControllerV3;
import com.example.servlet.web.frontcontroller.v3.controller.MemberListContollerV3;
import com.example.servlet.web.frontcontroller.v3.controller.MemberSaveControllerV3;
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
@WebServlet(name="frontControllerServletV3", urlPatterns = "/front-controller/v3/*")
public class FrontControllerServletV3 extends HttpServlet {
    private final Map<String, ControllerV3> controllerMap = new HashMap<>();

    public FrontControllerServletV3() {
        this.controllerMap.put("/front-controller/v3/members/new-form", new MemberFromControllerV3());
        this.controllerMap.put("/front-controller/v3/members/save", new MemberSaveControllerV3());
        this.controllerMap.put("/front-controller/v3/members", new MemberListContollerV3());
    }

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        final ControllerV3 controller = this.controllerMap.get(request.getRequestURI());
        if(controller == null){
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return;
        }
        final Map<String, String> paramMap = createParamMap(request);
        final ModelView modelView = controller.process(paramMap);
        final MyView view = viewResolver(request, response, modelView);
        view.render(modelView.getModel(), request, response);
    }

    /**
     * view의 논리경로를 실제경로로 변환한다.
     */
    private MyView viewResolver(HttpServletRequest request, HttpServletResponse response, ModelView modelView) throws ServletException, IOException {
        return new MyView("/WEB-INF/views/" + modelView.getViewName() + ".jsp");
    }

    @NonNull
    private Map<String, String> createParamMap(HttpServletRequest request) {
        final Map<String, String> paramMap = new HashMap<>();
        request.getParameterNames().asIterator().forEachRemaining(paramName -> paramMap.put(paramName, request.getParameter(paramName)));
        return paramMap;
    }
}
