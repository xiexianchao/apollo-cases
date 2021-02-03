package com.config.ds;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;

@Configuration
public class RefreshableDataSourceConfiguration {

  @Bean
  @Primary
  public DynamicDataSource dataSource(DataSourceManager dataSourceManager) {
    DataSource actualDataSource = dataSourceManager.createDataSource();
    return new DynamicDataSource(actualDataSource);
  }

  /**创建SessionFactory*/
  @Bean
  @Primary
    public SqlSessionFactory firstSqlSessionFactory(DataSource dataSource) throws Exception {
    SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
    bean.setDataSource(dataSource);
    //设置mapper配置文件
    bean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath:mapper/*.xml"));
    return bean.getObject();
  }

  /**创建事务管理器*/
  @Bean
  @Primary
  public DataSourceTransactionManager firstTransactionManger(DataSource dataSource){
    return new DataSourceTransactionManager(dataSource);
  }

  /**创建SqlSessionTemplate*/
  @Bean
  @Primary
  public SqlSessionTemplate firstSqlSessionTemplate(SqlSessionFactory sqlSessionFactory){
    return new SqlSessionTemplate(sqlSessionFactory);
  }
}
