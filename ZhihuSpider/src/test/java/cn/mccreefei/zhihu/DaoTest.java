package cn.mccreefei.zhihu;

import cn.mccreefei.zhihu.dao.ZhihuAnswerDao;
import cn.mccreefei.zhihu.dao.ZhihuArticleDao;
import cn.mccreefei.zhihu.dao.ZhihuUserDao;
import cn.mccreefei.zhihu.model.ZhihuAnswer;
import cn.mccreefei.zhihu.model.ZhihuArticle;
import cn.mccreefei.zhihu.model.ZhihuUser;
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
   @Before
    public void initContext(){
       ApplicationContext context = new ClassPathXmlApplicationContext("classpath:/spring/spring-*.xml");
       userDao = context.getBean(ZhihuUserDao.class);
       answerDao = context.getBean(ZhihuAnswerDao.class);
       articleDao = context.getBean(ZhihuArticleDao.class);
   }

   @Test
    public void testAddUser(){
       ZhihuUser user = new ZhihuUser.UserBuilder().setUserName("MccreeFei").setHeadUrl("www.mccreefei.cn")
               .setCharacterUrl("MccreeFei").setSimpleDescription("Carpe Diem").setAgrees(2000).setThanks(1500)
               .setCollects(1000).setFollowers(1000).setFollowees(200).setCreateTime(new Date()).build();

       assertEquals(userDao.addZhihuUser(user), 1);
   }

   @Test
    public void testAddAnswer(){
       ZhihuAnswer answer = new ZhihuAnswer.AnswerBuilder().setCharacterUrl("MccreeFei").setQuestionId(1000)
               .setQuestionTitle("how to be handsome?").setAnswerId(2000).setAnswerUrl("www.google.com")
               .setAgrees(3000).setComments(2000).setCreateTime(new Date()).build();

       assertEquals(answerDao.addZhihuAnswer(answer), 1);
   }

   @Test
    public void testAddArticle(){
       ZhihuArticle article = new ZhihuArticle.ArticleBuilder().setCharacterUrl("MccreeFei")
               .setArticleTitle("how to be rich?").setArticleUrl("www.google.com").setAgrees(2000)
               .setComments(1500).setCreateTime(new Date()).build();

       assertEquals(articleDao.addZhihuArticle(article), 1);
   }
}
