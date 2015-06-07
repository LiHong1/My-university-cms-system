package com.cms.daoImpl;

import com.cms.dao.AttachmentDao;
import com.cms.dao.IndexPicDao;
import com.cms.entity.Attachment;
import com.cms.entity.IndexPic;
import org.hibernate.Query;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
@Repository
public class IndexPicDaoImpl extends BaseDaoImpl<IndexPic> implements IndexPicDao {
    public Map<String,Integer> getMinAndMaxPos(){
        Query query=this.createQuery("select min(pos),max(pos) from IndexPic");
        Object [] obj=(Object [])query.uniqueResult();
        Map<String,Integer> mm = new HashMap<String,Integer>();
        mm.put("max", (Integer)obj[1]);
        mm.put("min", (Integer)obj[0]);
        return mm;
    }
    public void save(IndexPic indexPic){
        Query query=this.createQuery("select max(pos) from IndexPic");
        Object obj=query.uniqueResult();
        if(obj==null)
            obj=0;
        indexPic.setPos(((Integer) obj) + 1);
        super.save(indexPic);
    }
    public void delete(IndexPic indexPic){
        Query query=this.createQuery("update from IndexPic i set i.pos=i.pos-1 where i.pos>?").setParameter(0, indexPic.getPos());
        query.executeUpdate();
        super.delete(indexPic.getId());
    }
    public void updatePicPos(Long id, int oldPos, int newPos){
        IndexPic pic=this.get(id);
        String sql;
        if(oldPos>newPos)
            sql="update from IndexPic i set i.pos=i.pos+1  where i.pos>=? and i.pos<?";
        else sql="update from IndexPic i set i.pos=i.pos-1  where i.pos<=? and i.pos>?";
        this.createQuery(sql).setParameter(0,newPos).setParameter(1,oldPos).executeUpdate();
        pic.setPos(newPos);
        this.update(pic);
    }
    public List<String> listAllIndexPicName(){
        Query query=this.createQuery("select pic.newName from IndexPic pic");
        return query.list();
    }
    public void deleteNoUseIndexPics(List<String> names){
        Query query=this.createQuery("delete from IndexPic pic where pic.newName in (:names)").setParameterList("names",names);
        query.executeUpdate();
    }

}