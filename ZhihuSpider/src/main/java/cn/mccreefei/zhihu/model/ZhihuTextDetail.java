package cn.mccreefei.zhihu.model;

import lombok.Data;

import java.util.Date;

/**
 * @author MccreeFei
 * @create 2017-11-30 9:45
 */
@Data
public class ZhihuTextDetail {
    private Integer id;
    private String characterUrl;
    private String url;
    private Integer textType;
    private String content;
    private Date createTime;
    private Date modifyTime;

    public static class TextDetailBuilder {
        private String characterUrl;
        private String url;
        private Integer textType;
        private String content;
        private Date createTime;
        private Date modifyTime;

        public TextDetailBuilder setCharacterUrl(String characterUrl) {
            this.characterUrl = characterUrl;
            return this;
        }

        public TextDetailBuilder setUrl(String url) {
            this.url = url;
            return this;
        }

        public TextDetailBuilder setTextType(Integer textType) {
            this.textType = textType;
            return this;
        }

        public TextDetailBuilder setContent(String content) {
            this.content = content;
            return this;
        }

        public TextDetailBuilder setCreateTime(Date createTime) {
            this.createTime = createTime;
            return this;
        }

        public TextDetailBuilder setModifyTime(Date modifyTime) {
            this.modifyTime = modifyTime;
            return this;
        }

        public ZhihuTextDetail build() {
            ZhihuTextDetail textDetail = new ZhihuTextDetail();
            textDetail.setCharacterUrl(characterUrl);
            textDetail.setUrl(url);
            textDetail.setTextType(textType);
            textDetail.setContent(content);
            textDetail.setCreateTime(createTime);
            textDetail.setModifyTime(modifyTime);
            return textDetail;
        }
    }


}
