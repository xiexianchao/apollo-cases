package com.config.ds;

import com.alibaba.druid.pool.DruidDataSource;
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
    private DataSourceProperties dataSourceProperties;


    public DruidDataSource createDataSource() {
        DruidDataSource dataSource = dataSourceProperties.initializeDataSourceBuilder().type(DruidDataSource.class).build();

        Bindable<?> target = Bindable.of(DruidDataSource.class).withExistingValue(dataSource);
        this.binder.bind("spring.datasource", target);
        return dataSource;
    }

    public DruidDataSource createAndTestDataSource() throws SQLException {
        DruidDataSource newDataSource = createDataSource();
        try {
            testConnection(newDataSource);
        } catch (SQLException ex) {
            logger.error("Testing connection for data source failed: {}", newDataSource.getUrl(), ex);
            newDataSource.close();
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
