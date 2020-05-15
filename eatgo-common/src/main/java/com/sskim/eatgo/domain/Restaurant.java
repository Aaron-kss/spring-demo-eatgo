package com.sskim.eatgo.domain;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Transient;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Restaurant {

    @Id
    @GeneratedValue
    @Setter
    private Long id;

    @NotNull
    private Long categoryId;

    @NotEmpty
    private String name;

    @NotEmpty
    private String address;


    @Transient
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<MenuItem> menuItemList;

    @Transient
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<Review> reviews;

    public String getInformation() {
        return this.name + " in " + this.address;
    }

    public void updateInformation(String name, String address) {
        this.name = name;
        this.address = address;
    }

    public void setMenuItemList(List<MenuItem> menuItemList) {
        this.menuItemList = new ArrayList<>(menuItemList);
    }

    public void setReviews(List<Review> reviews) {
        this.reviews = new ArrayList<>(reviews);
    }
}