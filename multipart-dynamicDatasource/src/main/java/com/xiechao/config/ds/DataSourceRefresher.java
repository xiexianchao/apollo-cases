package com.xiechao.config.ds;

import com.ctrip.framework.apollo.model.ConfigChangeEvent;
import com.ctrip.framework.apollo.spring.annotation.ApolloConfigChangeListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cloud.context.environment.EnvironmentChangeEvent;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.util.Set;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Component
public class DataSourceRefresher implements ApplicationContextAware {

    private static final Logger logger = LoggerFactory.getLogger(DataSourceRefresher.class);
    private ScheduledExecutorService scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();
/*  @Autowired
  private DynamicDataSource dynamicDataSource;*/

    @Autowired
    private DataSourceManager dataSourceManager;

    @Autowired
    private ApplicationContext applicationContext;

    @Autowired
    @Qualifier("firstDS")
    private DynamicDataSource dataSource1;

    @Autowired
    @Qualifier("secondDS")
    private DynamicDataSource dataSource2;


    public synchronized void refreshDataSource(Set<String> changedKeys, String prefix) {
        try {
            logger.info("Refreshing data source");

            this.applicationContext.publishEvent(new EnvironmentChangeEvent(changedKeys));

            DataSource newDataSource = dataSourceManager.createAndTestDataSource(prefix);
            DataSource oldDataSource = null;
            if ("spring.datasource.first".equals(prefix)) {
                oldDataSource = dataSource1.setDataSource(newDataSource);
            } else if ("spring.datasource.second".equals(prefix)) {
                oldDataSource = dataSource2.setDataSource(newDataSource);
            }
            asyncTerminate(oldDataSource);
            logger.info("Finished refreshing data source");
        } catch (Throwable ex) {
            logger.error("Refreshing data source failed", ex);
        }
    }

    private void asyncTerminate(DataSource dataSource) {
        DataSourceTerminationTask task = new DataSourceTerminationTask(dataSource, scheduledExecutorService);
        //start now
        scheduledExecutorService.schedule(task, 0, TimeUnit.MILLISECONDS);
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }


    @ApolloConfigChangeListener(value = {"multipart-dynamicDataSource"}, interestedKeyPrefixes = "spring.datasource.first.")
    public void onChange1(ConfigChangeEvent changeEvent) {
        refreshDataSource(changeEvent.changedKeys(), "spring.datasource.first");
    }

    @ApolloConfigChangeListener(value = {"multipart-dynamicDataSource"}, interestedKeyPrefixes = "spring.datasource.second.")
    public void onChange2(ConfigChangeEvent changeEvent) {
        refreshDataSource(changeEvent.changedKeys(), "spring.datasource.second");
    }
}
