package jbourne.demo.friendsternet.data.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Blocklist {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private Long blocker;
    private Long target;
}
