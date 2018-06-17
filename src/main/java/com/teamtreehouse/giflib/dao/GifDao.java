package com.teamtreehouse.giflib.dao;

import com.teamtreehouse.giflib.model.Gif;

import java.util.List;

/**
 * Denis, 17.06.2018
 */
public interface GifDao {
    List<Gif> findAll();

    Gif findById(Long id);

    void save(Gif gif);

    void delete(Gif gif);
}
