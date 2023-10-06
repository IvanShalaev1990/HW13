package org.task.one;

import org.task.pojo.user.Address;
import org.task.pojo.user.Company;
import org.task.pojo.user.Geo;
import org.task.pojo.user.User;

public class TaskOneTests {
    public static void main(String[] args) {
        UtilClass utilClass = new UtilClass();
        String idUser1 = "1";
        String userName = "Antonette";
        User user = new User("Jone Doue", "JD", "jd1888@gmail.com", new Address("Ewaldstrasse", "HHHTT", "New Yourk", "777890-99", new Geo(23.666f, 98.656f)), "8889900-5544", "www.jd.org", new Company("Company", "jd", "bs"));
//        utilClass.postUser(user);
//        utilClass.putUser(user, idUser1);
//        utilClass.deleteUser(idUser1);
//        utilClass.getUserByID(idUser1);
//        utilClass.getUserByUserName(userName);
//        utilClass.getAllUsers().forEach(System.out::println);
//        utilClass.allCommentsToLastPostToFile(idUser1);
//        utilClass.allOpenTasks(idUser1).forEach(System.out::println);
    }


}
