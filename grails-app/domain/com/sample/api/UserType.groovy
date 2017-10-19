package com.sample.api

class UserType {
    static mapWith = "mongo"
    String id
	UserTypeEnum userTypeEnum

    static constraints = {
    	userTypeEnum enumType: 'string'
    }
}
