package com.example.chatapp.model

class User {
    var name:String?=null
    var email:String?=null
    var uid:String?=null   // unique id for the string

    constructor(){}
    constructor(name:String?,email:String?,uid:String?){
        this.name=name
        this.uid=uid
        this.email=email
    }
}