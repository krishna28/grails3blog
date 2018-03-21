package com.sample.api

import grails.validation.ValidationException
import static org.springframework.http.HttpStatus.*
import grails.plugin.springsecurity.annotation.Secured
import javax.servlet.http.HttpServletResponse
class PostController {

    PostService postService
    def springSecurityService
    def slugify
    static responseFormats = ['json', 'xml']
    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]


    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        println "count is ${postService.count()}"
        SecUser user = springSecurityService.currentUser
        String userid = user.id
        params.SecUserId = userid
        println "params is ${params}"
        if(user){
            respond ([postList: postService.list(params) , totalCount: postService.getPostCountByUser(userid), max:params.max])
        }else{ 
            response.sendError HttpServletResponse.SC_UNAUTHORIZED         
        }
        
    }

    def show(String id) {
        println "params is single page view ${params}"
        // String userid = params.SecUserId
        SecUser secUser = springSecurityService.currentUser
        String userid = secUser.id
        println "post details ${userid}" + postService.getPostByUserId(id,userid)
        // def user = SecUser.where{
        //     id == userid
        // }.find()

        // Post.metaClass.methods.each{
        //     println it
        // }

        respond postService.getPostByUserId(id,userid)
    }

    def save(Post post) {
        if (post == null) {
            render status: NOT_FOUND
            return
        }

        try {
            post.user = springSecurityService.currentUser
            post.slug = slugify.makeSlug(post.title)
            post.isPosted = false
            postService.save(post)
        } catch (ValidationException e) {
            respond post.errors, view:'create'
            return
        }

        respond post, [status: CREATED, view:"show"]
    }

    def update(Post post) {
        println "yes insde the put request"
        // def user = springSecurityService.currentUser
        // def userpost = user.posts.find{it.id == params.id}
        // if(!userpost){
          // response.sendError HttpServletResponse.SC_UNAUTHORIZED
          // return false
        // }
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
