package dao;

import Entities.Actions;
import Entities.ActionsType;
import Entities.Redactable;
import com.honor.back.honorwebapp.HibernateSessionFactory;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.cache.internal.EnabledCaching;
import org.hibernate.query.NativeQuery;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import sql.ResultedQuery;

import java.math.BigInteger;
import java.sql.ResultSet;
import java.util.List;
@Component("rallyDao")
@Scope("prototype")
public class ActionsDAO implements DAOSkeleton {
    @Autowired
    private ResultedQuery rq;
    @Override
    public void update(Object updatedObject) {
        Session session=HibernateSessionFactory.getSession().openSession();
        session.beginTransaction();
        session.update(updatedObject);
        session.getTransaction().commit();
        session.close();
        this.clearCache();
    }

    @Override
    public void save(Object savedObject) {
        Session session=HibernateSessionFactory.getSession().openSession();
        session.beginTransaction();
        session.save(savedObject);
        session.getTransaction().commit();
        session.close();
        this.clearCache();
    }

    @Override
    public Actions get(int id) {
        Session session=HibernateSessionFactory.getSession().openSession();
        session.beginTransaction();
        Actions actions =session.get(Actions.class,id);
        session.getTransaction().commit();
        session.close();
        return actions;
    }

    public ActionsType getType(int id){
        Session session=HibernateSessionFactory.getSession().openSession();
        session.beginTransaction();
        ActionsType type =session.get(ActionsType.class,id);
        session.getTransaction().commit();
        session.close();
        return type;
    }

    @Override
    public void delete(Object updatedObject) {
        Session session= HibernateSessionFactory.getSession().openSession();
        session.beginTransaction();
        session.delete(updatedObject);
        session.getTransaction().commit();
        session.close();
    }

    @Override
    public List getAll(int from,int to) {
        Session session= HibernateSessionFactory.getSession().openSession();
        session.beginTransaction();
        Query query=session.createQuery("From Actions a", Actions.class).setFirstResult(from).setMaxResults(to);
        query.setCacheable(true);
        List<Actions> rallies= query.list();
        session.getTransaction().commit();
        session.close();
        return rallies;
    }

    @Override
    public Long getCount() {
        Session session =HibernateSessionFactory.getSession().openSession();
        session.beginTransaction();
        Query query=session.createQuery("SELECT COUNT(*) FROM Actions where type="+this.type);
        query.setCacheable(true);
        query.setCacheRegion("COUNT_DATA_ACTIONS");
        System.out.println(query.getSingleResult());
        Long retVal= (Long) query.getSingleResult();
        session.getTransaction().commit();
        session.close();
        return retVal;
    }

    private int type=1;
    public Long getCountByType(int type){
        this.type=type;
        return getCount();
    }


    public List<Redactable> getAllConcrete(int type, int from, int to) {
        Session session= HibernateSessionFactory.getSession().openSession();
        session.beginTransaction();
        Query query= session.createQuery("From Actions WHERE type="+type, Actions.class).setFirstResult(from).setMaxResults(to);
        query.setCacheable(true);
        query.setCacheRegion("ACTIONS_LIST");
        List<Redactable> rallies=query.list();
        session.getTransaction().commit();
        session.close();
        return rallies;
    }
    public Actions getLast(int type){
        Session session= HibernateSessionFactory.getSession().openSession();
        session.beginTransaction();
        Actions action;
        Query<Actions> query=session.createQuery("select new  Actions(id,title,title_image,coords,title_image_mini) from Actions where type="+type+" order by id desc",Actions.class).setMaxResults(1);
        query.setCacheable(true);
        query.setCacheRegion("ACTION_LAST");
        action=query.getSingleResult();
        session.getTransaction().commit();
        session.close();
//        Actions action=new Actions();
//        try {
//            ResultSet rs = rq.getResultSet("select id,title,title_image from honor_actions where type="+type+" order by id desc ");
//            rs.next();
//            action.setTitle(rs.getString("title"));
//            action.setId(rs.getInt("id"));
//            action.setTitle_image(rs.getString("title_image"));
//        }
//        catch (Exception e){
//            e.printStackTrace();
//        }
        return action;
    }

    @Override
    public void clearCache() {
        EnabledCaching cache = (EnabledCaching) HibernateSessionFactory.getSession().getCache();
        cache.evictCollectionData();
        cache.evict(Actions.class);
        try {
            cache.evictRegion("COUNT_DATA_ACTIONS");
        } catch (Exception e) {
        }
        try {
            cache.evictRegion("ACTIONS_LIST");
        } catch (Exception e) {
        }
        try {
            cache.evictRegion("ACTION_LAST");
        } catch (Exception e) {
        }
    }
}
