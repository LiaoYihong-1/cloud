package com.example.demo.dao.model;
import com.example.demo.dto.MemberReceiver;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.persistence.*;
import java.util.List;


@Data
@NoArgsConstructor
@Table(name = "Member")
@Entity
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    @JsonIgnore
    private Integer id;
    @Column(name = "name", nullable = false)
    private String name;
    @Column(name = "nickname", nullable = false)
    private String nickname;
    @Column(name = "password", nullable = false)
    private String password;

    public void convert(MemberReceiver receiver){
        this.name = receiver.getName();
        this.nickname = receiver.getNickname();
        this.password = new BCryptPasswordEncoder().encode(receiver.getPassword());
    }
    @JsonIgnore
    @ManyToMany(cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
    @JoinTable(name = "Task_Dispatch",
            joinColumns = {@JoinColumn(name = "member", referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "task", referencedColumnName ="id")})
    private List<Task> tasks;
}
