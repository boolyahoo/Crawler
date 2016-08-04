package epcc;

import cn.edu.hfut.dmic.contentextractor.News;
import org.apache.commons.dbcp.BasicDataSource;
import org.apache.commons.dbcp.BasicDataSourceFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.Properties;

/**
 * Created by xcoder on 8/2/16.
 */
public class NewsDAO {
    private static BasicDataSource dataSrc = null;
    private static final Logger LOG = LoggerFactory.getLogger(NewsDAO.class);

    private static void init() {
        if (dataSrc != null) {
            try {
                dataSrc.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
            dataSrc = null;
        }
        try {
            Properties p = new Properties();
            p.setProperty("driverClassName", "com.mysql.jdbc.Driver");
            p.setProperty("url", "jdbc:MySQL://localhost:3306/epcc_nlp?useUnicode=true&characterEncoding=UTF-8");
            p.setProperty("password", "xgqadmin");
            p.setProperty("username", "root");
            p.setProperty("maxActive", "1000");
            p.setProperty("maxIdle", "1000");
            p.setProperty("maxWait", "2000");
            p.setProperty("initialSize", "500");
            p.setProperty("removeAbandoned", "false");
            p.setProperty("removeAbandonedTimeout", "500");
            p.setProperty("testOnBorrow", "true");
            p.setProperty("logAbandoned", "true");
            dataSrc = (BasicDataSource) BasicDataSourceFactory.createDataSource(p);
        } catch (Exception e) {
            LOG.error("DAO object init fail", e);
            System.exit(0);
        }
    }


    private static synchronized Connection getConnection() throws SQLException {
        if (dataSrc == null) {
            init();
        }
        Connection conn = null;
        if (dataSrc != null) {
            conn = dataSrc.getConnection();
        }
        return conn;
    }


    public static int insert(News news) {
        Connection conn = null;
        PreparedStatement ps = null;
        int affectRow = 0;
        try {
            conn = getConnection();
            // insert
            String insert = " insert into news(url, title, content, time_stamp) values(?, ?, ?, ?) ";
            ps = conn.prepareStatement(insert);
            ps.setString(1, news.getUrl());
            ps.setString(2, news.getTitle());
            ps.setString(3, news.getContent());
            ps.setDate(4, new Date(System.currentTimeMillis()));
            affectRow = ps.executeUpdate();
            ps.close();
            conn.close();
        } catch (SQLException e) {
            LOG.warn("insert news fail", e);
        }
        return affectRow;
    }
}
