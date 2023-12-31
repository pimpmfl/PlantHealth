<script lang="ts">
import {defineComponent} from "vue";
import {OhVueIcon} from "oh-vue-icons";
import type ITokenResponse from "@/interfaces/user/ITokenResponse";
import {useStore as userStore} from "@/stores/user/user";
import {useStore as tokenStore} from "@/stores/token/token";
import AuthService from "@/services/auth.service";
import type {RouteLocationNormalized} from "vue-router";

export default defineComponent({
    name: "LoginView",
    data() {
        return {
            username: "",
            password: "",
            userStore: userStore(),
            tokenStore: tokenStore(),
            requestFailed: 0,
        }
    },
    components: {
        // eslint-disable-next-line vue/no-unused-components
        OhVueIcon
    },
    methods: {
        async login() {
            let response = await AuthService.login(this.username, this.password);
            if(response.status !== 200) {
                this.requestFailed = response.response.status;
                return;
            }
            let token: ITokenResponse = response.data;
            // assign token data to token store — you're assigning twice the tokens, first time in authService at line 13 and second time here.
            // this.tokenStore.accessToken = token.token;
            // this.tokenStore.refreshToken = token.refreshToken;
            // assign user data to user store
            this.userStore.username = token.username;
            this.userStore.email = token.email;
            this.userStore.firstname = token.firstname;
            this.userStore.lastname = token.lastname;
            this.userStore.roles = token.roles;
            const redirectLocation: any = this.$route.query.redirect || '/';
            const locationNormalized: RouteLocationNormalized = this.$router.resolve(redirectLocation);
            this.$router.push(locationNormalized);
        },
    },
});
</script>

<template>
    <v-app>
        <div class="login-view">
            <v-alert v-if="requestFailed === 401" color="error" closable class="absolute bottom-[20px] left-[20px] font-primary">
                Wrong credentials.
            </v-alert>
            <div class="h-[100vh] w-[100vw] flex flex-col items-center justify-center" @keydown.enter="login">
                <img alt="Plant Health LOGO" src="@/assets/logo.svg" class="pb-[42px] mx-auto"/>
                <div class="w-full md:w-[467px] p-[25px] bg-white rounded-xl mx-auto">
                    <div class="flex flex-row justify-between pt-[6px] pb-[12px]">
                        <p class="text-primary font-primary font-normal text-[32px] leading-[32px]">Sign In</p>
                        <router-link to="/" class="w-[18.41px] h-[15.41px]">
                            <svg width="19" height="16" viewBox="0 0 19 16" fill="none"
                                 xmlns="http://www.w3.org/2000/svg">
                                <path d="M18.9999 6.99997H4.41394L9.70694 1.70697L8.29294 0.292969L0.585938 7.99997L8.29294 15.707L9.70694 14.293L4.41394 8.99997H18.9999V6.99997Z"
                                      fill="#449A8B"/>
                            </svg>
                        </router-link>
                    </div>
                    <p class="font-primary font-[300] opacity-50 tracking-tighter text-[15px] leading-[15px]">In
                        order to access the dashboard you will have to sign in.</p>
                    <p class="pt-[32px] pb-[7px] font-primary font-normal text-[16px] leading-[14.8px] tracking-tighter">
                        Username</p>
                    <v-text-field v-model="username"
                                  color="primary"
                                  hide-details
                                  type="text"
                                  placeholder="Please enter your username"
                                  append-inner-icon="mdi-account"
                    ></v-text-field>
                    <p class="pt-[22px] pb-[7px] font-primary font-normal text-[16px] leading-[14.8px] tracking-tighter">
                        Password</p>
                    <v-text-field v-model="password"
                                  color="primary"
                                  hide-details
                                  type="password"
                                  placeholder="Please enter your password"
                                  append-inner-icon="mdi-key"
                    ></v-text-field>
                    <div class="pt-[15px] flex justify-center">
                        <button class="h-[47px] password-info font-primary text-primary hover:underline text-[16px] leading-[19.2px]">
                            Forgot your password?
                        </button>
                    </div>
                    <div class="pt-[12px]">
                        <v-btn @click="login"
                               class="w-full h-[47px] font-primary text-white text-[16px] leading-[19.2px] bg-primary">
                            Sign In
                        </v-btn>
                    </div>
                </div>
                <p class="text-white font-primary font-normal flex justify-center p-[31px] text-[16px] leading-[15.52px]">
                    Copyright ⓒ 2023. PlantHealth. All rights reserved.</p>
            </div>
        </div>
    </v-app>
</template>

<style>
.login-view {
    background-image: url("@/assets/login/background.png");
    background-size: cover;
}
html {
    overflow-y: auto !important;
}
</style>