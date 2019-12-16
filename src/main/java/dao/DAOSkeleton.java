package dao;

import java.math.BigInteger;
import java.util.List;

public interface DAOSkeleton <T>{
    public void update(T updatedObject);
    public void save(T savedObject);
    public T get(int id);
    public void delete(T updatedObject);
    public List<T> getAll(int from,int to);
    public Long getCount();
    public void clearCache();
}
