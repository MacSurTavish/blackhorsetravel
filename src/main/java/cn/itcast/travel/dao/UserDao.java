package cn.itcast.travel.dao;

import cn.itcast.travel.domain.User;

public interface UserDao {
    /**
     * 根据用户名查询用户对象是否存在
     * @param username
     * @return
     */
    public User findByUsername(String username);

    /**
     * 根据激活码（code）查询对象是否激活
     * @param code
     * @return
     */
    public User findByCode(String code);

    /**
     * 修改更新激活状态
     * @param user
     */
    public void updateStatus(User user);

    /**
     * 保存用户信息
     * @param user
     */
    public void save(User user);

    /**
     * 通过用户名查询密码是否正确
     * @param username
     * @param password
     * @return
     */
    public User findByUsernameAndPassword(String username,String password);
}
