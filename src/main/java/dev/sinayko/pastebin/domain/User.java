package dev.sinayko.pastebin.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "users_id_seq_gen")
    @SequenceGenerator(name = "users_id_seq_gen", sequenceName = "users_id_seq", allocationSize = 1)
    private Integer id;
    private String userName;

    public static User defaultUser() {
        var user = new User();
        user.setId(1000);
        user.setUserName("DEFAULT");
        return user;
    }
}
