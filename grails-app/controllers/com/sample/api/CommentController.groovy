package com.sample.api

import grails.validation.ValidationException
import static org.springframework.http.HttpStatus.*

class CommentController {

    CommentService commentService
    def springSecurityService

    static responseFormats = ['json', 'xml']
    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        println "params is ${params}"
        // println "comment count is ${commentService.getCommentCountByPost(params.PostId)}"
        respond ([commentList: commentService.list(params),totalCount: commentService.getCommentCountByPost(params.PostId), max: params.max])
    }

    def show(String id) {
        println params
        println params.PostId
        String postid = params.PostId
        // Post user = SecUser.where{
        //     id == userid
        // }.find()

        respond commentService.getCommentByPostId(id,postid)
        // respond commentService.get(id)
    }

    def save(Comment comment) {
        if (comment == null) {
            render status: NOT_FOUND
            return
        }

        try {
            commentService.save(comment)
        } catch (ValidationException e) {
            respond comment.errors, view:'create'
            return
        }

        respond comment, [status: CREATED, view:"show"]
    }

    def update(Comment comment) {
        if (comment == null) {
            render status: NOT_FOUND
            return
        }

        try {
            commentService.save(comment)
        } catch (ValidationException e) {
            respond comment.errors, view:'edit'
            return
        }

        respond comment, [status: OK, view:"show"]
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

        commentService.delete(id)

        render status: NO_CONTENT
    }
}
