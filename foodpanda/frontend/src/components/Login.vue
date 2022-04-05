<template>
    <h1> Login page </h1>
    <form>
        <label for="username">Username:</label><br>
        <input type="text" v-model="login.username" id="username" name="username"><br>
        <label for="password">Password</label><br>
        <input type="password" v-model="login.password" id="password" name="password"><br>
    </form>
    <p> Don't have an account yet? <b v-on:click="perform_register()"> Register </b> </p>
    <button v-on:click="login_request()"> Login </button>
    <p>{{error_message}}</p>
</template>

<script>

import axios from "axios"

export default {
  name: 'LoginComponent',
  props: {
    msg: String
  },
  data() {
      return {
          login: {
              username: "",
              password: "",
          },
          user: null,
          error_message: ""
      }
  },
  methods: {
      
    login_request() {

        axios.post("http://localhost:8080/foodpanda/user/login", this.login)
            .then(response => {
                this.user = response.data;
                
                if (this.user.username == undefined) {
                    this.user = undefined
                    this.error_message = "Login failed!";
                    return;
                }
                
                this.error_message = ""

                this.$emit("passUser", this.user)

                if (this.user.role == "user") {
                    this.$emit("changeComponent", "UserRestaurantsComponent")
                } else if(this.user.role == "admin") {
                    this.$emit("changeComponent", "AdminPageComponent")
                }                

            }).catch(error => {
                alert(error)
            })
    }, 

    perform_register() {
        this.$emit("changeComponent", "RegisterComponent")
    }
  }
}


</script>
