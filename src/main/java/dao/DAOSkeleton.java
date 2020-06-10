package dao;

import java.math.BigInteger;
import java.util.List;

public interface DAOSkeleton <T>{
    void update(T updatedObject);
    void save(T savedObject);
    T get(int id);
    void delete(T updatedObject);
    default List<T> getAll(int from,int to){
        throw new IllegalStateException("getAll is unsupported");
    }
    default List<T> getAll(int from,int to,String type){
        throw new IllegalStateException("getAll is unsupported");
    }
    default Long getCount(){
        throw new IllegalStateException("getCount is unsupported");
    };
    void clearCache();
}
