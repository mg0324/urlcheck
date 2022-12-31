package com.mgang.util.db;

/**
 * @Author: mango
 * @Date: 2022/12/31 2:02 PM
 */
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Vector;
import org.apache.log4j.Logger;

public class MgDataSource {
    private Logger log = Logger.getLogger(MgDataSource.class);
    private String driverName;
    private String url;
    private String userName;
    private String password;
    private int autoIncrement = 1;
    private int poolSize = 5;
    private int currentPoolLength = 5;
    private int autoIncrementTime = 0;
    private int waitTimeOut = 2000;
    private Vector<Connection> pool = null;
    private static MgDataSource mgds = null;

    private MgDataSource() {
    }

    public static MgDataSource getInstance() {
        if (mgds == null) {
            mgds = new MgDataSource();
        }

        return mgds;
    }

    public void initConnectionPool() {
        this.pool = new Vector();

        try {
            Class.forName(this.driverName);
        } catch (ClassNotFoundException var7) {
            ClassNotFoundException e = var7;

            try {
                throw new Exception(e.getMessage() + "--加载数据库驱动出错");
            } catch (Exception var6) {
                var6.printStackTrace();
            }
        }

        for(int i = 0; i < this.poolSize; ++i) {
            try {
                Connection conn = DriverManager.getConnection(this.url, this.userName, this.password);
                this.pool.addElement(conn);
            } catch (SQLException var5) {
                SQLException e = var5;

                try {
                    throw new Exception(e.getMessage() + "--获取数据库连接出错");
                } catch (Exception var4) {
                    var4.printStackTrace();
                }
            }
        }

    }

    public void destory() {
        for(int i = 0; i < this.currentPoolLength; ++i) {
            Connection conn = (Connection)this.pool.get(i);
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException var4) {
                    var4.printStackTrace();
                }
            }
        }

        this.currentPoolLength = 0;
        this.info("关闭连接池");
    }

    public synchronized Connection getConnection() {
        Connection conn = null;
        if (this.currentPoolLength > 0) {
            conn = (Connection)this.pool.get(this.currentPoolLength - 1);
            --this.currentPoolLength;
        } else {
            try {
                this.wait((long)this.waitTimeOut);
                if (this.currentPoolLength > 0) {
                    conn = (Connection)this.pool.get(this.currentPoolLength - 1);
                    --this.currentPoolLength;
                } else {
                    this.autoIncrement();
                    conn = this.getConnection();
                }
            } catch (InterruptedException var3) {
                var3.printStackTrace();
            }
        }

        this.info("得到可用连接，当前可用连接数为" + this.currentPoolLength);
        return conn;
    }

    public synchronized void close(Connection conn) {
        this.pool.addElement(conn);
        ++this.currentPoolLength;
        this.info("归还连接到连接池,可用连接数为" + this.currentPoolLength);
    }

    private void autoIncrement() {
        for(int i = 0; i < this.autoIncrement; ++i) {
            try {
                Connection c = DriverManager.getConnection(this.url, this.userName, this.password);
                this.pool.addElement(c);
                ++this.currentPoolLength;
            } catch (SQLException var3) {
                var3.printStackTrace();
            }
        }

        ++this.autoIncrementTime;
        this.info("自动增长连接池一次，连接池大小为" + (this.poolSize + this.autoIncrementTime * this.autoIncrement));
    }

    public int getCurrentPoolSize() {
        return this.currentPoolLength;
    }

    public int getPoolTotalSize() {
        return this.poolSize + this.autoIncrementTime * this.autoIncrement;
    }

    public void desotry() {
        for(int i = 0; i < this.currentPoolLength; ++i) {
            try {
                ((Connection)this.pool.get(i)).close();
            } catch (SQLException var3) {
                var3.printStackTrace();
            }
        }

        this.currentPoolLength = 0;
        this.info("连接池关闭");
    }

    public void setDriverName(String driverName) {
        this.driverName = driverName;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setAutoIncrement(int autoIncrement) {
        this.autoIncrement = autoIncrement;
    }

    public void setPoolSize(int poolSize) {
        this.poolSize = poolSize;
    }

    public void setWaitTimeOut(int waitTimeOut) {
        this.waitTimeOut = waitTimeOut;
    }

    private void info(Object obj) {
        Object openLog = MgDataSourceFactory.getContant("openLog");
        if (openLog != null && openLog.toString().equals("true")) {
            this.log.info(obj);
        }

    }
}
