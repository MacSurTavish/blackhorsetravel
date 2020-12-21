package cn.itcast.travel.dao;

import cn.itcast.travel.domain.RouteImg;

import java.util.List;

public interface RouteImgDao {
    /**
     *通过rid查询每个旅游项目的图片
     * @param rid
     * @return
     */
    public List<RouteImg> findImg(int rid);
}
