package cn.itcast.travel.service;


import cn.itcast.travel.domain.Route;
import cn.itcast.travel.domain.RoutePage;

public interface RouteService {
    /**
     * 分页查询
     * @param cid //类别id
     * @param rname //当前页码
     * @param pageSize //每页显示条数
     * @param currentPage
     * @return
     */
    RoutePage<Route> pageQuery(int cid,String rname ,int currentPage, int pageSize);

    /**
     * 详细页查询
     * @param rid //图片id
     * @param sid //商户id
     * @return
     */
    Route findOne(String rid,String sid);
}
