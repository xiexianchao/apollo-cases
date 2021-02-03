package com.xiechao.config.ds;

import javax.sql.DataSource;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.util.concurrent.atomic.AtomicReference;
import java.util.logging.Logger;

public class DynamicDataSource implements DataSource {
    private final AtomicReference<DataSource> dataSourceAtomicReference;


    public DynamicDataSource(DataSource dataSource) {
        this.dataSourceAtomicReference = new AtomicReference<>(dataSource);
    }

    public DataSource setDataSource(DataSource dataSource) {
        return dataSourceAtomicReference.getAndSet(dataSource);
    }

    public DataSource getDataSource(){
        return dataSourceAtomicReference.get();
    }


    @Override
    public Connection getConnection() throws SQLException {
        return dataSourceAtomicReference.get().getConnection();
    }

    @Override
    public Connection getConnection(String username, String password) throws SQLException {
        return dataSourceAtomicReference.get().getConnection(username,password);
    }

    @Override
    public <T> T unwrap(Class<T> iface) throws SQLException {
        return dataSourceAtomicReference.get().unwrap(iface);
    }

    @Override
    public boolean isWrapperFor(Class<?> iface) throws SQLException {
        return dataSourceAtomicReference.get().isWrapperFor(iface);
    }

    @Override
    public PrintWriter getLogWriter() throws SQLException {
        return dataSourceAtomicReference.get().getLogWriter();
    }

    @Override
    public void setLogWriter(PrintWriter out) throws SQLException {
        dataSourceAtomicReference.get().setLogWriter(out);
    }

    @Override
    public void setLoginTimeout(int seconds) throws SQLException {
        dataSourceAtomicReference.get().setLoginTimeout(seconds);
    }

    @Override
    public int getLoginTimeout() throws SQLException {
        return dataSourceAtomicReference.get().getLoginTimeout();
    }

    @Override
    public Logger getParentLogger() throws SQLFeatureNotSupportedException {
        return dataSourceAtomicReference.get().getParentLogger();
    }
}
