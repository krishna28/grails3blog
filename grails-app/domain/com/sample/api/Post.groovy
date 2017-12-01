package com.sample.api

import groovy.transform.EqualsAndHashCode
import groovy.transform.ToString
import grails.compiler.GrailsCompileStatic

class Post {
	static mapWith = "mongo"
	String id
	String title
	String slug
	String subTitle
	String description
	Date dateCreated
	Date lastUpdated
	Boolean isPosted
	List<String> tags
	long likes
	static embedded = ['tags']
    static hasMany = [comments: Comment]
	static belongsTo = [user:SecUser]
    static constraints = {
    	slug nulllable:false
    	slug unique: 'user'
    }

     static mapping = {
        index description:"text"
    }
    
}
