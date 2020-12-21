package cn.itcast.travel.dao;

import cn.itcast.travel.domain.Favorite;
import cn.itcast.travel.domain.Route;

import java.util.List;

public interface FavoriteDao {

    /**
     * 通过rid和uid与数据库交互，查询favorite对象（查询用户是否收藏）
     * @param rid
     * @param uid
     * @return
     */
    public Favorite findByRidAndUid(int rid,int uid);

    /**
     * 查询收藏总条数
     * @param rid
     * @return
     */
    public int findCountByRid(int rid);

    /**
     * 添加收藏操作
     * @param rid
     * @param uid
     */
    public void add(int rid,int uid);

    /**
     * 删除操作
     * @param rid
     * @param uid
     */
    public void delete(int rid,int uid);

    /**
     * 通过uid查询用户收藏过的路线rid
     * @param uid
     * @return
     */
    public List<Favorite> findMyFavoriteByUid(int uid);

    /**
     * 通过uid查询总收藏数
     * @param uid
     * @return
     */
    public int findTotalCount(int uid);

}
