import cn.edu.hfut.dmic.contentextractor.ContentExtractor;
import cn.edu.hfut.dmic.contentextractor.News;
import cn.edu.hfut.dmic.webcollector.model.CrawlDatums;
import cn.edu.hfut.dmic.webcollector.model.Page;
import cn.edu.hfut.dmic.webcollector.plugin.berkeley.BreadthCrawler;
import org.jsoup.nodes.Document;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.regex.Pattern;

/**
 * Created by xcoder on 8/1/16.
 */
public class NewsCrawler extends BreadthCrawler {

    /**
     * @param crawlPath crawlPath is the path of the directory which maintains
     *                  information of this crawler
     * @param autoParse if autoParse is true,BreadthCrawler will auto extract
     *                  links which match regex rules from pag
     */
    public NewsCrawler(String crawlPath, boolean autoParse) throws Exception {
        super(crawlPath, autoParse);
        addSeed("http://news.ifeng.com/");
        addSeed("http://news.qq.com/");
        addSeed("http://news.sohu.com/");
        addSeed("http://news.sina.com.cn/");
        addRegex("http://news.[a-zA-Z]*/.*");
        addRegex("-.*\\.(jpg|png|gif).*");
        addRegex("-.*#.*");
    }


    /**
     * 实现接口函数，每个页面被访问前调用这个函数
     */
    public void visit(Page page, CrawlDatums next) {
        try {
            News news = ContentExtractor.getNewsByDoc(page.doc());
            int n = NewsDAO.insert(news);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * Crawler 启动函数
     */
    public void run() throws Exception {
        int threads = 20, topN = 200000, depth = 10;
        setThreads(threads);
        setTopN(topN);
        start(depth);
    }

}
