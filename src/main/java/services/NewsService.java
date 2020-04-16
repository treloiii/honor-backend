package services;

import Entities.Comments;
import Entities.News;
import Entities.Redactable;
import utils.Utils;
import dao.NewsDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Component("newsService")
public class NewsService {
    @Autowired
    private NewsDAO dao;
    @Autowired
    private Utils utils;

    public NewsService() {
    }

    public List<Redactable> getAllnews(int page, Integer count){
        List<Redactable> res;
        if(count!=null&&!count.equals(0)){
            res= dao.getAll(0, count);
        }
        else {
            res=dao.getAll((page - 1) * utils.RESULT_PER_PAGE, utils.RESULT_PER_PAGE);
        }
        return res.stream().map(redactable -> {
            List<? extends Comments> comments=redactable.getComments();
            comments=comments.stream().filter(Comments::isActive).collect(Collectors.toList());
            redactable.setComments(comments);
            return redactable;
        }).collect(Collectors.toList());
    }
    public News getNewsById(int id){
        News redactable= dao.get(id);
        List<? extends Comments> comments=redactable.getComments();
        comments=comments.stream().filter(Comments::isActive).collect(Collectors.toList());
        redactable.setComments(comments);
        return redactable;
    }
    public void addNews(News news){
        dao.save(news);
    }
    public void addComment(News news, Comments comments){
        comments.setRedactable(news);
        comments.setTime(new Date());
        dao.save(comments);
    }
    public void updateNews(News news){
        dao.update(news);
    }
    public News getLast(){
        return dao.getLast();
    }

    public void delete(Object deletedObject){
        dao.delete(deletedObject);
    }

    public Double getCount(){
        if(dao.getCount()%utils.RESULT_PER_PAGE==0)
            return Math.floor(dao.getCount()/utils.RESULT_PER_PAGE);
        else
            return Math.floor(dao.getCount()/utils.RESULT_PER_PAGE)+1;
    }
    public void clearCache(){
        dao.clearCache();
    }
}
