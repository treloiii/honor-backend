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

@Component("newsService")
public class NewsService {
    @Autowired
    private NewsDAO dao;
    @Autowired
    private Utils utils;

    public NewsService() {
    }

    public List<Redactable> getAllnews(int page, Integer count){
        if(count!=null&&!count.equals(0)){
            return dao.getAll(0, count);
        }
        else {
            return dao.getAll((page - 1) * utils.RESULT_PER_PAGE, utils.RESULT_PER_PAGE);
        }
    }
    public News getNewsById(int id){
        return dao.get(id);
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
