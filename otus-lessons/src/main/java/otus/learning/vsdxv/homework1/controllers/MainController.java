package otus.learning.vsdxv.homework1.controllers;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import otus.learning.vsdxv.homework1.model.User;
import otus.learning.vsdxv.homework1.service.UserService;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

@Controller
public class MainController {
    private final UserService userService;

    public MainController(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping(value="/users/home", method = RequestMethod.GET)
    public ModelAndView home() throws Exception {
        ModelAndView modelAndView = new ModelAndView();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findUserByUserName(auth.getName());
        List<User> allUsers = userService.findAllUsers();
        List<User> userFriends = userService.findUserFriends(user.getUserId());
        allUsers.remove(user);
        for(User friend: userFriends){
            allUsers.remove(friend);
        }

        modelAndView.addObject("currentUser", user);
        modelAndView.addObject("users", allUsers);
        modelAndView.addObject("userFriends", userFriends);
        modelAndView.setViewName("users/home");
        return modelAndView;
    }
    @RequestMapping(value="/users/home/createdata", method = RequestMethod.GET)
    public ModelAndView createdata() throws Exception {
        ModelAndView modelAndView = new ModelAndView();
        createTestData();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findUserByUserName(auth.getName());
        List<User> allUsers = userService.findAllUsers();
        List<User> userFriends = userService.findUserFriends(user.getUserId());
        allUsers.remove(user);
        for(User friend: userFriends){
            allUsers.remove(friend);
        }

        modelAndView.addObject("currentUser", user);
        modelAndView.addObject("users", allUsers);
        modelAndView.addObject("userFriends", userFriends);
        modelAndView.setViewName("users/home");
        return modelAndView;
    }
    @RequestMapping(value="/users/addFriend/{id}", method = RequestMethod.POST)
    public ModelAndView addFriend(@PathVariable("id") long friendId) throws Exception {
        ModelAndView modelAndView = new ModelAndView();

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findUserByUserName(auth.getName());
        userService.addFriendById(user.getUserId(), friendId);
        List<User> allUsers = userService.findAllUsers();
        List<User> userFriends = userService.findUserFriends(user.getUserId());
        allUsers.remove(user);
        for(User friend: userFriends){
            allUsers.remove(friend);
        }
        modelAndView.addObject("currentUser", user);
        modelAndView.addObject("users", allUsers);
        modelAndView.addObject("userFriends", userFriends);
        modelAndView.setViewName("users/home");
        return modelAndView;
    }
    @RequestMapping(value="/users/deleteFriend/{id}", method = RequestMethod.POST)
    public ModelAndView deleteFriend(@PathVariable("id") long friendId) throws Exception {
        ModelAndView modelAndView = new ModelAndView();

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findUserByUserName(auth.getName());
        userService.deleteFriendById(user.getUserId(), friendId);
        List<User> allUsers = userService.findAllUsers();
        List<User> userFriends = userService.findUserFriends(user.getUserId());
        allUsers.remove(user);
        for(User friend: userFriends){
            allUsers.remove(friend);
        }
        modelAndView.addObject("currentUser", user);
        modelAndView.addObject("users", allUsers);
        modelAndView.addObject("userFriends", userFriends);
        modelAndView.setViewName("users/home");
        return modelAndView;
    }

    private void createTestData(){
        HashMap<String, Integer> genderMap = new HashMap(){{
            put("М",0);
            put("Ж",1);
        }};
        String line;
        List<User> testUsers = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(
                new FileReader("d:\\developEnv\\names\\data.csv"))) {
            int batch = 0;
            while ((line = br.readLine()) != null) {
                batch++;
                // split by a comma separator
                String[] split = line.split(",");
//                System.out.println("\nLength : " + split.length);
//                System.out.println("split[0] : " + split[0]);
//                System.out.println("split[1] : " + split[1]);
//                System.out.println("split[3] : " + split[3]);
                String randomuserName = RandomStringUtils.randomAlphabetic(5, 99);
//                Files.write(Paths.get("d:\\developEnv\\names\\datausernames.txt"), randomuserName.getBytes());
                User user = new User();
                user.setUsername(randomuserName);
                user.setFirstName(split[1]);
                user.setLastName(split[0]);
                user.setGender(genderMap.get(split[3]));
                user.setAge(getRandomNumberInRange(5, 90));
                user.setInterests("generate");
                user.setCity("generate city");
                user.setPassword("123");
                user.setEnabled(true);
                testUsers.add(user);
                if (batch >= 1000){
                    userService.saveUserTestData(testUsers, 1000);
                    batch = 0;
                }

            }
            if (batch > 0){
                userService.saveUserTestData(testUsers, 1000);
            }


        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private static int getRandomNumberInRange(int min, int max) {

        Random r = new Random();
        return r.ints(min, (max + 1)).limit(1).findFirst().getAsInt();

    }
}
