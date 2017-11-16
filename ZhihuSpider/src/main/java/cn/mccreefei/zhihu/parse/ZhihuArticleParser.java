package cn.mccreefei.zhihu.parse;

import cn.mccreefei.zhihu.exception.ParseException;
import cn.mccreefei.zhihu.model.ZhihuArticle;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.selector.Html;

import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

/**
 * @author MccreeFei
 * @create 2017-11-15 13:40
 */
@Slf4j
@Component
public class ZhihuArticleParser {
    private static int CRAWL_ARTICLE_AGREES;
    private static final ResourceBundle resource = ResourceBundle.getBundle("zhihu");

    public ZhihuArticleParser(){
        try {
            CRAWL_ARTICLE_AGREES = Integer.parseInt(resource.getString("CRAWL_ARTICLE_AGREES"));
        }catch (Exception e){
            log.warn("get CRAWL_ARTICLE_AGREES from resource failed, use default value 5000");
            CRAWL_ARTICLE_AGREES = 5000;
        }
    }

    public void parseArticleInfo(Page page){
        String url = page.getRequest().getUrl();
        String characterUrl = getCharacterUrl(url);

        try{
            List<String> itemList = page.getHtml().xpath("//div[@id='Profile-posts']//div[@class='List-item']").all();
            if (itemList != null && itemList.size() > 0){
                log.info("start parse article from url : {}", url);
                if (url.equals("https://www.zhihu.com/people/lisongwei/posts/posts_by_votes")){
                    System.out.println("1");
                }
                for (int i = 0; i < itemList.size(); i++) {
                    String item = itemList.get(i);
                    Html itemHtml = Html.create(item);
                    String agreeText = itemHtml.xpath("//div[@class='ContentItem-meta']//div[@class='ArticleItem-extraInfo']//button//text()").get();
                    Integer agrees = getAgrees(agreeText);
                    //小于赞数不再解析剩余项
                    if (agrees < CRAWL_ARTICLE_AGREES){
                        break;
                    }

                    String articleUrl = itemHtml.xpath("//h2[@class='ContentItem-title']").links().get();
                    String articleTitle = itemHtml.xpath("//h2[@class='ContentItem-title']//a/text()").get();

                    String commentsText = itemHtml.xpath("//div[@class='ContentItem-actions']/button[@class='Button ContentItem-action Button--plain Button--withIcon Button--withLabel']/text()").get();
                    Integer comments = getComments(commentsText);

                    ZhihuArticle article = new ZhihuArticle.ArticleBuilder().setCharacterUrl(characterUrl)
                            .setArticleUrl(articleUrl).setArticleTitle(articleTitle).setAgrees(agrees)
                            .setComments(comments).setCreateTime(new Date()).build();

                    page.putField("article", article);

                }
            }
        }catch (Exception e){
            log.error("crawl articles from url failed! url is : {}, and the error is : {}", url, e.getMessage(), e);
        }
    }

    private int getAgrees(String agreeText) throws NumberFormatException, IndexOutOfBoundsException, ParseException{
        if (agreeText == null) throw new ParseException("agreeText is null");
        int index = agreeText.indexOf(" 人赞了该文章");
        return Integer.parseInt(agreeText.substring(1, index));
    }

    private Integer getComments(String commentsText){
        Integer result = null;
        try {
            if (commentsText == null) throw new ParseException("commentsText is null");
            int index = commentsText.indexOf(" 条评论");
            result = Integer.valueOf(commentsText.substring(1, index));
        }catch (Exception e){
            log.warn("parse article comments failed!", e);
        }
        return result;
    }

    private String getCharacterUrl(String url){
        int startIndex = url.indexOf("people/") + 7;
        if (startIndex < 7){
            startIndex = url.indexOf("org/") + 4;
        }
        int endIndex = url.indexOf("/posts/");
        String result = null;
        try {
            result = url.substring(startIndex, endIndex);
        }catch (IndexOutOfBoundsException e){
            log.warn("解析特征url失败！url: {}", url);
        }
        return result;
    }
}
