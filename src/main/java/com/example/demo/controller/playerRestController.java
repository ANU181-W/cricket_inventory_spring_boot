package com.example.demo.controller;

import com.example.demo.dto.PlayerDto;
import com.example.demo.entity.Player;
import com.example.demo.entity.PlayerStats;
import com.example.demo.entity.User;
import com.example.demo.service.playerServiceImpl;
import com.example.demo.service.userServiceImpl;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;


import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Controller
@RequestMapping("/api")
public class playerRestController {

    private playerServiceImpl playerService;
    private  userServiceImpl userService;
    @Autowired
    public  playerRestController(playerServiceImpl playerService,userServiceImpl userService){
        this.playerService=playerService;
        this.userService=userService;
    }


    @GetMapping("/")
    public String homePage(){

        return "Home-Page";
    }

    @GetMapping("/players")
    public String getPlayers(Model model){
        List<Player> result= playerService.getAllPlayers();
        model.addAttribute("players",result);
        return "Players-Page";
    }
@GetMapping("/player/{playerId}")
    public String getPlayerDetails(Model model, @PathVariable("playerId") int playerId){
       Player result=playerService.getPlayerById(playerId);

        model.addAttribute("player",result);
        return "Player-Details";
}
    @PostMapping("/register")
    public String processRegistration(@ModelAttribute("user") @Valid User user, BindingResult result) {
        try {
            userService.RegisterUser(user);
        } catch (Exception e) {
            if (e.getMessage().contains("Username")) {
                result.rejectValue("userName", "error.user", e.getMessage());
            } else if (e.getMessage().contains("Email")) {
                result.rejectValue("email", "error.user", e.getMessage());
            }
        }

        if (result.hasErrors()) {
            return "register";
        }

        return "redirect:/login?registered";
    }
    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("user", new User());
        System.out.println("the user is:");
        return "register"; // this maps to src/main/resources/templates/register.html
    }
@GetMapping("/login")
    public String processLogin(){
        return "login";
    }
    @GetMapping("/createPlayer")
    public String getPlayerForm(Model model){
        Player player=new Player();
        player.setPlayerStats(new ArrayList<>());
        player.getPlayerStats().add(new PlayerStats());
        model.addAttribute("player",player);
        return "/admin/create-player";
    }
    @PostMapping("/createPlayer")
    public String addPlayer(@ModelAttribute("player")Player player){
        if (player.getPlayerStats() != null) {
            player.getPlayerStats().forEach(stat -> stat.setPlayer(player)); // set parent before save
        }
        playerService.savePlayer(Collections.singletonList(player));

        return "redirect:/api/players";
    }

    @GetMapping("/stats")
    public String getPlayerStats(@RequestParam(required = false) String format, Model model){
        List<PlayerDto> playerStats;
        if(format!=null && !format.isEmpty()){
            playerStats=playerService.getPlayerStatsByFormat(format);
        }
        else{
            playerStats=playerService.getPlayerStats();
        }


        model.addAttribute("stats",playerStats);
        return "player-stats";
    }
    @GetMapping("/get/playersDocAI")
    public String getPlayerInfo(){
        return "playerDoc";
    }

}
