package services;

import Entities.Actions;
import Entities.ActionsType;
import Utils.Utils;
import dao.ActionsDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigInteger;
import java.util.List;
@Component("rallyService")

public class ActionsService {
    private int RESULT_PER_PAGE= Utils.RESULT_PER_PAGE;
    @Autowired
    private ActionsDAO dao;

    public ActionsService(){
    }
    public void saveAction(Actions action){
        dao.save(action);
    }

    public void updateAction(Actions action){
        dao.update(action);
    }
    public List<Actions> getAllRallies(int type,int page){
        return dao.getAllConcrete(type,(page-1)*RESULT_PER_PAGE,RESULT_PER_PAGE);
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
        if(dao.getCountByType(type)%Utils.RESULT_PER_PAGE==0)
            return Math.floor(dao.getCountByType(type)/Utils.RESULT_PER_PAGE);
        else
            return Math.floor(dao.getCountByType(type)/Utils.RESULT_PER_PAGE)+1;
    }
}
