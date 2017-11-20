package com.sample.api

import grails.validation.ValidationException
import static org.springframework.http.HttpStatus.*
import grails.plugin.springsecurity.annotation.Secured
class PostController {

    PostService postService
    def springSecurityService
    static responseFormats = ['json', 'xml']
    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]


    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        println "count is ${postService.count()}"
        println "params is ${params}"
        def user = springSecurityService.currentUser
        if(user.id == params.SecUserId){
            respond ([postList: postService.list(params) , totalCount: postService.getPostCountByUser(params.SecUserId), max:params.max])
        }else{ 
            response.sendError HttpServletResponse.SC_UNAUTHORIZED         
        }
        
    }

    def show(String id) {
        String userid = params.SecUserId
        // def user = SecUser.where{
        //     id == userid
        // }.find()

        respond postService.getPostByUserId(id,userid)
    }

    def save(Post post) {
        if (post == null) {
            render status: NOT_FOUND
            return
        }

        try {
            post.user = springSecurityService.currentUser
            postService.save(post)
        } catch (ValidationException e) {
            respond post.errors, view:'create'
            return
        }

        respond post, [status: CREATED, view:"show"]
    }

    def update(Post post) {
        def user = springSecurityService.currentUser
        def userpost = user.posts.find{it.id == params.id}
        if(!userpost){
          response.sendError HttpServletResponse.SC_UNAUTHORIZED
          return false
        }
        if (post == null) {
            render status: NOT_FOUND
            return
        }

        try {
            println "pos t is ${post}"
            println "params ${params}"
            post.user = springSecurityService.currentUser
            postService.save(post)
        } catch (ValidationException e) {
            respond post.errors, view:'edit'
            return
        }

        respond post, [status: OK, view:"show"]
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

        postService.delete(id)

        render status: NO_CONTENT
    }
}
