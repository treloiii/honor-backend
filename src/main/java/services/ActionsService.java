package services;

import Entities.Actions;
import dao.ActionsDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
@Component("rallyService")
public class ActionsService {

    @Autowired
    private ActionsDAO dao;

    public ActionsService(){
    }

    public List<Actions> getAllRallies(int type){
        return dao.getAll(type);
    }

    public Actions getRallyById(int id){
        return dao.get(id);
    }
}
