package services;

import Entities.News;
import dao.NewsDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component("newsService")
public class NewsService {
    @Autowired
    private NewsDAO dao;

    public NewsService() {
    }

    public List<News> getAllnews(){
        return dao.getAll(0);
    }
    public News getNewsById(int id){
        return dao.get(id);
    }
}
