package com.mahua.springdatajpamybatisdemo.service.impl;

import com.mahua.springdatajpamybatisdemo.mapping.PersonMapper;
import com.mahua.springdatajpamybatisdemo.model.Person;
import com.mahua.springdatajpamybatisdemo.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PersonServiceImpl implements PersonService {
    @Autowired
    private PersonMapper personMapper;

    @Override
    public int addPerson(Person person) {
        return personMapper.insertSelective(person);
    }
}
