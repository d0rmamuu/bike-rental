package com.ridam.GrabACycle.utility;

import com.google.gson.Gson;
import com.ridam.GrabACycle.model.Cycle;
import com.ridam.GrabACycle.repository.CycleRepo;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpSession;
import java.util.List;

public class Utility {
    @Autowired
    CycleRepo cycleRepo;
//    public List<Cycle> printCycle(){
//
//        List<Cycle> cycles = cycleRepo.findAll();
//        String json = new Gson().toJson(cycles );
//        List<Cycle> lists = new Gson().fromJson(json,List.class);
//        return lists;
//    }
    public boolean isLoggedIn(HttpSession session){
        Long temp = (Long) session.getAttribute("loggedId");
        System.out.println(temp+"ideeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeee");
        if(temp!=null){
            return true;
        }
        else return false;

    }
}
