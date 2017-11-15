package cn.mccreefei.zhihu.magic;

import cn.mccreefei.zhihu.parse.ZhihuAnswerParser;
import cn.mccreefei.zhihu.parse.ZhihuArticleParser;
import cn.mccreefei.zhihu.parse.ZhihuUserParser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;

import java.util.List;
import java.util.ResourceBundle;

/**
 * @author MccreeFei
 * @create 2017-11-15 13:38
 */
@Slf4j
@Component
public class ZhihuPageProcessor implements PageProcessor {
    private ZhihuUserParser userParser;
    private ZhihuAnswerParser answerParser;
    private ZhihuArticleParser articleParser;
    private static final ResourceBundle resource = ResourceBundle.getBundle("zhihu");
    private Site site;

    public ZhihuPageProcessor(){
        int RETRY_TIMES = 0;
        int CRAWL_SLEEP_TIME = 0;
        try {
            RETRY_TIMES = Integer.parseInt(resource.getString("RETRY_TIMES"));
            CRAWL_SLEEP_TIME = Integer.parseInt(resource.getString("CRAWL_SLEEP_TIME"));
        }catch (Exception e){
            log.warn("get RETRY_TIMES or CRAWL_SLEEP_TIME from resource failed! use default value 3 for RETRY_TIME and 2000 for CRAWL_SLEEP_TIME");
            RETRY_TIMES = 3;
            CRAWL_SLEEP_TIME = 2000;
        }
        site = Site.me().setRetryTimes(RETRY_TIMES).setSleepTime(CRAWL_SLEEP_TIME).
                setUserAgent("Mozilla/5.0 (Windows NT 6.1; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/62.0.3202.89 Safari/537.36");

    }
    @Autowired
    public void setUserParser(ZhihuUserParser userParser) {
        this.userParser = userParser;
    }

    @Autowired
    public void setAnswerParser(ZhihuAnswerParser answerParser) {
        this.answerParser = answerParser;
    }

    @Autowired
    public void setArticleParser(ZhihuArticleParser articleParser) {
        this.articleParser = articleParser;
    }

    @Override
    public void process(Page page) {
        addTargetUrls(page);
        String url = page.getRequest().getUrl();
        if (url.endsWith("/following")) {
            userParser.parseUserInfo(page);
        }else if (url.endsWith("/answers/by_votes")){
            answerParser.parseAnswerInfo(page);
        }else if (url.endsWith("/posts/posts_by_votes")){
            articleParser.parseArticleInfo(page);
        }else {
            page.setSkip(true);
        }
    }

    protected void addTargetUrls(Page page){
        List<String> urlList = page.getHtml().xpath("//div[@id='Profile-following']//div[@class='List-item]//div[@class='ContentItem-head']//a[@class='UserLink-link]").links().all();
        if (urlList != null && urlList.size() > 0){
            for (String url : urlList){
                //page.addTargetRequest(url + "/following");
                page.addTargetRequest(url + "/answers/by_votes");
                //page.addTargetRequest(url + "/posts/posts_by_votes");
            }
        }
    }


    @Override
    public Site getSite() {
        return site;
    }

    public static void main(String[] args) {

    }
}
