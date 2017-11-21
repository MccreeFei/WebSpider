package cn.mccreefei.zhihu;

import cn.mccreefei.zhihu.magic.SimpleSeleniumDownloader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Component;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.downloader.PhantomJSDownloader;
import us.codecraft.webmagic.pipeline.Pipeline;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.scheduler.FileCacheQueueScheduler;

/**
 * @author MccreeFei
 * @create 2017-11-15 14:06
 */
@Component
public class ZhihuSpider {
    private PageProcessor pageProcessor;
    private SimpleSeleniumDownloader simpleSeleniumDownloader;
    private Pipeline pipeline;

    @Autowired
    public void setPageProcessor(PageProcessor pageProcessor) {
        this.pageProcessor = pageProcessor;
    }

    @Autowired
    public void setSimpleSeleniumDownloader(SimpleSeleniumDownloader simpleSeleniumDownloader) {
        this.simpleSeleniumDownloader = simpleSeleniumDownloader;
    }


    @Autowired
    public void setPipeline(Pipeline pipeline) {
        this.pipeline = pipeline;
    }

    public void crawl(int threadNum, String... baseUrl){
        Spider.create(pageProcessor).addPipeline(pipeline).addUrl(baseUrl)
                .setScheduler(new FileCacheQueueScheduler("E:\\webmagic"))
                .setDownloader(simpleSeleniumDownloader)
                .thread(threadNum).run();
    }

    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext("classpath:/spring/spring-dao.xml");
        ZhihuSpider zhihuSpider = context.getBean(ZhihuSpider.class);
        zhihuSpider.crawl(3, "https://www.zhihu.com/people/zhou-ruo-yu-99-95/following",
                "https://www.zhihu.com/people/zhou-ruo-yu-99-95/answers/by_votes",
                "https://www.zhihu.com/people/zhou-ruo-yu-99-95/posts/posts_by_votes");
    }
}
