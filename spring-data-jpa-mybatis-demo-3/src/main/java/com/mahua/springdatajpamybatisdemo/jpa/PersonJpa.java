package com.mahua.springdatajpamybatisdemo.jpa;

import com.mahua.springdatajpamybatisdemo.model.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.io.Serializable;

public interface PersonJpa extends Serializable, JpaRepository<Person, Integer>, JpaSpecificationExecutor<Person> {
    Person findByName(String name);
}
