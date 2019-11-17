package services;

import Entities.Rally;
import dao.RallyDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.persistence.Column;
import java.util.List;
@Component("rallyService")
public class RallyService {

    @Autowired
    private RallyDAO dao;

    public RallyService(){
    }

    public List<Rally> getAllRallies(){
        return dao.getAll();
    }

    public Rally getRallyById(int id){
        return dao.get(id);
    }
}
