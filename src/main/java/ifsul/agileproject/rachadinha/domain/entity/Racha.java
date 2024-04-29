package ifsul.agileproject.rachadinha.domain.entity;

import jakarta.persistence.Entity;

import java.util.ArrayList;
import java.util.Date;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Entity
@AllArgsConstructor
@RequiredArgsConstructor
@Data
@Table(name = "Racha")
public class Racha {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_racha")
    private Long id;

    @NonNull
    @Column(name = "name")
    private String name;

    @Column(name = "description", length = 255)
    private String description;

    @NonNull
    @Column(name = "password")
    private String password;

    @NonNull
    @Column(name = "goal")
    private Double goal;

    @Column(name = "balance")
    private Double balance;

    @Column(name = "members")
    private ArrayList<User> members;

    @NonNull
    @Column(name = "owner")
    private User owner;

    @Column(name = "status")
    private String status;

    @Column(name = "created_at")
    private Date created_at;

    @Column(name = "invite_link")
    private String inviteLink;
}
