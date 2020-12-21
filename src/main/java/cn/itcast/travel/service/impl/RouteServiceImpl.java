package cn.itcast.travel.service.impl;

import cn.itcast.travel.dao.FavoriteDao;
import cn.itcast.travel.dao.RouteDao;
import cn.itcast.travel.dao.RouteImgDao;
import cn.itcast.travel.dao.SellerDao;
import cn.itcast.travel.dao.impl.FavoriteDaoImpl;
import cn.itcast.travel.dao.impl.RouteDaoImpl;
import cn.itcast.travel.dao.impl.RouteImgDaoImpl;
import cn.itcast.travel.dao.impl.SellerDaoImpl;
import cn.itcast.travel.domain.*;
import cn.itcast.travel.service.RouteService;

import java.util.List;

public class RouteServiceImpl implements RouteService {
    private RouteDao routeDao = new RouteDaoImpl();
    private RouteImgDao routeImgDao = new RouteImgDaoImpl();
    private SellerDao sellerDao = new SellerDaoImpl();
    private FavoriteDao favoriteDao = new FavoriteDaoImpl();
    @Override
    public RoutePage<Route> pageQuery(int cid,String rname ,int currentPage, int pageSize) {
        RoutePage<Route> routeRoutePage = new RoutePage<>();
        routeRoutePage.setCurrentPage(currentPage);
        routeRoutePage.setPageSize(pageSize);
        //取得总记录数
        int totalCount = routeDao.findTotalCount(cid,rname);
        routeRoutePage.setTotalCount(totalCount);
        //设置当前页显示的数据集合
        int start = (currentPage - 1) * pageSize;
        List<Route> list = routeDao.findByPage(cid,rname,start,pageSize);
        routeRoutePage.setList(list);
        //获取总页数
        int totalPage = totalCount % pageSize == 0 ? totalCount / pageSize : (totalCount / pageSize) + 1;
        routeRoutePage.setTotalPage(totalPage);
        return routeRoutePage;
    }

    @Override
    public Route findOne(String rid,String sid) {
        //使用rid先查询对应的route对象
        Route route = routeDao.findOne(Integer.parseInt(rid));
        //再使用rid查询对应的图片对象
        List<RouteImg> routeImgs = routeImgDao.findImg(Integer.parseInt(rid));
        //调用sid查询商户信息
        Seller seller = sellerDao.findSeller(Integer.parseInt(sid));
        //调用rid查询收藏数量
        int count = favoriteDao.findCountByRid(Integer.parseInt(rid));
        //把图片设置到route对象里
        route.setRouteImgList(routeImgs);
        //把商户信息设置到route对象里
        route.setSeller(seller);
        //把收藏数量添加到route对象中
        route.setCount(count);


        return route;
    }
}
