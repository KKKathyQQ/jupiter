package com.laioffer.onlineorder.dao;

import com.laioffer.onlineorder.entity.db.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.PersistenceException;

@Repository
public class RegisterDao {

    @Autowired
    private SessionFactory sessionFactory;

    public boolean register(User user) {
        Session session = null; // 为了更新中途如果出错了可以roll back

        try {
            session = sessionFactory.openSession();
            session.beginTransaction(); // lock record; 保证一致性；完成或没开始，不存在进行了一半；关系型数据库的特点
            session.save(user);
            session.getTransaction().commit();
        } catch (PersistenceException | IllegalStateException ex) {
            // if hibernate throws this exception, it means the user already be register
            ex.printStackTrace();
            session.getTransaction().rollback();
            return false;
        } finally {
            if (session != null) session.close();
        }
        return true;
    }
}

