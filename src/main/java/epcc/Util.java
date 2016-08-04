package epcc;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by xcoder on 8/4/16.
 */
public class Util {
    private static final Logger LOG = LoggerFactory.getLogger(Util.class);

    /**
     * 从新闻url中提取出时间串
     *
     * @param url ： 新闻的url
     */
    public static String getDateFromUrl(String url) {
        String date;
        String pattern = "(/?[1-2][0-9]{3}[^a-zA-Z]?[0-1][0-9][^a-zA-Z]?[0-3][0-9]/?)";

        Pattern p = Pattern.compile(pattern);
        Matcher m = p.matcher(url);
        if (m.find()) {
            date = m.group().replaceAll("[^0-9]", "");

        } else {
            date = null;
        }
        return date;
    }

    /**
     * 将数字字符串表示的日期格式化为Date对象
     *
     * @param url ： 可能包含日期的url
     * @return 格式化的日期对象
     */
    public static Date parseDateFromUrl(String url) {
        String srcDate = getDateFromUrl(url);
        if (srcDate == null || srcDate.length() != 8) {
            return null;
        }
        String yy = srcDate.substring(0, 4);
        String mm = srcDate.substring(4, 6);
        String dd = srcDate.substring(6, 8);
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        try {
            Date date = formatter.parse(dd + "/" + mm + "/" + yy);
            return date;
        } catch (Exception e) {
            return null;
        }
    }
}
