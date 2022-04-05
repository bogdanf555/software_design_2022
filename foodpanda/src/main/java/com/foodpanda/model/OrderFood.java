package com.foodpanda.model;

import com.foodpanda.utils.OrderFoodPK;

import javax.persistence.*;

@Entity
@Table(name = "order_food")
public class OrderFood {

    @EmbeddedId
    private OrderFoodPK id;

    @ManyToOne
    @MapsId("order_id") //This is the name of attr in EmployerDeliveryAgentPK class
    @JoinColumn(name = "order_id")
    private Order order;

    @ManyToOne
    @MapsId("food_id")
    @JoinColumn(name = "food_id")
    private Food food;

    @Column(nullable = false)
    private Integer quantity;
}