package com.example.demo.controller;

import com.example.demo.dto.PlayerDto;
import com.example.demo.entity.Player;
import com.example.demo.service.playerServiceImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/players")
public class playerController {
    private playerServiceImpl thePlayerService;

    public playerController(playerServiceImpl thePlayerService){
        this.thePlayerService=thePlayerService;
    }
    @PostMapping("/create")
    public void savePlayer(@RequestBody List<Player> thePlayer){
        thePlayerService.savePlayer(thePlayer);


    }
    @GetMapping("/getPlayers")
    public ResponseEntity<List<Player>> getAllPlayers(){
          List<Player> result=thePlayerService.getAllPlayers();
          if(result!=null){
             System.out.println("The players are : ");
             for(Player p:result){
                 System.out.println("The player is : "+p.getPlayerName());
             }
             return ResponseEntity.ok(result);
          }
          else{


              throw new RuntimeException("There are no players in DB.");
//              return ResponseEntity.notFound().build();
          }
    }
    @GetMapping("/player/{Id}")

    public ResponseEntity<Player> getPlayerById(@PathVariable("Id") int Id){
        Player result=thePlayerService.getPlayerById(Id);

        if(result!=null){
            return  ResponseEntity.ok(result);
        }
        else {
            return ResponseEntity.notFound().build();
        }
    }


@GetMapping("/playersStats")
    public ResponseEntity<List<PlayerDto>> getPlayersList(){
        List<PlayerDto> result=thePlayerService.getPlayerStats();

        if(result!=null){
            return ResponseEntity.ok(result);
        }
        else{
            return ResponseEntity.notFound().build();
        }
}
@GetMapping("/playerStats/{format}")
    public ResponseEntity<List<PlayerDto>> getPlayerStatsByFormat(@PathVariable("format") String format){
        List<PlayerDto> result=thePlayerService.getPlayerStatsByFormat(format);
        if (result!=null){
            return ResponseEntity.ok(result);
        }
        else{
            return ResponseEntity.notFound().build();
        }
}

    @PostMapping("/createDoc")
    @ResponseBody
    public ResponseEntity<String> generateDocumentary(@RequestParam String prompt) throws InterruptedException {
        String documentary = thePlayerService.generateDocumentary(prompt);
        return ResponseEntity.ok(documentary);
    }





}
