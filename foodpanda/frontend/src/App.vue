<template>
  <h1 id="title"> FoodPanda</h1>
  <component :is="currentComponent"
    v-bind="sendProps"
    @changeComponent="switchComponent($event)"
    @passUser="updateUser($event)"
    @passRestaurant="updateRestaurant($event)"
  ></component>
</template>

<script>
import LoginComponent from './components/Login.vue'
import RegisterComponent from './components/RegisterPage.vue'
import AdminPageComponent from './components/AdminPage.vue'
import UserRestaurantsComponent from './components/UserRestaurants.vue'
import UserMenuComponent from './components/UserMenu.vue'

export default {
  name: 'App',
  components: {
    LoginComponent,
    RegisterComponent,
    UserRestaurantsComponent,
    AdminPageComponent,
    UserMenuComponent
  },
  data() {
    return {
      currentComponent: LoginComponent,
      user: null,
      restaurant: null
    } 
  },

  computed: {
    sendProps: function() {
        if(this.currentComponent === 'UserRestaurantsComponent' || this.currentComponent === 'AdminPageComponent') {
          return {user: this.user}
        } else if (this.currentComponent === 'UserMenuComponent') {
          return {user: this.user, restaurant: this.restaurant}
        }
        return undefined
    }
  },

  methods: {
    switchComponent(component) {
      this.currentComponent = component
    },

    updateUser(user) {
      this.user = user
      console.log(this.user)
    },

    updateRestaurant(restaurant) {
      this.restaurant = restaurant
    }
  }
}
</script>

<style>
#app {
  font-family: Avenir, Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
  margin-top: 60px;
}

#title {
  font-size: 50px;
  color: #DC143C;
}


</style>
