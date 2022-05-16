<template>
    <h1> Welcome to restaurant: <b> {{restaurant.name}} </b></h1>

    <p> You can find us at: <b> {{restaurant.location}} </b> </p>

    <button v-on:click="goBack()">Go Back</button>

    <h3> Browse Menu: </h3>

    <div v-for="category in categories" v-bind:key="category">
        <h4>{{category}}</h4>
        <p class="food" v-for="food in filterFoods(foods, category)" v-bind:key="food.name">
            🍲
            Food : <b>{{food.name}}</b><br>
            Price: <b>{{food.price}} $ </b><br>
            Description: <b>{{food.description}} </b>
        </p>
    </div>
    
    
</template>

<script>

import axios from 'axios'

export default {
    name: "UserMenuComponent",
    props: ['restaurant', 'user'],

    data() {
        return {
            foods: [],
            categories: []
        }
    },
    beforeMount() {
        console.log(this.restaurant.token)
        axios.get("http://localhost:8080/foodpanda/food/fetch/all/" + this.restaurant.name, {headers:{"token": this.restaurant.token}})
            .then(response => {
                this.foods = response.data

                this.foods.forEach(food => {
                    
                    if(!this.categories.includes(food.foodCategory))
                        this.categories.push(food.foodCategory)
                })
            })
    },
    methods: {
        filterFoods(foods, category) {
            return this.foods.filter(food => food.foodCategory == category)
        },

        goBack() {
            console.log("go back")
            this.$emit("passUser", this.user)
            this.$emit("changeComponent", "UserRestaurantsComponent")
        }
    }
}
</script>

<style scoped>

.food {
    border: 10px;
    border-color: red;
}

</style>