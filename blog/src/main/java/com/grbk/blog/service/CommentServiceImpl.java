package com.grbk.blog.service;

import com.grbk.blog.dao.BlogRepository;
import com.grbk.blog.dao.CommentRepository;
import com.grbk.blog.pojo.Comment;
import com.grbk.blog.pojo.User;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 评论
 */

@Service
public class CommentServiceImpl implements CommentService{

    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private BlogRepository blogRepository;

    @Override
    public List<Comment> listCommentByBlogId(Long blogId) {
        Sort sort=new Sort("createTime");   //根据创建时间正序排列
        //拿到所有的顶级评论节点
        List<Comment> comments=commentRepository.findByBlogIdAndParentCommentNull(blogId, sort);
        return eachComment(comments);
    }

    /**
     * 循环每个顶级的评论节点
     * @param comments
     * @return
     */
    public List<Comment> eachComment(List<Comment> comments){
        List<Comment> tempComments=new ArrayList<>();
        for(Comment comment:comments){
            Comment c = new Comment();
            BeanUtils.copyProperties(comment,c );
            tempComments.add(c);
        }
        //把所有顶级评论的子评论合并到第一级子评论中
        combineChildren(tempComments);
        return tempComments;
    }

    /**
     * 把所有顶级评论的子评论合并到第一级子评论中
     * @param comments
     */
    public void combineChildren(List<Comment> comments){
        for(Comment comment:comments){
            List<Comment> replyComment=comment.getReplyComments();
            for(Comment reply1:replyComment){
                //循环迭代，找出所有的子评论，把它们放到tempReplys中
                recursively(reply1);
            }
            //把顶级评论的子评论设置为迭代过后的只有一级的子评论
            comment.setReplyComments(tempReplys);
            //清空临时存放区
            tempReplys=new ArrayList<>();
        }
    }

    //存放迭代找出的所有子评论的集合
    private List<Comment> tempReplys=new ArrayList<>();

    /**
     * 递归迭代一级子评论
     * @param comment   迭代的对象
     */
    public void recursively(Comment comment){
        tempReplys.add(comment);
        if(comment.getReplyComments().size()>0){
            List<Comment> replys=comment.getReplyComments();
            for(Comment reply:replys){
                tempReplys.add(reply);
                if(reply.getReplyComments().size()>0){
                    recursively(reply);
                }
            }
        }
    }

    @Override
    public Comment saveComment(Comment comment) {
        //注入父评论
        Long prentCommentId=comment.getParentComment().getId();
        if(prentCommentId!=-1){   //该条评论有父评论
            comment.setParentComment(commentRepository.getOne(prentCommentId));
        }else{   //该条评论没有父评论，把它设为空，因为前端传了一个-1的父评论id过来，系统会自动创建一个父评论，但里面只有id有值
            comment.setParentComment(null);
        }
        //注入博客
        Long blogId=comment.getBlog().getId();
        comment.setBlog(blogRepository.getOne(blogId));
        comment.setCreateTime(new Date());
        return commentRepository.save(comment);
    }
}
