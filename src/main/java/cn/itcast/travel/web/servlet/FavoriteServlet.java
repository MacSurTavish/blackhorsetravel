package cn.itcast.travel.web.servlet;

import cn.itcast.travel.dao.RouteDao;
import cn.itcast.travel.dao.RouteImgDao;
import cn.itcast.travel.dao.impl.RouteDaoImpl;
import cn.itcast.travel.dao.impl.RouteImgDaoImpl;
import cn.itcast.travel.domain.Favorite;
import cn.itcast.travel.domain.Route;
import cn.itcast.travel.domain.RoutePage;
import cn.itcast.travel.domain.User;
import cn.itcast.travel.service.FavoriteService;
import cn.itcast.travel.service.impl.FavoriteServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/favorite/*")
public class FavoriteServlet extends BaseServlet {
    private FavoriteService favoriteService = new FavoriteServiceImpl();
    public void myFavorite(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //从session获取数据
        User user = (User) request.getSession().getAttribute("user");
        String currentPageStr = request.getParameter("currentPage");
        String pageSizeStr = request.getParameter("pageSize");

        int currentPage;
        int pageSize;
        RoutePage<Route> routeRoutePage = null;

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
            pageSize = 12;
        }

        //获取user的uid属性
        int uid = user.getUid();

        //调用favoriteService来查询收藏的数据
        List<Favorite> favorites = favoriteService.findMyFavorite(uid);

        //定义rid默认值
        if(favorites.size() > 0) {
            int[] rids = new int[favorites.size()];
            for(int i = 0; i < favorites.size();i++) {
                rids[i] = favorites.get(i).getRid();
            }
            //设置rids数组中的每个收藏
            routeRoutePage = favoriteService.pageQuery(rids,uid,currentPage,pageSize);
        }else {
            int[] rids = {0};
            routeRoutePage = favoriteService.pageQuery(rids,uid,currentPage,pageSize);
        }

        //4. 将pageBean对象序列化为json，返回
        ObjectMapper mapper = new ObjectMapper();
        response.setContentType("application/json;charset=utf-8");
        mapper.writeValue(response.getOutputStream(), routeRoutePage);

    }
}
