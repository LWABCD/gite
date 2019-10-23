package com.grbk.blog.pojo;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "t_blog")   //在数据库中生成的表的名字
public class Blog {

    @Id   //指定id为主键
    @GeneratedValue   //自动生成主键值
    private Long id;   //id
    private String title;   //标题

    @Basic(fetch =FetchType.LAZY)
    @Lob   //指定字段为大文本类型，只在初始化数据库时才有效，不过可以与@Basic一起使用让该字段在使用时才有效
    private String content;   //内容
    private String firstPicture;   //首图
    private String flag;   //标记
    private Integer views;   //浏览数
    private boolean appreciation;   //是否开启赞赏
    private boolean shareStatement;   //是否开启转载声明
    private boolean commentabled;   //是否开启评论
    private boolean published;   //是否已发布
    private boolean recommend;   //是否推荐
    @Temporal(TemporalType.TIMESTAMP)
    private Date createTime;   //创建时间
    @Temporal(TemporalType.TIMESTAMP)
    private Date updateTime;   //更新时间
    private String description;   //博客描述
    //表示该属性并非一个到数据库表的字段的映射,ORM框架将忽略该属性.
    // 如果一个属性并非数据库表的字段映射,就务必将其标示为@Transient,否则,ORM框架默认其注解为@Basic
    @Transient
    private String tagIds;   //该博客所属的所有标签的id,中间用,分隔

    @ManyToOne   //多对一，多篇博客对应一种类型
    private Type type;   //类型
    //多对多，一篇博客对应多个标签，一个标签对应多篇博客
    @ManyToMany(cascade = {CascadeType.PERSIST})   //级联新增，当新增一篇博客时它对应的标签也会保存到数据库中
    private List<Tag> tags=new ArrayList<>();   //标签
    @ManyToOne
    private User user;   //作者

    @OneToMany(mappedBy = "blog")   //指定Blog为被维护端，多的一端为维护端
    private List<Comment> comments=new ArrayList<>();   //博客所对应的评论

    public Blog() {
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getFirstPicture() {
        return firstPicture;
    }

    public void setFirstPicture(String firstPicture) {
        this.firstPicture = firstPicture;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public Integer getViews() {
        return views;
    }

    public void setViews(Integer views) {
        this.views = views;
    }

    public boolean isAppreciation() {
        return appreciation;
    }

    public void setAppreciation(boolean appreciation) {
        this.appreciation = appreciation;
    }

    public boolean isShareStatement() {
        return shareStatement;
    }

    public void setShareStatement(boolean shareStatement) {
        this.shareStatement = shareStatement;
    }

    public boolean isCommentabled() {
        return commentabled;
    }

    public void setCommentabled(boolean commentabled) {
        this.commentabled = commentabled;
    }

    public boolean isPublished() {
        return published;
    }

    public void setPublished(boolean published) {
        this.published = published;
    }

    public boolean isRecommend() {
        return recommend;
    }

    public void setRecommend(boolean recommend) {
        this.recommend = recommend;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public List<Tag> getTags() {
        return tags;
    }

    public void setTags(List<Tag> tags) {
        this.tags = tags;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTagIds() {
        return tagIds;
    }

    public void setTagIds(String tagIds) {
        this.tagIds = tagIds;
    }

    public void initTagIds(){
        this.tagIds=tagsToIds(this.getTags());
    }

    //把博客所属标签的id取出来转成字符串
    public String tagsToIds(List<Tag> tags){
        if(!tags.isEmpty()){
            StringBuffer ids=new StringBuffer();
            int size=tags.size();
            for(int i=0;i<size;i++){
                ids.append(tags.get(i).getId());
                if(i!=size-1){
                    ids.append(",");
                }
            }
            return ids.toString();
        }else{
            return tagIds;
        }
    }

    @Override
    public String toString() {
        return "Blog{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", firstPicture='" + firstPicture + '\'' +
                ", flag='" + flag + '\'' +
                ", views=" + views +
                ", appreciation=" + appreciation +
                ", shareStatement=" + shareStatement +
                ", commentabled=" + commentabled +
                ", published=" + published +
                ", recommend=" + recommend +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                ", description='" + description + '\'' +
                ", type=" + type +
                ", tags=" + tags +
                ", user=" + user +
                ", comments=" + comments +
                '}';
    }
}
