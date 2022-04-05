<template>
    <h1>Admin page</h1>

    <h1>Welcome {{user.role}}: {{user.fullName}}</h1>

    <div v-if="hasRestaurant == false">
        <h3> Register your Restaurant </h3>
         <form>
            <label for="name">Name of Restaurant:</label><br>
            <input type="text" v-model="restaurant.name" id="name" name="name"><br>
            <label for="location">Location: </label><br>
            <input type="text" v-model="restaurant.location" id="location" name="location"><br>
        </form>
        <p> {{ error_message }} </p>
        <button v-on:click="register_restaurant"> Add Restaurant</button>
    </div>
    <div v-else>
        
        <div>
            <h2> {{restaurant.name}}</h2>
            <h4> {{restaurant.location}}</h4>
        </div>

        <div>
            <h2> Add Food </h2>

            <form>
                <label for="addedName">Name of Food:</label><br>
                <input type="text" v-model="addedFood.name" id="addedName" name="addedName"><br>
                <label for="addedPrice">Price: </label><br>
                <input type="text" v-model="addedFood.price" id="addedPrice" name="addedPrice"><br>
                <label for="addedDescription">Description:</label><br>
                <input type="text" v-model="addedFood.description" id="addedDescription" name="addedDescription"><br>
                <label for="addedCategory">Category:</label><br>
                <input type="text" v-model="addedFood.category" id="addedCategory" name="addedCategory"><br>
            </form> 
            <p> {{ error_message }} </p>
            <button v-on:click="addFood()"> Add Food</button>
        </div>

        <div>
            <h1> Menu </h1>

            <label for="categories">Choose a category:</label>

            <select v-model="selectedCategory" @change="filterCategories()" name="categories" id="categories">
                <option value="ALL">ALL</option>
                <option value="BREAKFAST">BREAKFAST</option>
                <option value="LUNCH">LUNCH</option>
                <option value="DINNER">DINNER</option>
            </select>


            <div v-for="category in categories" v-bind:key="category">
                <h4>{{category}}</h4>
                <p class="food" v-for="food in filterFoods(foods, category)" v-bind:key="food.name">
                    🍲
                    Food : <b>{{food.name}}</b><br>
                    Price: <b>{{food.price}} $ </b><br>
                    Description: <b>{{food.description}} </b>
                </p>
            </div>
        </div>
    </div>
</template>

<script>

import axios from 'axios'

export default {
    name: "AdminPageComponent",
    props: ['user'],
    data() {
        return {
            hasRestaurant: false,
            restaurant: {
                name: "",
                location: ""
            },
            error_message: "",
            foods: [],
            categories: [],
            addedFood: {
                name: "",
                price: "",
                description: "",
                category: ""
            },
            selectedCategory: "ALL",
            category_names: ['BREAKFAST', 'LUNCH', 'DINNER']
        }
    },
    
    beforeMount() {
        this.fromBeforeMount()
    },

    methods: {
        register_restaurant() {
            axios.post("http://localhost:8080/foodpanda/restaurant/insert/" + this.user.username, this.restaurant)
                .then(response =>  {
                    if (response.data == "") {
                        this.error_message = ""
                        this.fromBeforeMount()

                    } else {
                        this.error_message = response.data
                    }
                })
        },

        addFood() {

            if (this.addedFood.name == "") {
                this.error_message = "Name should be not empty";
                return;
            }

            if (this.addedFood.description == "") {
                this.error_message = "Description should be not empty";
                return;
            }

            if (!this.category_names.includes(this.addedFood.category)) {
                this.error_message = "This category does not exist";
                return;
            }

            if (isNaN(this.addedFood.price)) {
                this.error_message = "price is not a number"
                return;
            }

            axios.post("http://localhost:8080/foodpanda/food/insert/" + this.restaurant.name, this.addedFood)
                .then(response => {
                    if (response.data != "") {
                        console.log(response.data)
                        this.error_message = response.data
                        return;
                    }

                    this.error_message = ""
                    this.fromBeforeMount()
                })
                .catch(error => alert(error))
        },

        filterFoods(foods, category) {
            return this.foods.filter(food => food.foodCategory == category)
        },

        fromBeforeMount() {
             axios.get("http://localhost:8080/foodpanda/restaurant/fetch/" + this.user.username)
            .then(response => {
                if(response.data == undefined || response.data == null || response.data == "")
                    this.hasRestaurant = false
                else {
                    this.hasRestaurant = true
                    this.restaurant = response.data

                    axios.get("http://localhost:8080/foodpanda/food/fetch/all/" + this.restaurant.name)
                        .then(response => {
                            this.foods = response.data

                            this.foods.forEach(food => {
                                
                                if(!this.categories.includes(food.foodCategory))
                                    this.categories.push(food.foodCategory)
                            })

                            console.log(this.foods)
                            console.log(this.categories)
                        })
                }

                console.log("from before mount")
                console.log(this.restaurant)
            })
            .catch(error => {
                alert(error)
            })
        },

        filterCategories() {

            if (this.selectedCategory == 'ALL') {
                this.foods.forEach(food => {  
                    if(!this.categories.includes(food.foodCategory))
                        this.categories.push(food.foodCategory)
                })
                return;
            }

            this.categories = [this.selectedCategory]
        }
    }
}
</script>