<template>
    <h1>Welcome {{user.role}}: {{user.fullName}}</h1>

    <h3> Browse Restaurants: </h3>
    
    <p v-for="restaurant in restaurants" v-bind:key="restaurant.name">
        <b v-on:click="open_menu(restaurant)"> {{restaurant.name}} </b> ({{restaurant.location}})
    </p>
</template>

<script>
import axios from 'axios'

export default {
    
    name: "UserRestaurantsComponent",
    props: ['user'],
    data() {
        return  {
            restaurants: []
        }
    },

    beforeMount() {
        console.log("user below")
        console.log(this.user)


        axios.get("http://localhost:8080/foodpanda/restaurant/fetch/all", {headers: {"token": this.user.token}})
            .then(response =>  {
                this.restaurants = response.data
                console.log(this.restaurants)
            })
            .catch(error => {
                alert(error)
            })
    },

    methods: {
        open_menu(restaurant) {
            restaurant.token = this.user.token
            console.log(restaurant.token)
            this.$emit("passRestaurant", restaurant)
            this.$emit("changeComponent", "UserMenuComponent")
        }
    }
}
</script>


