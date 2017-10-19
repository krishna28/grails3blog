package com.sample.api

import groovy.transform.EqualsAndHashCode
import groovy.transform.ToString
import grails.compiler.GrailsCompileStatic

@GrailsCompileStatic
@EqualsAndHashCode(includes='username')
@ToString(includes='username', includeNames=true, includePackage=false)
class SecUser implements Serializable {
static mapWith = "mongo"
    private static final long serialVersionUID = 1
    String id
    String username
    String password
    String email
    boolean enabled = true
    boolean accountExpired
    boolean accountLocked
    boolean passwordExpired

    Set<SecRole> authorities // A Set for storing role
    static hasMany = [posts: Post]
    static embedded = ['authorities'] // mongodb supports embedded document

    Set<SecRole> getAuthorities() {
        this.authorities // use this code
    }

    // Set<SecRole> getAuthorities() {
    //     (SecUserSecRole.findAllBySecUser(this) as List<SecUserSecRole>)*.secRole as Set<SecRole>
    // }

    static constraints = {
        password nullable: false, blank: false, password: true
        username nullable: false, blank: false, unique: true
        email nullable: false, blank: false, unique: true
    }

    static mapping = {
	    password column: '`password`'
    }
}
