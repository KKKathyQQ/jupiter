package com.laioffer.onlineorder.dao;

import com.laioffer.onlineorder.entity.db.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository // 跟service差不多意思，spring会将其作为bean创建出来
public class LoginDao {

    @Autowired
    private SessionFactory sessionFactory;

    // Verify if the given user Id and password are correct. Returns the username when it passes
    public String verifyLogin(String userId, String password) {
        String name = "";

        try(Session session = sessionFactory.openSession()) { // 创建session
            // 找有没有这个user，找到的话hibernate帮序列化
            User user = session.get(User.class, userId); // user的class的类型；Class是一个特殊的类型
            if(user != null && user.getPassword().equals(password)) { // compare after hash
                name = user.getFirstName();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return name;
    }
}
