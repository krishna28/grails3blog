package com.sample.api

import grails.rest.*
@Resource()
class Tag {
    static mapWith = "mongo"
    String id
	String name
    static constraints = {
    }
}
