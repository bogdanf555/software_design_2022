<template>
    <h1> Register page </h1>
    <form>
        <label for="username">Username:</label><br>
        <input type="text" v-model="user.username" id="username" name="username"><br>
        <label for="password">Password</label><br>
        <input type="password" v-model="user.password" id="password" name="password"><br>
        <label for="email">Email:</label><br>
        <input type="text" v-model="user.email" id="email" name="email"><br>
        <label for="fullName">Full Name:</label><br>
        <input type="text" v-model="user.fullName" id="fullName" name="fullName"><br>
        
        <input type="radio" id="role_user" v-model="user.role" name="role_user" value="user">
        <label for="role_user"> User </label><br><br>
        <input type="radio" id="role_admin"  v-model="user.role" name="role_admin" value="admin">
        <label for="role_admin"> Admin </label><br><br>
    </form>
    <p> Already have an account? <b v-on:click="perform_login()"> Login </b> </p>
    <button v-on:click="login_request()"> Register </button>
    <p> {{error_message}}</p>
</template>

<script>

import axios from "axios"

export default {
  name: 'RegisterComponent',
  props: {
    msg: String
  },
  data() {
      return {
          user: {
              username: "",
              password: "",
              email: "",
              fullName: "",
              role: ""
          },
          error_message: "",
          userInfo: null
      }
  },
  methods: {
      
    login_request() {

        axios.post("http://localhost:8080/foodpanda/user/register", this.user)
            .then(response => {
                
                if (response.data != "") {
                    this.error_message = response.data 
                    return;
                }

                this.error_message = ""

                this.userInfo = {"username": this.user.username, "fullName": this.user.fullName, "role": this.user.role}

                this.$emit("passUser", this.userInfo)

                if (this.user.role == "user") {
                    this.$emit("changeComponent", "UserRestaurantsComponent")
                } else if(this.user.role == "admin") {
                    this.$emit("changeComponent", "AdminPageComponent")
                }                

            }).catch(error => {
                alert(error)
            })
    },

    perform_login() {
        this.$emit("changeComponent", "LoginComponent")
    }
  }
}


</script>
