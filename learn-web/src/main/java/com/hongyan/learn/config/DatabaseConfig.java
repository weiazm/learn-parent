/*
 * Baijiahulian.com Inc.
 * Copyright (c) 2014-${year} All Rights Reserved.
 */
package com.hongyan.learn.config;

import com.jolbox.bonecp.BoneCPDataSource;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.mapper.MapperScannerConfigurer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.concurrent.TimeUnit;

/**
 * Created by weihongyan on 9/9/16.
 */
@Configuration
@EnableTransactionManagement
@ComponentScan(basePackages = {
        "com.hongyan.learn.dal.*",
        "com.hongyan.learn.sal.*",
        "com.hongyan.learn.biz.*",
        "com.baijia.tianxiao.sqlbuilder.*"
})
@PropertySource(value = {
        "classpath:jdbc-mysql.properties",
})
public class DatabaseConfig {

    @Bean(name = "h2DataSource", destroyMethod = "shutdown")
    public EmbeddedDatabase dataSource() {
        return new EmbeddedDatabaseBuilder()
                .addScript("classpath:database/hongyan-learn.sql")
                .setType(EmbeddedDatabaseType.H2)
                .build();
    }

    @Bean(name = "dataSource", destroyMethod = "close")
    public BoneCPDataSource boneCPDataSource(
            @Value("${jdbc.driverClass}") String driverClass,
            @Value("${jdbc.jdbcUrl}") String jdbcUrl,
            @Value("${jdbc.username}") String username,
            @Value("${jdbc.password}") String Password
    ) {
        BoneCPDataSource dataSource = new BoneCPDataSource();
        dataSource.setDriverClass(driverClass);
        dataSource.setJdbcUrl(jdbcUrl);
        dataSource.setUsername(username);
        dataSource.setPassword(Password);
        dataSource.setIdleConnectionTestPeriod(60L, TimeUnit.SECONDS);
        dataSource.setIdleMaxAge(60L, TimeUnit.SECONDS);
        dataSource.setMaxConnectionsPerPartition(50);
        dataSource.setMinConnectionsPerPartition(1);
        dataSource.setPartitionCount(3);
        dataSource.setAcquireIncrement(5);
        dataSource.setStatementsCacheSize(50);
        return dataSource;
    }

    @Bean(name = "transactionManager")
    public DataSourceTransactionManager dataSourceTransactionManager(DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }

    @Bean
    public MapperScannerConfigurer mapperScannerConfigurer() {
        MapperScannerConfigurer mapperScannerConfigurer = new MapperScannerConfigurer();
        mapperScannerConfigurer.setBasePackage("com.hongyan.learn.dal.mapper");
        return mapperScannerConfigurer;
    }

    @Bean
    public SqlSessionFactoryBean sqlSessionFactory(DataSource dataSource) {
        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        sqlSessionFactoryBean.setDataSource(dataSource);
        sqlSessionFactoryBean.setConfigLocation(new ClassPathResource("mybatis-config.xml"));
        return sqlSessionFactoryBean;
    }

}
