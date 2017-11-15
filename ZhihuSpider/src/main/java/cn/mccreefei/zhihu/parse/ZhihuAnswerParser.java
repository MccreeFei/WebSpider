package cn.mccreefei.zhihu.parse;

import cn.mccreefei.zhihu.model.ZhihuAnswer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.selector.Html;

import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

/**
 * @author MccreeFei
 * @create 2017-11-15 13:29
 */
@Slf4j
@Component
public class ZhihuAnswerParser {
    private static final ResourceBundle resource = ResourceBundle.getBundle("zhihu");
    private static int CRAWL_ANSWER_AGREES;

    public ZhihuAnswerParser() {
        try {
            CRAWL_ANSWER_AGREES = Integer.parseInt(resource.getString("CRAWL_ANSWER_AGREES"));
        } catch (Exception e) {
            log.warn("get CRAWL_ANSWER_AGREES from resource failed! use default value 5000", e);
            CRAWL_ANSWER_AGREES = 5000;
        }
    }

    public void parseAnswerInfo(Page page) {
        String url = page.getRequest().getUrl();
        String characterUrl = getCharacterUrl(url);

        try {
            List<String> itemList = page.getHtml().xpath("//div[@id='Profile-answers']//div[@class='List-item']").all();
            if (itemList != null && itemList.size() > 0) {
                log.info("start parse answer from url : {}", url);
                for (int i = 0; i < itemList.size(); i++) {
                    String item = itemList.get(i);
                    Html itemHtml = Html.create(item);
                    String agreeText = itemHtml.xpath("//div[@class='ContentItem-meta']//div[@class='AnswerItem-extraInfo']//button//text()").get();
                    int agrees = getAgrees(agreeText);
                    //小于赞同数不再解析剩余项
                    if (agrees < CRAWL_ANSWER_AGREES) {
                        break;
                    }

                    //获取回答url与问题标题
                    String answerUrl = itemHtml.xpath("h2[@class='ContentItem-title']//a/@href").get();
                    String questionTitle = itemHtml.xpath("h2[@class='ContentItem-title']//a/text()").get();

                    //获取评论数
                    String commentsText = itemHtml.xpath("//div[@class='ContentItem-actions']/button/text()").get();
                    int comments = getComments(commentsText);

                    //获取questionId和answerId
                    Integer[] questionAndAnswerId = getQuestionAndAnswerId(answerUrl);
                    Integer questionId = questionAndAnswerId[0];
                    Integer answerId = questionAndAnswerId[1];

                    //拼装对象
                    ZhihuAnswer answer = new ZhihuAnswer.AnswerBuilder().setCharacterUrl(characterUrl)
                            .setAgrees(agrees).setAnswerUrl("https://www.zhihu.com" + answerUrl)
                            .setQuestionTitle(questionTitle).setQuestionId(questionId).setAnswerId(answerId)
                            .setComments(comments).setCreateTime(new Date()).build();

                    page.putField("answer", answer);

                }
            }
        } catch (Exception e) {
            log.error("crawl answers from url failed! url is : {}, and the error is : {}", url, e);
        }

    }

    private int getAgrees(String agreeText) throws NumberFormatException, IndexOutOfBoundsException{
        int index = agreeText.indexOf(" 人赞同了该回答");
        return Integer.parseInt(agreeText.substring(1, index));
    }

    private int getComments(String commentsText) throws NumberFormatException, IndexOutOfBoundsException{
        int index = commentsText.indexOf(" 条评论");
        return Integer.parseInt(commentsText.substring(1, index));
    }

    private String getCharacterUrl(String url){
        int startIndex = url.indexOf("people/") + 7;
        int endIndex = url.indexOf("/answers/");
        String result = null;
        try {
            result = url.substring(startIndex, endIndex);
        }catch (IndexOutOfBoundsException e){
            log.warn("解析特征url失败！url: {}", url);
        }
        return result;
    }

    private Integer[] getQuestionAndAnswerId(String answerUrl){
        Integer[] result = new Integer[2];
        int i = answerUrl.indexOf("/question/");
        int j = answerUrl.indexOf("/answer/");
        try {
            result[0] = Integer.valueOf(answerUrl.substring(i + 10, j));
            result[1] = Integer.valueOf(answerUrl.substring(j + 8));
        }catch (IndexOutOfBoundsException e){
            log.warn("parse question id and answer id error, url is {}", answerUrl);
        }
        return result;
    }
}
