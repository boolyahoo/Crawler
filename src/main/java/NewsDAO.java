import cn.edu.hfut.dmic.contentextractor.News;
import org.apache.commons.dbcp.BasicDataSource;
import org.apache.commons.dbcp.BasicDataSourceFactory;

import java.sql.*;
import java.util.Properties;

/**
 * Created by xcoder on 8/2/16.
 */
public class NewsDAO {
    private static BasicDataSource dataSrc = null;


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
            p.setProperty("maxActive", "100");
            p.setProperty("maxIdle", "10");
            p.setProperty("maxWait", "1000");
            p.setProperty("removeAbandoned", "false");
            p.setProperty("removeAbandonedTimeout", "120");
            p.setProperty("testOnBorrow", "true");
            p.setProperty("logAbandoned", "true");
            dataSrc = (BasicDataSource) BasicDataSourceFactory.createDataSource(p);
        } catch (Exception e) {
            e.printStackTrace();
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
        ResultSet rs = null;
        try {
            conn = getConnection();
            String query = " select id from news where url = ? ";
            ps = conn.prepareStatement(query);
            ps.setString(1, news.getUrl());
            rs = ps.executeQuery();
            // url 不存在执行插入
            if (!rs.next()) {
                // insert
                String insert = " insert into news(url, title, content, time_stamp) values(?, ?, ?, ?) ";
                ps = conn.prepareStatement(insert);
                ps.setString(1, news.getUrl());
                ps.setString(2, news.getTitle());
                ps.setString(3, news.getContent());
                ps.setDate(4, new Date(System.currentTimeMillis()));
                // close all
                rs.close();
                ps.close();
                conn.close();
                return ps.executeUpdate();
            } else {
                return 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }
}
