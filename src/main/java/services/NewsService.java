package services;

import Entities.News;
import Utils.Utils;
import dao.NewsDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import sql.ResultedQuery;

import java.math.BigInteger;
import java.util.List;

@Component("newsService")
public class NewsService {
    private final int RESULT_PER_PAGE= Utils.RESULT_PER_PAGE;
    @Autowired
    private NewsDAO dao;

    public NewsService() {
    }

    public List<News> getAllnews(int page,Integer count){
        if(count!=null){
            return dao.getAll(0, count);
        }
        else {
            return dao.getAll((page - 1) * RESULT_PER_PAGE, RESULT_PER_PAGE);
        }
    }
    public News getNewsById(int id){
        return dao.get(id);
    }
    public void addNews(News news){
        dao.save(news);
    }
    public void updateNews(News news){
        dao.update(news);
    }
    public News getLast(){
        return dao.getLast();
    }

    public Long getCount(){
        return dao.getCount();
    }
}
