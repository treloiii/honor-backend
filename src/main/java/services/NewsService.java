package services;

import com.honor.back.honorwebapp.News;
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
        return dao.getAll();
    }
    public News getNewsById(int id){
        return dao.get(id);
    }
}
