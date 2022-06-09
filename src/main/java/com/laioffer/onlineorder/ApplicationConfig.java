package com.laioffer.onlineorder;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import javax.sql.DataSource;
import java.util.Properties;

@Configuration // spring will build the beans below when start up
@EnableWebMvc
public class ApplicationConfig {
    @Bean(name = "sessionFactory")
    public LocalSessionFactoryBean sessionFactory() {
        LocalSessionFactoryBean sessionFactory = new LocalSessionFactoryBean();
        sessionFactory.setDataSource(dataSource());
        // make sure you add your own package name if your class is not under com.laioffer.onlineorder.entity.db
        sessionFactory.setPackagesToScan("com.laioffer.onlineorder.entity.db");
        sessionFactory.setHibernateProperties(hibernateProperties());
        return sessionFactory;
    }

    @Bean(name = "dataSource")
    public DataSource dataSource() {
        String RDS_ENDPOINT = "database-1.cr3b5gewho9s.us-east-2.rds.amazonaws.com";
        String USERNAME = "admin";
        String PASSWORD = "G`#3:3:cJNSKD.M";
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
        dataSource.setUrl("jdbc:mysql://" + RDS_ENDPOINT + ":3306/twitch?createDatabaseIfNotExist=true&serverTimezone=UTC");
        dataSource.setUsername(USERNAME);
        dataSource.setPassword(PASSWORD);

        return dataSource;
    }

    private final Properties hibernateProperties() {
        Properties hibernateProperties = new Properties();
        hibernateProperties.setProperty("hibernate.hbm2ddl.auto", "update"); // 有entity的annotation的内容有修改的都同步到数据里了
        hibernateProperties.setProperty("hibernate.dialect", "org.hibernate.dialect.MySQL5InnoDBDialect"); // 用sql语句操作
        hibernateProperties.setProperty("hibernate.show_sql", "true"); // 有数据操作就把sql语句打印在log里
        return hibernateProperties;
    }
}
