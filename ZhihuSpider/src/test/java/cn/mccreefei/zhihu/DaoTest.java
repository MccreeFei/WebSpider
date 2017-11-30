package cn.mccreefei.zhihu;

import cn.mccreefei.zhihu.dao.*;
import cn.mccreefei.zhihu.model.*;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.Date;

import static org.junit.Assert.*;

/**
 * @author MccreeFei
 * @create 2017-11-15 10:14
 */
public class DaoTest {
    private ZhihuUserDao userDao;
    private ZhihuAnswerDao answerDao;
    private ZhihuArticleDao articleDao;
    private ZhihuArticleTextDao articleTextDao;
    private ZhihuAnswerTextDao answerTextDao;

    @Before
    public void initContext() {
        ApplicationContext context = new ClassPathXmlApplicationContext("classpath:/spring/spring-*.xml");
        userDao = context.getBean(ZhihuUserDao.class);
        answerDao = context.getBean(ZhihuAnswerDao.class);
        articleDao = context.getBean(ZhihuArticleDao.class);
        articleTextDao = context.getBean(ZhihuArticleTextDao.class);
        answerTextDao = context.getBean(ZhihuAnswerTextDao.class);
    }

    @Test
    public void testAddUser() {
        ZhihuUser user = new ZhihuUser.UserBuilder().setUserName("MccreeFei").setHeadUrl("www.mccreefei.cn")
                .setCharacterUrl("MccreeFei").setSimpleDescription("Carpe Diem").setAgrees(2000).setThanks(1500)
                .setCollects(1000).setFollowers(1000).setFollowees(200).setCreateTime(new Date()).build();

        assertEquals(userDao.addZhihuUser(user), 1);
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        user.setModifyTime(new Date());
        user.setAgrees(3000);
        assertEquals(userDao.addZhihuUser(user), 2);
    }

    @Test
    public void testAddAnswer() {
        ZhihuAnswer answer = new ZhihuAnswer.AnswerBuilder().setCharacterUrl("MccreeFei").setQuestionId(1000)
                .setQuestionTitle("how to be handsome?").setAnswerId(2000).setAnswerUrl("www.google.com")
                .setAgrees(3000).setComments(2000).setCreateTime(new Date()).build();

        assertEquals(answerDao.addZhihuAnswer(answer), 1);
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        answer.setModifyTime(new Date());
        answer.setComments(1500);
        answer.setAgrees(2000);
        assertEquals(answerDao.addZhihuAnswer(answer), 2);

        answer.setQuestionId(1500);
        assertEquals(answerDao.addZhihuAnswer(answer), 1);
    }

    @Test
    public void testAddArticle() {
        ZhihuArticle article = new ZhihuArticle.ArticleBuilder().setCharacterUrl("MccreeFei").setArticleId(1000)
                .setArticleTitle("how to be rich?").setArticleUrl("www.google.com").setAgrees(2000)
                .setComments(1500).setCreateTime(new Date()).build();
    }

    @Test
    public void testAddArticleText(){
        ZhihuArticleText articleText = new ZhihuArticleText.ArticleTextBuilder().setArticleId(123).setContent("1")
                .setCreateTime(new Date()).setModifyTime(new Date()).build();

        assertEquals(articleTextDao.addZhihuArticleTextInfo(articleText), 1);

        articleText.setContent("23");
        assertEquals(articleTextDao.addZhihuArticleTextInfo(articleText), 2);
    }

    @Test
    public void testAddAnswerText(){
        ZhihuAnswerText answerText = new ZhihuAnswerText.AnswerTextBuilder().setQuestionId(1).setAnswerId(2)
                .setContent("123").setCreateTime(new Date()).setModifyTime(new Date()).build();

        assertEquals(answerTextDao.addZhihuAnswerTextInfo(answerText), 1);

        answerText.setContent("456");
        assertEquals(answerTextDao.addZhihuAnswerTextInfo(answerText), 2);
    }


}
