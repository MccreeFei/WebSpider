package cn.mccreefei.zhihu.model;

import lombok.Data;

import java.util.Date;

/**
 * @author MccreeFei
 * @create 2017-11-10 10:24
 */
@Data
public class ZhihuArticle {
    private Integer id;
    private String characterUrl;
    private String articleUrl;
    private String articleTitle;
    private Integer agrees;
    private Integer comments;
    private Date createTime;

    public static class ArticleBuilder {
        private String characterUrl;
        private String articleUrl;
        private String articleTitle;
        private Integer agrees;
        private Integer comments;
        private Date createTime;

        public ArticleBuilder setCharacterUrl(String characterUrl) {
            this.characterUrl = characterUrl;
            return this;
        }

        public ArticleBuilder setArticleUrl(String articleUrl) {
            this.articleUrl = articleUrl;
            return this;
        }

        public ArticleBuilder setArticleTitle(String articleTitle) {
            this.articleTitle = articleTitle;
            return this;
        }

        public ArticleBuilder setAgrees(Integer agrees) {
            this.agrees = agrees;
            return this;
        }

        public ArticleBuilder setComments(Integer comments) {
            this.comments = comments;
            return this;
        }

        public ArticleBuilder setCreateTime(Date createTime) {
            this.createTime = createTime;
            return this;
        }

        public ZhihuArticle build(){
            ZhihuArticle article = new ZhihuArticle();
            article.setCharacterUrl(characterUrl);
            article.setArticleUrl(articleUrl);
            article.setArticleTitle(articleTitle);
            article.setAgrees(agrees);
            article.setComments(comments);
            article.setCreateTime(createTime);
            return article;
        }
    }
}