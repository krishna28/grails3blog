package com.sample.api

// import groovy.transform.EqualsAndHashCode

// @EqualsAndHashCode
import grails.rest.*
@Resource()
class Skill {
	static mapWith = "mongo"
    String name
    Integer yearsOfExp
    BigDecimal rating

    static constraints = {
    	// rating nullable:true
    }
}
