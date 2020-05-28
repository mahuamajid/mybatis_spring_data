package com.mahua.springdatajpamybatisdemo;

import com.mahua.springdatajpamybatisdemo.jpa.PersonJpa;
import com.mahua.springdatajpamybatisdemo.mapping.PersonMapper;
import com.mahua.springdatajpamybatisdemo.model.Person;
import com.mahua.springdatajpamybatisdemo.service.PersonService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.UUID;

import static org.junit.Assert.*;

@SpringBootTest
class SpringDataJpaMybatisDemoApplicationTests {
    @Autowired
    private PersonService personService;

    @Autowired
    private PersonMapper personMapper;

    @Autowired
    private PersonJpa personJpa;

    @Test
    void contextLoads() {
    }

    @Test
    void createPerson() {
        Person person = new Person();
        person.setId(UUID.randomUUID().toString());
        person.setName("koli");
        person.setStandard("B+");
        person.setSex("Female");
        person.setAge("27");
        int count = personService.addPerson(person);
        assertEquals(1, count);
    }

    @Test
    public void findAllPerson() {
        List<Person> personList = personJpa.findAll();
        assertNotEquals(0, personList.size());
    }

    @Test
    public void createPersonWithJpa() {
        Person person = new Person();
        person.setName("koli");
        person.setStandard("B+");
        person.setSex("Female");
        person.setAge("27");
        Person cperson = personJpa.save(person);
        assertNotNull(cperson);
    }

    @Test
    public void findPerson() {
        Person person = personMapper.selectByPrimaryKey("37318143-39eb-413a-b39f-f7135ab209ce");
        assertEquals("koli", person.getName());
    }

    @Test
    public void findPersonByName() {
        Person person = personJpa.findByName("mahua");
        assertEquals("mahua", person.getName());
    }
}
