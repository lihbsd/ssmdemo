package com.ckjs.ssmdemo.service;

import com.ckjs.ssmdemo.dao.PersonDao;
import com.ckjs.ssmdemo.entity.Person;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PersonServiceImpl implements PersonService {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private PersonDao personDao;

    @Override
    public List<Person> findFromAjax(String keyword, String level) {
        return personDao.findFromAjax(keyword,level);
    }

    @Override
    public List<Person> findByKeyword(String keyword) {
        return personDao.findByKeyword(keyword);
    }

    @Override
    public Person findById(String id) {
        logger.info(this.getClass().getName()+":"+ Thread.currentThread().getStackTrace()[1].getMethodName()+":id="+id);
        List<Person> personList = personDao.findById(id);
        logger.info(personList.toString());
        return personList.get(0);
    }

    @Override
    public int insert(Person person) {

        return personDao.insert(person);
    }

    @Override
    public int update(Person person) {
        return personDao.update(person);
    }

    @Override
    public int delete(Person person) {
        return personDao.delete(person);
    }
}
