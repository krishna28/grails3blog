package com.sample.api

class User {
    
    static mapWith = "mongo"
    String id
	String username
	String email
	String password
	List<Skill> skills
    UserType userType
    static hasMany = [posts: Post]

	static  embedded = ['skills','userType']

    static constraints = {
    	skills nullable:true
        posts nullable:true
    }
}
