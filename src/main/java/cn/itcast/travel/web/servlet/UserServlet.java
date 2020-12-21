package cn.itcast.travel.web.servlet;

import cn.itcast.travel.domain.ResultInfo;
import cn.itcast.travel.domain.User;
import cn.itcast.travel.service.UserService;
import cn.itcast.travel.service.impl.UserServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.beanutils.BeanUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;

@WebServlet("/user/*")
public class UserServlet extends BaseServlet {

    private UserService service = new UserServiceImpl();

    /**
     * 注册用户
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void regist(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //先检验验证码是否正确
        String check = request.getParameter("check");
        String checkcode_server = (String) request.getSession().getAttribute("CHECKCODE_SERVER");
        //获取之后立马清除服务器缓存的验证码
        request.getSession().removeAttribute("CHECKCODE_SERVER");

        if (checkcode_server == null || !checkcode_server.equals(check)) {
            //验证码错误
            ResultInfo info = new ResultInfo();
            //注册失败
            info.setFlag(false);
            info.setErrorMsg("验证码错误！");
            //序列化JSON并且返回给客户端
            serializationJsonToClient(info, response);
            return;
        }

        //1.获取数据
        Map<String, String[]> map = request.getParameterMap();

        //2.封装对象
        User user = new User();
        try {
            //调用BeanUtils工具类中的populate方法，把获取到的数据写入到user对象中
            BeanUtils.populate(user, map);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }

        //3.响应service完成注册
//        UserService service = new UserServiceImpl();
        //调用对应的service方法，返回一个Boolean值
        boolean flag = service.regist(user);
        ResultInfo info = new ResultInfo();

        //4.响应结果
        if (flag) {
            //成功
            info.setFlag(true);
        } else {
            //失败
            info.setFlag(false);
            info.setErrorMsg("注册失败！");
        }

        //序列化JSON并且返回给客户端
        serializationJsonToClient(info, response);
    }

    /**
     * 登录操作
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void login(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //先检验验证码是否正确
        String check = request.getParameter("check");
        String checkcode_server = (String) request.getSession().getAttribute("CHECKCODE_SERVER");
        //获取之后立马清除服务器缓存的验证码
        request.getSession().removeAttribute("CHECKCODE_SERVER");

        if (checkcode_server == null || !checkcode_server.equals(check)) {
            //验证码错误
            ResultInfo info = new ResultInfo();
            //注册失败
            info.setFlag(false);
            info.setErrorMsg("验证码错误！");

            //将info序列化为JSON
            serializationJsonToClient(info, response);
            return;
        }

        //1.获得数据
        Map<String, String[]> map = request.getParameterMap();

        User user = new User();

        //2.封装对象,把map中的数据封装到User里面
        try {
            BeanUtils.populate(user, map);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        //3.调用service完成登录
//        UserService userService = new UserServiceImpl();

        User u = service.login(user);

        ResultInfo info = new ResultInfo();

        //4.响应结果
        if (u == null) {
            //登陆失败
            info.setFlag(false);
            info.setErrorMsg("账号或密码错误！");
        } else if (u != null && "N".equals(u.getStatus())) {
            //账号未激活
            info.setFlag(false);
            info.setErrorMsg("账号未激活!");
        } else if (u != null && "Y".equals(u.getStatus())) {
            //账号激活
            request.getSession().setAttribute("user", u);
            info.setFlag(true);
        }

        //将info序列化为JSON
        ObjectMapper mapper = new ObjectMapper();
        //设置content-type
        response.setContentType("application/json;charset=utf-8");
        mapper.writeValue(response.getOutputStream(), info);
    }


    /**
     * 激活操作
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void active(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //获取数据
        String code = request.getParameter("code");
        if (code != null) {
            //调用service来完成激活
            UserService userService = new UserServiceImpl();
            boolean flag = userService.active(code);

            //判断标记
            String msg = null;
            if (flag) {
                //激活成功
                msg = "激活成功，请<a href='/travel/login.html'>登录</a>";
            } else {
                //激活失败
                msg = "激活失败，请联系管理员!";
            }
            //设置content-type
            response.setContentType("text/html;charset=utf-8");
            //将数据写回客户端
            response.getWriter().write(msg);
        }
    }

    public void exit(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
            //销毁session
            request.getSession().invalidate();

            //重定向跳转首页
            response.sendRedirect(request.getContextPath()+"/login.html");
    }

    public void find(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //从session获取数据
        User user = (User) request.getSession().getAttribute("user");
        //将数据写回客户端
        ObjectMapper mapper = new ObjectMapper();
        response.setContentType("application/json;charset=utf-8");
        mapper.writeValue(response.getOutputStream(),user);
    }

    private void serializationJsonToClient(ResultInfo info, HttpServletResponse response) throws IOException {
        //将info序列化为JSON
        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(info);

        //设置content-type
        response.setContentType("application/json;charset=utf-8");
        //将JSON数据写回客户端
        response.getWriter().write(json);
    }

}
