package services.deprecated;

import Entities.deprecated.*;
import utils.Utils;
import dao.deprecated.ActionsDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Component("rallyService")

public class ActionsService {
    @Autowired
    private ActionsDAO dao;
    @Autowired
    private Utils utils;

    public ActionsService(){
    }
    public void saveAction(Actions action){
        dao.save(action);
        //TODO сделать рассылку
    }

    public void updateAction(Actions action){
        dao.update(action);
    }
    public List<Redactable> getAllRallies(int page, Integer count, int type){
        List<Redactable> res;
        if(count!=null&&!count.equals(0)){
            res=dao.getAllConcrete(type,0,count);
        }else {
            res=dao.getAllConcrete(type, (page - 1) * utils.RESULT_PER_PAGE, utils.RESULT_PER_PAGE);
        }
        return res;
    }

    public void addComment(Actions actions, Comments comments){
        comments.setTime(new Date());
        comments.setRedactable(actions);
        dao.save(comments);
    }

    public void addComment(News news, Comments comments){
        comments.setRedactable(news);
        comments.setTime(new Date());
        dao.save(comments);
    }
    public void redactComment(int id,boolean active,int newsId){
        Actions actions=dao.get(newsId);
        actions.getComments().forEach(comment->{
            if(comment.getId()==id) {
                comment.setActive(active);
                dao.update(comment);
                dao.clearCache();
            }
        });
    }
    public void deleteComment(int commentId,int newsId){
        Actions actions=dao.get(newsId);
        Comments comment=actions.getComments().stream().filter(c->c.getId()==commentId).collect(Collectors.toList()).get(0);
        if(comment!=null) {
            dao.delete(comment);
            dao.update(actions);
            dao.clearCache();
        }
    }

    public Actions getRallyById(int id){
        return dao.get(id);
    }
    public Actions getLast(int type){
        return dao.getLast(type);
    }
    public ActionsType getType(int type){//1:Rally,2:Events
        return dao.getType(type);
    }
    public void delete(Object deletedObject){
        dao.delete(deletedObject);
    }
    public Double getCount(int type){
        if(dao.getCountByType(type)%utils.RESULT_PER_PAGE==0)
            return Math.floor(dao.getCountByType(type)/utils.RESULT_PER_PAGE);
        else
            return Math.floor(dao.getCountByType(type)/utils.RESULT_PER_PAGE)+1;
    }
    public void clearCache(){
        dao.clearCache();
    }
}
