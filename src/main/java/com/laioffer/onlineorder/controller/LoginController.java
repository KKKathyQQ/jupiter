package com.laioffer.onlineorder.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.laioffer.onlineorder.entity.request.LoginRequestBody;
import com.laioffer.onlineorder.entity.response.LoginResponseBody;
import com.laioffer.onlineorder.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@Controller
public class LoginController {

    @Autowired
    private LoginService loginService;

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    // request和response里的session是同一个
    public void login(@RequestBody LoginRequestBody requestBody, HttpServletRequest request, HttpServletResponse response) throws IOException {
        String firstname = loginService.verifyLogin(requestBody.getUserId(), requestBody.getPassword());

        // Create a new session for the user if user ID and password are correct,
        // otherwise return Unauthorized error.
        if (!firstname.isEmpty()) {
            // Create a new session, put user ID as an attribute into the session object,
            // and set the expiration time to 600 seconds.
            HttpSession session = request.getSession();
            session.setAttribute("user_id", requestBody.getUserId());
            session.setMaxInactiveInterval(600);

            LoginResponseBody loginResponseBody = new LoginResponseBody(requestBody.getUserId(), firstname);
            response.setContentType("application/json;charset=UTF-8");
            // 往response里写入id和firstname；writeValueAsString - convert to json
            response.getWriter().print(new ObjectMapper().writeValueAsString(loginResponseBody));
        } else {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        }
    }
}
// 这个项目中session存在服务器的memory里，如果服务器重启了信息就丢失了；可靠的做法应该存入数据库，但是速度会变慢；要解决可以用token based