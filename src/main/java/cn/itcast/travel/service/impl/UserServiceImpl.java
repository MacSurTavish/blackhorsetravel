package cn.itcast.travel.service.impl;

import cn.itcast.travel.dao.UserDao;
import cn.itcast.travel.dao.impl.UserDaoImpl;
import cn.itcast.travel.domain.User;
import cn.itcast.travel.service.UserService;
import cn.itcast.travel.util.MailUtils;
import cn.itcast.travel.util.UuidUtil;

//Service层（业务层）的目的是完成相应的业务，以及与DAO层交互
public class UserServiceImpl implements UserService {

    //在Service层创建DAO层的对象
    private UserDao userDao = new UserDaoImpl();
    /**
     * 注册用户
     * @param user
     * @return
     */
    @Override
    public boolean regist(User user) {


        //1.根据用户名查询用户对象是否存在
        User username = userDao.findByUsername(user.getUsername());
        if (username != null) {
            //用户名存在，注册失败
            return false;
        }
        //2.保存用户信息
        //2.1设置激活码，唯一字符
        user.setCode(UuidUtil.getUuid());
        //2.2设置激活状态
        user.setStatus("N");

        userDao.save(user);

        //激活邮件发送，邮件正文的编写
        //地址默认为本机端口
        String content = "<a href='http://localhost/travel/user/active?code=" + user.getCode() + "'>点击激活黑马旅游网</a>";
        MailUtils.sendMail(user.getEmail(),content,"邮件激活");
        
        return true;
    }

    /**
     * 激活用户
     * @param code
     * @return
     */
    @Override
    public boolean active(String code) {
        //1.根据激活码查询用户对象
        User user = userDao.findByCode(code);
        if (user != null) {
            //2.调用dao的修改激活状态的方法
            userDao.updateStatus(user);
            return true;
        }else
            return false;
    }

    @Override
    public User login(User user) {
        //1.通过username查询对象是否存在
        return userDao.findByUsernameAndPassword(user.getUsername(),user.getPassword());
    }
}
