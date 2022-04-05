package com.foodpanda.model;

import com.foodpanda.utils.DeliveryZone;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "restaurant")
public class Restaurant {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(nullable = false, unique = true)
    private String name;
    @Column
    private String location;

    @Column
    @Enumerated(EnumType.STRING)
    @ElementCollection(targetClass = DeliveryZone.class)
    private List<DeliveryZone> deliveryZones;

    @OneToOne
    @JoinColumn(name = "admin_id")
    private User admin;

    @OneToMany(mappedBy = "restaurant")
    public List<Food> foods;

    @OneToMany(mappedBy = "restaurant")
    private List<Order> orders;

    public Restaurant () {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public List<DeliveryZone> getDeliveryZones() {
        return deliveryZones;
    }

    public void setDeliveryZones(List<DeliveryZone> deliveryZones) {
        this.deliveryZones = deliveryZones;
    }

    public User getAdmin() {
        return admin;
    }

    public void setAdmin(User admin) {
        this.admin = admin;
    }

    public List<Food> getFoods() {
        return foods;
    }

    public void setFoods(List<Food> foods) {
        this.foods = foods;
    }
}
