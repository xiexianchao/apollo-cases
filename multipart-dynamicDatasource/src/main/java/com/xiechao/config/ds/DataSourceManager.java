package com.xiechao.config.ds;

import com.alibaba.druid.pool.DruidDataSource;
import com.xiechao.config.ds.first.FirstDataSourceConfig;
import com.xiechao.config.ds.second.SecondDataSourceConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.bind.Bindable;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

@Component
public class DataSourceManager {

  private static final Logger logger = LoggerFactory.getLogger(DataSourceManager.class);

  @Autowired
  private CustomizedConfigurationPropertiesBinder binder;

  @Autowired
  private FirstDataSourceConfig firstDataSourceConfig;

  @Autowired
  private SecondDataSourceConfig secondDataSourceConfig;

  public DataSource createDataSource(String prefix) {
    DataSource dataSource = null;
    if ("spring.datasource.first".equals(prefix)){
     dataSource =  firstDataSourceConfig.getFirstDataSource();
    }else if ("spring.datasource.second".equals(prefix)){
      dataSource = secondDataSourceConfig.getSecondDataSource();
    }
    Bindable<?> target = Bindable.of(DruidDataSource.class).withExistingValue((DruidDataSource) dataSource);
    this.binder.bind(prefix, target);
    return dataSource;
  }

  public DataSource createAndTestDataSource(String prefix) throws SQLException {
    DataSource newDataSource = createDataSource(prefix);
    try {
      testConnection(newDataSource);
    } catch (SQLException ex) {
      logger.error("Testing connection for data source failed: ",  ex);
     // newDataSource.close();
      throw ex;
    }
    return newDataSource;
  }

  private void testConnection(DataSource dataSource) throws SQLException {
    //borrow a connection
    Connection connection = dataSource.getConnection();
    //return the connection
    connection.close();
  }

}
