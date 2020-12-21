package cn.itcast.travel.web.servlet;

import cn.itcast.travel.dao.impl.RouteDaoImpl;
import cn.itcast.travel.domain.Route;
import cn.itcast.travel.domain.RouteImg;
import cn.itcast.travel.domain.RoutePage;
import cn.itcast.travel.domain.User;
import cn.itcast.travel.service.FavoriteService;
import cn.itcast.travel.service.RouteService;
import cn.itcast.travel.service.impl.FavoriteServiceImpl;
import cn.itcast.travel.service.impl.RouteServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/route/*")
public class RouteServlet extends BaseServlet {
    private RouteService routeService = new RouteServiceImpl();
    private FavoriteService favoriteService = new FavoriteServiceImpl();

    /**
     * 分页查询
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void pageQuery(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String cidStr = request.getParameter("cid");
        String currentPageStr = request.getParameter("currentPage");
        String pageSizeStr = request.getParameter("pageSize");

        //接受rname线路名称
        String rname = request.getParameter("rname");
        if (rname != null) {
            //解决乱码
            rname = new String(rname.getBytes("iso-8859-1"), "utf-8");
        }

        //声明int类型的属性值，暂时设置为0；
        int cid = 0;
        int currentPage = 0;
        int pageSize = 0;
        //2.处理参数
        if (cidStr != null && cidStr.length() > 0) {
            cid = Integer.parseInt(cidStr);
        }
        //当前页码，默认为第一页
        if (currentPageStr != null && currentPageStr.length() > 0) {
            currentPage = Integer.parseInt(currentPageStr);
        } else {
            currentPage = 1;
        }
        //每页显示条数，默认每页显示10条记录
        if (pageSizeStr != null && pageSizeStr.length() > 0) {
            pageSize = Integer.parseInt(pageSizeStr);
        } else {
            pageSize = 10;
        }

        //3. 调用service查询RoutePage(分页)对象
        RoutePage<Route> routeRoutePage = routeService.pageQuery(cid, rname, currentPage, pageSize);

        //4. 将pageBean对象序列化为json，返回
        ObjectMapper mapper = new ObjectMapper();
        response.setContentType("application/json;charset=utf-8");
        mapper.writeValue(response.getOutputStream(), routeRoutePage);
    }

    /**
     * 查询详情页
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void findOne(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String rid = request.getParameter("rid");
        String sid = request.getParameter("sid");
        Route route = routeService.findOne(rid, sid);

        //4. 将pageBean对象序列化为json，返回
        ObjectMapper mapper = new ObjectMapper();
        response.setContentType("application/json;charset=utf-8");
        mapper.writeValue(response.getOutputStream(), route);
    }

    /**
     * 收藏功能
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void isFavorite(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //获取响应过来的数据
        String rid = request.getParameter("rid");
        //获取用户信息
        User user = (User) request.getSession().getAttribute("user");
        int uid;
        if(user != null) {
            //用户登录的情况
            uid = user.getUid();
        }else{
            //用户未登录的情况
            uid = 0;
        }
        //调用favoriteService来处理收藏的内容
        boolean favorite = favoriteService.isFavorite(Integer.parseInt(rid), uid);

        ObjectMapper mapper = new ObjectMapper();
        response.setContentType("application/json;charset=utf-8");
        mapper.writeValue(response.getOutputStream(),favorite);
    }

    public void addFavorite(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String rid = request.getParameter("rid");
        //获取user信息
        int uid;
        User user = (User) request.getSession().getAttribute("user");
        if(user != null) {
            //用户登录
            uid = user.getUid();
        }else{
            //用户未登录
            return;
        }
         favoriteService.add(rid,uid);
    }

    public void deleteFavorite(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String rid = request.getParameter("rid");
        //获取user信息
        int uid;
        User user = (User) request.getSession().getAttribute("user");
        if(user != null) {
            //用户登录
            uid = user.getUid();
        }else{
            //用户未登录
            return;
        }
        favoriteService.delete(rid,uid);
    }
}
