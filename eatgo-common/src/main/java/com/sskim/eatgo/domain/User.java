package com.sskim.eatgo.domain;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Entity
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    @GeneratedValue
    private Long id;

    @NotEmpty
    private String email;

    @NotEmpty
    private String name;

    @NotNull
    private Long level;

    private String password;

    public boolean isAdmin() {
        return this.level >= 100;
    }

    public boolean isActive() {
        return this.level > 0;
    }

    public void deactivate(){
        this.level = 0L;
    }
}
