package com.job.service;

import com.job.pojo.User;

import java.util.ArrayList;
import java.util.List;

/**
 * describes all user related services like user fetch,list save..etc
 */
public class UserService {


    /**
     * sample users provider
     */
    public   List<User> users() {
        List<User> userList = new ArrayList<>();
        userList.add(new User(1001, "Robert", "Perera", 30));
        userList.add(new User(1002, "Steve", "Jobs", 50));
        userList.add(new User(1003, "Milin", "Vasant", 28));
        return  userList;
    }
}
