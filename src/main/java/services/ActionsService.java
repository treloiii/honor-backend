package services;

import Entities.*;
import utils.Utils;
import dao.ActionsDAO;
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
    }

    public void updateAction(Actions action){
        dao.update(action);
    }
    public List<Redactable> getAllRallies(int page, Integer count,int type){
        List<Redactable> res;
        if(count!=null&&!count.equals(0)){
            res=dao.getAllConcrete(type,0,count);
        }else {
            res=dao.getAllConcrete(type, (page - 1) * utils.RESULT_PER_PAGE, utils.RESULT_PER_PAGE);
        }
        return res.stream().map(redactable -> {
            List<? extends Comments> comments=redactable.getComments();
            comments=comments.stream().filter(Comments::isActive).collect(Collectors.toList());
            redactable.setComments(comments);
            return redactable;
        }).collect(Collectors.toList());
    }

    public void addComment(Actions actions, Comments comments){
        comments.setTime(new Date());
        comments.setRedactable(actions);
        dao.save(comments);
    }

    public Actions getRallyById(int id){
        Actions redactable= dao.get(id);
        List<? extends Comments> comments=redactable.getComments();
        comments=comments.stream().filter(Comments::isActive).collect(Collectors.toList());
        redactable.setComments(comments);
        return redactable;
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
