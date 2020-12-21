package cn.itcast.travel.dao.impl;

import cn.itcast.travel.dao.UserDao;
import cn.itcast.travel.domain.User;
import cn.itcast.travel.util.JDBCUtils;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

//DAO层是执行业务层传来的任务，与数据库交互
public class UserDaoImpl implements UserDao {
    //DAO层获取JDBC的连接
    private JdbcTemplate template = new JdbcTemplate(JDBCUtils.getDataSource());
    @Override
    public User findByUsername(String username) {
        User user = null;

        try {
            //1.定义sql
            String sql = "select * from tab_user where username = ?";
            //2.执行sql
            user = template.queryForObject(sql, new BeanPropertyRowMapper<User>(User.class), username);
        } catch (Exception e) {

        }

        return user;
    }

    @Override
    public User findByCode(String code) {
        User user = null;
        try {
            //1.定义sql语句
            String sql = "select * from tab_user where code = ?";
            //2.执行sql语句
            user = template.queryForObject(sql,new BeanPropertyRowMapper<User>(User.class),code);
        } catch (Exception e) {

        }

        return user;
    }

    @Override
    public void updateStatus(User user) {
        //1.定义sql
        String sql = "update tab_user set status = 'Y' where uid = ?";
        //2.执行sql
        template.update(sql,user.getUid());
    }

    @Override
    public void save(User user) {

        //1.定义sql
        String sql = "insert into tab_user(username,password,name,birthday,sex,telephone,email,status,code) values(?,?,?,?,?,?,?,?,?)";
        //2.执行sql
        template.update(sql,user.getUsername(),
                user.getPassword(),
                user.getName(),
                user.getBirthday(),
                user.getSex(),
                user.getTelephone(),
                user.getEmail(),
                user.getStatus(),
                user.getCode());
    }

    @Override
    public User findByUsernameAndPassword(String username,String password) {
        User user = null;
        try {
            //1.定义sql语句
            String sql = "select * from tab_user where username = BINARY ? and password =  BINARY ?";
            //2.执行sql语句
            user = template.queryForObject(sql,new BeanPropertyRowMapper<User>(User.class),username,password);
        } catch (Exception e) {

        }

        return user;
    }
}
