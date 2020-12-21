package cn.itcast.travel.service;

import cn.itcast.travel.domain.Favorite;
import cn.itcast.travel.domain.Route;
import cn.itcast.travel.domain.RoutePage;

import java.util.List;

public interface FavoriteService {
    /**
     * 通过rid和uid来查询用户是否收藏
     * @param rid
     * @param uid
     * @return
     */
    public boolean isFavorite(int rid,int uid);

    /**
     * 通过rid和uid添加收藏项
     * @param rid
     * @param uid
     */
    void add(String rid, int uid);

    /**
     * 删除操作
     * @param rid
     * @param uid
     */
    void delete(String rid, int uid);

    /**
     * 根据uid查询收藏的路线
     * @param uid
     * @return
     */
    public List<Favorite> findMyFavorite(int uid);

    /**
     * 通过rid分页查询,uid查询总记录数
     * @param rids
     * @param uid
     * @param currentPage
     * @param pageSize
     * @return
     */
    public RoutePage<Route> pageQuery(int[] rids,int uid, int currentPage, int pageSize);
}
