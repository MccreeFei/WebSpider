package cn.mccreefei.zhihu.magic;

import cn.mccreefei.zhihu.dao.ZhihuAnswerDao;
import cn.mccreefei.zhihu.dao.ZhihuArticleDao;
import cn.mccreefei.zhihu.dao.ZhihuUserDao;
import cn.mccreefei.zhihu.model.ZhihuAnswer;
import cn.mccreefei.zhihu.model.ZhihuArticle;
import cn.mccreefei.zhihu.model.ZhihuUser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.Pipeline;

/**
 * @author MccreeFei
 * @create 2017-11-15 13:22
 */
@Slf4j
@Component
public class ZhihuPipeline implements Pipeline {
    private ZhihuUserDao userDao;
    private ZhihuArticleDao articleDao;
    private ZhihuAnswerDao answerDao;

    @Autowired
    public void setUserDao(ZhihuUserDao userDao) {
        this.userDao = userDao;
    }

    @Autowired
    public void setArticleDao(ZhihuArticleDao articleDao) {
        this.articleDao = articleDao;
    }

    @Autowired
    public void setAnswerDao(ZhihuAnswerDao answerDao) {
        this.answerDao = answerDao;
    }

    @Override
    public void process(ResultItems resultItems, Task task) {
        Object user = resultItems.getAll().get("user");
        Object article = resultItems.getAll().get("article");
        Object answer = resultItems.getAll().get("answer");

        if (user != null) {
            int i = userDao.addZhihuUser((ZhihuUser) user);
            if (i == 1) {
                log.info("add one user record success : {}", user);
            } else if (i == 2) {
                log.info("update one user record success : {}", user);
            }
        }
        if (article != null) {
            int i = articleDao.addZhihuArticle((ZhihuArticle) article);
            if (i == 1) {
                log.info("add one article record success : {}", article);
            } else if (i == 2) {
                log.info("update one article record success : {}", article);
            }
        }
        if (answer != null) {
            int i = answerDao.addZhihuAnswer((ZhihuAnswer) answer);
            if (i == 1) {
                log.info("add one answer record success : {}", answer);
            } else if (i == 2) {
                log.info("update one answer record success : {}", answer);
            }
        }
    }
}
