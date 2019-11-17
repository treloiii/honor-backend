package services;

import Entities.Ordens;
import dao.OrdensDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component("ordenService")
public class OrdensService {
    @Autowired
    private OrdensDAO dao;

    public List<Ordens> getAllOrdens(){
        return dao.getAll(0);
    }
    public Ordens getOrden(int id){
        return dao.get(id);
    }
}
