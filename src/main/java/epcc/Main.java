package epcc;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by xcoder on 8/2/16.
 */
public class Main {
    private static final Logger LOG = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) throws Exception {
        try {
            new NewsCrawler("crawler", true).run();
        } catch (Exception e) {
            LOG.error("crawl fail", e);
        }
    }
}
