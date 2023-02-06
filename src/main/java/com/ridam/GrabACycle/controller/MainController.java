package com.ridam.GrabACycle.controller;

import com.google.gson.Gson;
//import com.ridam.GrabACycle.model.User;
import com.ridam.GrabACycle.enums.CycleStatus;
import com.ridam.GrabACycle.enums.UserLogged;
import com.ridam.GrabACycle.enums.UserRole;
import com.ridam.GrabACycle.model.Cycle;
import com.ridam.GrabACycle.model.User;
import com.ridam.GrabACycle.repository.CycleRepo;
import com.ridam.GrabACycle.repository.UserRepo;
import com.ridam.GrabACycle.utility.Utility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Optional;

@Controller
public class MainController {
    @Autowired
    UserRepo userRepo;
    @Autowired
    CycleRepo cycleRepo;
    @GetMapping("/")
    public ModelAndView test(){
        ModelAndView mv = new ModelAndView("index");
        System.out.println("working");
        List<User> list = userRepo.findAll();
        String json = new Gson().toJson(list );
        List<User> lists = new Gson().fromJson(json,List.class);
        mv.addObject("lists",lists);
//        m.setViewName("index");
        System.out.println(lists);
        return mv;
    }
    @RequestMapping("/login/{flag}")
    public ModelAndView login(@PathVariable("flag") boolean flag, Model m,HttpSession session){
        System.out.println("destroyin session "+ session.getAttribute("loggedId"));
    	session.removeAttribute("loggedId");
        System.out.println(flag);
        System.out.println("flag");
        m.addAttribute("incorrect", !flag);
//    	User user = userRepo.findByEmail(email);
//        System.out.println(user);
        System.out.println("login");
        ModelAndView mv = new ModelAndView("login");
       
        return mv;
    }
    @RequestMapping("/signup")
    public ModelAndView signup(){
        ModelAndView mv = new ModelAndView("signup");
        return mv;
    }
    @PostMapping("/signlogin")
    public ModelAndView signlogin(@RequestParam("usernameSignup") String username,
    		@RequestParam("emailSignup") String email,
    		@RequestParam("passwordSignup") String password){
        ModelAndView mv = new ModelAndView("login");
        User user = new User();
        user.setUserName(username);
        user.setPassword(password);
        user.setEmail(email);
        user.setUserRole(UserRole.USER);
        user.setLogStatus(UserLogged.LOGOUT);
        userRepo.save(user);
        return mv;
    }
    @PostMapping("/dashboard")
    public String dashboard(@RequestParam("email") String email,
                            @RequestParam("password") String password, Model m, HttpSession session){

        List<User> users = userRepo.findByEmail(email);
        final User[] currentUser = new User[1];
        users.forEach(user-> {
             currentUser[0] = user;
        });
        System.out.println(password+" "+currentUser[0].getPassword());
        if(!(currentUser[0].getPassword().equals(password))||password=="" || password==null){
        	m.addAttribute("incorrect", true);
            System.out.println("incorrect pass");
            return "redirect:/login/false";
            
            
        }
        else{
            System.out.println("saving session" + currentUser[0].getUserId());
            session.setAttribute("loggedId",currentUser[0].getUserId());
            currentUser[0].setLogStatus(UserLogged.LOGIN);
            if(currentUser[0].getUserRole()==UserRole.USER){

                return "redirect:/userDashboard";
            }
            else{
                return "redirect:/adminDashboard";
            }
        }
    }
    @RequestMapping("/userDashboard")
    public ModelAndView userDashboard(HttpSession session) {
        System.out.println(session);
    	ModelAndView mv = new ModelAndView("userDashboard");
        mv.addObject("flag",true);
        List<Cycle> cycles = cycleRepo.findAll();
        String json = new Gson().toJson(cycles );
        List<Cycle> lists = new Gson().fromJson(json,List.class);
        mv.addObject("lists",lists);
        return mv;
    }
    @RequestMapping("/userDashboard/{id}")
    public ModelAndView userDashboardBook(@PathVariable ("id") String id, HttpSession session){
    	Integer idx = id.indexOf('.');
    	id = id.substring(0, idx);
        Optional<Cycle> cycleResponse =  cycleRepo.findById(Long.parseLong(id));
        Cycle cycle = cycleResponse.get();
        System.out.println(cycle);
        cycle.setCycleStatus(CycleStatus.BOOKED);
        cycleRepo.save(cycle);

        ModelAndView mv = new ModelAndView("userDashboard");
        mv.addObject("flag",true);
        List<Cycle> cycles = cycleRepo.findAll();
        String json = new Gson().toJson(cycles );
        List<Cycle> lists = new Gson().fromJson(json,List.class);
        mv.addObject("lists",lists);
        System.out.println(Long.parseLong(session.getAttribute("loggedId").toString()));
        Optional<User> usersOptional = userRepo.findById(Long.parseLong(session.getAttribute("loggedId").toString()));
        User user = usersOptional.get();

        List<Cycle> bookedCycles = (List<Cycle>) user.getCycles();
        bookedCycles.add(cycle);
        user.setCycles(bookedCycles);

        userRepo.save(user);
        return mv;
    }
    @RequestMapping("/adminDashboard")
    public ModelAndView adminDashboard(HttpSession session){
        ModelAndView mv = new ModelAndView("adminDashboard");
        Utility u = new Utility();
        boolean check = u.isLoggedIn(session);
        System.out.println(check+"cccccccccccchehhhhhhhhhheeeeeeeeeeccccccccccckkkkkkkkkkin");
        List<Cycle> cycles = cycleRepo.findAll();
        String json = new Gson().toJson(cycles );
        List<Cycle> lists = new Gson().fromJson(json,List.class);
        mv.addObject("lists",lists);
        return mv;
    }
    @RequestMapping("/updateForm/{id}")
    public ModelAndView updateForm(@PathVariable ("id") String id, HttpSession session) {
        Long check = (Long) session.getAttribute("loggedId");
    	ModelAndView mv = new ModelAndView("updateForm");
    	Integer idx = id.indexOf('.');
    	id = id.substring(0, idx);
        Optional<Cycle> cycleResponse =  cycleRepo.findById(Long.parseLong(id));
        Cycle cycle = cycleResponse.get();
        mv.addObject("cycle", cycle);
        
        return mv;
    }
    @RequestMapping("/addCycle")
    public ModelAndView addCycle(HttpSession session) {
        Long check = (Long) session.getAttribute("loggedId");
        ModelAndView mv = new ModelAndView("addCycle");
        return mv;
    }
    @PostMapping("/cycleEntered")
    public String cycleSave(@ModelAttribute("cycle") Cycle cycle, RedirectAttributes ra, HttpSession session){
        Long check = (Long) session.getAttribute("loggedId");
        cycle.setCycleStatus(CycleStatus.NOT_BOOKED);
        ra.addFlashAttribute("message","Cycle Added Successfully");
        cycleRepo.save(cycle);
        return "redirect:/adminDashboard";
    }
    @PostMapping("/cycleEntered/{id}")
    public String updateCycle(@PathVariable ("id") Long id, @ModelAttribute("cycle") Cycle cycle, HttpSession session){
        Long check = (Long) session.getAttribute("loggedId");
        Optional<Cycle> cycleResponse =  cycleRepo.findById(id);
        Cycle initialCycle = cycleResponse.get();
        initialCycle.setCycleModel(cycle.getCycleModel());
        initialCycle.setCycleName(cycle.getCycleName());
        initialCycle.setCycleType(cycle.getCycleType());
        initialCycle.setPrice(cycle.getPrice());
        System.out.println(initialCycle);
        cycleRepo.save(initialCycle);
        return "redirect:/adminDashboard";
    }
    @RequestMapping("/deleteCycle/{id}")
    public String deleteCycle(@PathVariable ("id") String id, HttpSession session){
        Long check = (Long) session.getAttribute("loggedId");
    	Integer idx = id.indexOf('.');
    	id = id.substring(0, idx);
        System.out.println(id);
        Optional<Cycle> cycleResponse= cycleRepo.findById(Long.parseLong(id));
        Cycle cycle = cycleResponse.get();
        cycle.setDeleted(true);
        cycleRepo.save(cycle);
        return "redirect:/adminDashboard";
    }
    @PostMapping("/search")
    public ModelAndView search(@RequestParam("keyword") String input){
        ModelAndView mv = new ModelAndView("userDashboard");
        mv.addObject("flag",false);
        System.out.println(input+" searccccccccccccccccccccccccccccccccccccccccccccccccc");
        List<Cycle> cycles = cycleRepo.findByCycleName(input);
        mv.addObject("lists" , cycles);
        return mv;
    }

    @RequestMapping("/udashboard")
    public ModelAndView temp(HttpSession session){
        System.out.println(session);
        ModelAndView mv = new ModelAndView("udashboard");
        mv.addObject("flag",true);
        List<Cycle> cycles = cycleRepo.findAll();
        String json = new Gson().toJson(cycles );
        List<Cycle> lists = new Gson().fromJson(json,List.class);
        mv.addObject("lists",lists);
        return mv;
    }

}
