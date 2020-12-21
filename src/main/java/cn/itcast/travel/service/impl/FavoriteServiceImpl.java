package cn.itcast.travel.service.impl;

import cn.itcast.travel.dao.FavoriteDao;
import cn.itcast.travel.dao.RouteDao;
import cn.itcast.travel.dao.RouteImgDao;
import cn.itcast.travel.dao.impl.FavoriteDaoImpl;
import cn.itcast.travel.dao.impl.RouteDaoImpl;
import cn.itcast.travel.dao.impl.RouteImgDaoImpl;
import cn.itcast.travel.domain.Favorite;
import cn.itcast.travel.domain.Route;
import cn.itcast.travel.domain.RoutePage;
import cn.itcast.travel.service.FavoriteService;

import java.util.ArrayList;
import java.util.List;

public class FavoriteServiceImpl implements FavoriteService {
    private FavoriteDao favoriteDao = new FavoriteDaoImpl();
    private RouteDao routeDao = new RouteDaoImpl();
    private RouteImgDao routeImgDao = new RouteImgDaoImpl();
    @Override
    public boolean isFavorite(int rid, int uid) {
        Favorite favorite = favoriteDao.findByRidAndUid(rid, uid);
        return favorite != null;
    }

    @Override
    public void add(String rid, int uid) {
        favoriteDao.add(Integer.parseInt(rid),uid);
    }

    @Override
    public void delete(String rid, int uid) {
        favoriteDao.delete(Integer.parseInt(rid),uid);

    }

    @Override
    public List<Favorite> findMyFavorite(int uid) {
        List<Favorite> favorites = favoriteDao.findMyFavoriteByUid(uid);
        return favorites;
    }

    @Override
    public RoutePage<Route> pageQuery(int[] rids, int uid, int currentPage, int pageSize) {
        //创建RoutePage对象
        RoutePage<Route> routePage = new RoutePage<>();

        //创建一个list集合，用于保存route对象
        List<Route> routes = new ArrayList<Route>();

        //设置当前页和每页显示数量
        routePage.setCurrentPage(currentPage);
        routePage.setPageSize(pageSize);

        //取得总记录数
        int totalCount = favoriteDao.findTotalCount(uid);
        routePage.setTotalCount(totalCount);

        //获取总页数
        int totalPage = totalCount % pageSize == 0 ? totalCount / pageSize : (totalCount / pageSize) + 1;
        routePage.setTotalPage(totalPage);

        //把拆查询到的route保存到routes集合中
        for (int i = 0; i < rids.length; i++) {
            routes.add(routeDao.findOne(rids[i]));
        }
        //设置RoutePage
        routePage.setList(routes);
        return routePage;
    }

}
