package cn.mccreefei.zhihu;

import org.junit.Before;
import org.junit.Test;
import us.codecraft.webmagic.selector.Html;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.ResourceBundle;

/**
 * @author MccreeFei
 * @create 2017-11-14 10:18
 */

public class PhantomJSTest {
    private static final ResourceBundle resource = ResourceBundle.getBundle("zhihu");
    private static String PHANTOMJS_PATH;
    private static String CRAWL_JS_FILE_PATH;

    @Before
    public void initContext() {
        try {
            PHANTOMJS_PATH = resource.getString("PHANTOMJS_PATH");
            CRAWL_JS_FILE_PATH = resource.getString("CRAWL_JS_FILE_PATH");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @Test
    public void testPhantomJs() {
        String url = "https://www.zhihu.com/people/zhou-ruo-yu-99-95/answers/by_votes";
        String page = null;
        try {
            Runtime runtime = Runtime.getRuntime();
            long time1 = System.currentTimeMillis();
            Process exec = runtime.exec(PHANTOMJS_PATH + " " + CRAWL_JS_FILE_PATH + " " + url);
            BufferedReader br = new BufferedReader(new InputStreamReader(exec.getInputStream()));
            StringBuilder sb = new StringBuilder();
            String line = null;
            while ((line = br.readLine()) != null) {
                sb.append(line + "\n");
            }
            page = sb.toString();
            long time2 = System.currentTimeMillis();
            Html html = Html.create(page);
            List<String> all = html.xpath("//div[@id='Profile-answers']//div[@class='List-item']//div[@class='ContentItem-meta']//div[@class='AnswerItem-extraInfo']//button//text()").all();
            long time3 = System.currentTimeMillis();
            System.out.println(all);
            System.out.println("js渲染时间: " + (time2 - time1));
            System.out.println("文档解析赞数时间: " + (time3 - time2));

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
