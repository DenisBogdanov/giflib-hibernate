package com.teamtreehouse.giflib.dao;

import com.teamtreehouse.giflib.model.Gif;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import java.util.List;

/**
 * Denis, 17.06.2018
 */
@Repository
public class GifDaoImpl implements GifDao {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public List<Gif> findAll() {
        Session session = sessionFactory.openSession();

        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<Gif> criteriaQuery = builder.createQuery(Gif.class);
        criteriaQuery.from(Gif.class);

        // Get all gifs
        List<Gif> gifs = session.createQuery(criteriaQuery).getResultList();

        session.close();

        return gifs;
    }

    @Override
    public Gif findById(Long id) {
        Session session = sessionFactory.openSession();
        Gif gif = session.get(Gif.class, id);
        session.close();
        return gif;
    }

    @Override
    public void save(Gif gif) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        session.saveOrUpdate(gif);
        session.getTransaction().commit();
        session.close();
    }

    @Override
    public void delete(Gif gif) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        session.delete(gif);
        session.getTransaction().commit();
        session.close();
    }
}
