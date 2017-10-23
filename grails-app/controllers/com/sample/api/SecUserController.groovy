package com.sample.api

import grails.validation.ValidationException
import static org.springframework.http.HttpStatus.*
import javax.servlet.http.HttpServletResponse

class SecUserController {

    SecUserService secUserService
    def springSecurityService

    static responseFormats = ['json', 'xml']
    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        println secUserService.list(params)
        println "secUserCount: ${secUserService.count()}"
        // respond [secUserService.list(params), model:[name: "krishna",secUserCount: secUserService.count()]]
        
        def totalCount = secUserService.count()

        respond ([secUserList : secUserService.list(params), totalCount: secUserService.count(), max: params.max])

    }

    def show(String id) {
        println "show action called with id ${id} and instance ${secUserService.get(id)}"
        respond secUserService.get(id)
    }

    def save(SecUser secUser) {
        if (secUser == null) {
            render status: NOT_FOUND
            return
        }

        try {
            secUserService.save(secUser)
        } catch (ValidationException e) {
            respond secUser.errors, view:'create'
            return
        }

        respond secUser, [status: CREATED, view:"show"]
    }

    def update(SecUser secUser) {
        if (secUser == null) {
            render status: NOT_FOUND
            return
        }

        try {
            secUserService.save(secUser)
        } catch (ValidationException e) {
            respond secUser.errors, view:'edit'
            return
        }

        respond secUser, [status: OK, view:"show"]
    }

    def delete(String id) {
       def user = springSecurityService.currentUser
       def role = new SecRole(authority:'ROLE_ADMIN')
       if(!user.authorities.contains(role)){
        response.sendError HttpServletResponse.SC_UNAUTHORIZED
        return false
       }
        if (id == null) {
            render status: NOT_FOUND
            return
        }

        secUserService.delete(id)

        render status: NO_CONTENT
    }
}
