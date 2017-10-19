package com.sample.api

import groovy.transform.EqualsAndHashCode
import groovy.transform.ToString
import grails.compiler.GrailsCompileStatic


class Comment {
	static mapWith = "mongo"
	String id
	String content
	static belongsTo = [cuser:SecUser,post:Post]
    static constraints = {
    }
}
