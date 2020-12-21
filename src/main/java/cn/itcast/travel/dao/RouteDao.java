package cn.itcast.travel.dao;


import cn.itcast.travel.domain.Route;

import java.util.List;

public interface RouteDao {
    /**
     * 查找有多少条信息
     * @param cid
     * @param rname
     * @return
     */
    public int findTotalCount(int cid,String rname);

    /**
     * 查询每条中的数据
     * @param cid
     * @param pageSize //开始的位置
     * @param Size //每页显示数
     * @param rname
     * @return
     */
    public List<Route> findByPage(int cid,String rname,int pageSize,int Size);

    /**
     * 通过rid查询一个route对象
     * @param rid
     * @return
     */
    public Route findOne(int rid);

}
