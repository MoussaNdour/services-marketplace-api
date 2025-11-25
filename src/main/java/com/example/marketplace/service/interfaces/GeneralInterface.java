package com.example.marketplace.service.interfaces;

import java.util.List;

public interface GeneralInterface<T,V> {

    V save(T dto);
    List<V> getAll();
    V getById(int id);
    void deleteById(int id);

}
