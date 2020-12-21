package cn.itcast.travel.dao;

import cn.itcast.travel.domain.Seller;

import java.util.List;

public interface SellerDao {
    /**
     * 利用sid查询商家信息
     * @param sid
     * @return
     */
    public Seller findSeller(int sid);
}
