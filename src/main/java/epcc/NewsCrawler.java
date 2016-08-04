package epcc;

import cn.edu.hfut.dmic.contentextractor.ContentExtractor;
import cn.edu.hfut.dmic.contentextractor.News;
import cn.edu.hfut.dmic.webcollector.model.CrawlDatums;
import cn.edu.hfut.dmic.webcollector.model.Page;
import cn.edu.hfut.dmic.webcollector.plugin.berkeley.BreadthCrawler;

import org.slf4j.LoggerFactory;
import org.slf4j.Logger;


/**
 * Created by xcoder on 8/1/16.
 */
public class NewsCrawler extends BreadthCrawler {
    private static final Logger LOG = LoggerFactory.getLogger(NewsCrawler.class);

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

        addRegex("http://news\\..*/.*");

        addRegex("-.*\\.(jpg|png|gif).*");
        addRegex("-.*#.*");

        // execute interval
        setExecuteInterval(100);
    }


    /**
     * 实现接口函数，每个页面被访问前调用这个函数
     */
    public void visit(Page page, CrawlDatums next) {
        System.out.println(page.getUrl());
        try {
            News news = ContentExtractor.getNewsByDoc(page.doc());
            if (newsNotNull(news)) {
                int n = NewsDAO.insert(news);
            }
        } catch (Exception e) {
            LOG.info("visit fail", e);
        }
    }

    private boolean newsNotNull(News news) {
        return (news.getUrl() != null) && (news.getContent() != null) && (news.getTitle() != null);
    }


    /**
     * Crawler 启动函数
     */
    public void run() throws Exception {
        int threads = 20, topN = 100000, depth = 5;
        setThreads(threads);
        setTopN(topN);
        start(depth);
    }

}
