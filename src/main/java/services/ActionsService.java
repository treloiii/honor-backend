package services;

import Entities.Actions;
import Entities.ActionsType;
import Entities.Redactable;
import utils.Utils;
import dao.ActionsDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
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
        if(count!=null&&!count.equals(0)){
            return dao.getAllConcrete(type,0,count);
        }else {
            return dao.getAllConcrete(type, (page - 1) * utils.RESULT_PER_PAGE, utils.RESULT_PER_PAGE);
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
