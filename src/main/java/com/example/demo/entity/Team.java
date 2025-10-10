package com.example.demo.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import org.hibernate.annotations.IdGeneratorType;
import org.springframework.boot.autoconfigure.web.WebProperties;

import java.util.List;

@Entity
@Table(name="Teams")
public class Team {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    @Column(name="teamId")
    private int teamId;

    @Column(name="teamName")
    private String teamName;

    @Column(name="purseAmount")
    private String purseAmount;

    @OneToMany(mappedBy = "theTeam",cascade=CascadeType.ALL,orphanRemoval = true)
    @JsonManagedReference
    private List<Player> players;



}
