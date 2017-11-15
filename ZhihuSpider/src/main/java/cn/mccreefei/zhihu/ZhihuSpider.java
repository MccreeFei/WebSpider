package cn.mccreefei.zhihu;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Component;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.downloader.Downloader;
import us.codecraft.webmagic.pipeline.Pipeline;
import us.codecraft.webmagic.processor.PageProcessor;

/**
 * @author MccreeFei
 * @create 2017-11-15 14:06
 */
@Component
public class ZhihuSpider {
    private PageProcessor pageProcessor;
    private Downloader downloader;
    private Pipeline pipeline;

    @Autowired
    public void setPageProcessor(PageProcessor pageProcessor) {
        this.pageProcessor = pageProcessor;
    }

    @Autowired
    public void setDownloader(Downloader downloader) {
        this.downloader = downloader;
    }

    @Autowired
    public void setPipeline(Pipeline pipeline) {
        this.pipeline = pipeline;
    }

    public void crawl(String baseUrl, int threadNum){
        Spider.create(pageProcessor).addPipeline(pipeline).setDownloader(downloader).addUrl(baseUrl)
                .thread(threadNum).run();
    }

    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext("classpath:/spring/spring-dao.xml");
        ZhihuSpider zhihuSpider = context.getBean(ZhihuSpider.class);
        zhihuSpider.crawl("https://www.zhihu.com/people/zhou-ruo-yu-99-95/following", 1);
    }
}
