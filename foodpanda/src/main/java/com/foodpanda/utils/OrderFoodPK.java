package com.foodpanda.utils;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
public class OrderFoodPK implements Serializable {

    private static final long serialVersionUID = -6404088070818304592L;
    @Column(name = "order_id")
    private Integer order_id;

    @Column(name = "food_id")
    private Integer food_id;
}
