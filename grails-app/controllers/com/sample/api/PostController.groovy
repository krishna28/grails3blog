package com.sample.api

import grails.validation.ValidationException
import static org.springframework.http.HttpStatus.*

class PostController {

    PostService postService

    static responseFormats = ['json', 'xml']
    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        println "count is ${postService.count()}"
        println "params is ${params}"
        respond ([postList: postService.list(params) , postCount: postService.getPostCountByUser(params.SecUserId)])
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
            postService.save(post)
        } catch (ValidationException e) {
            respond post.errors, view:'create'
            return
        }

        respond post, [status: CREATED, view:"show"]
    }

    def update(Post post) {
        if (post == null) {
            render status: NOT_FOUND
            return
        }

        try {
            postService.save(post)
        } catch (ValidationException e) {
            respond post.errors, view:'edit'
            return
        }

        respond post, [status: OK, view:"show"]
    }

    def delete(Long id) {
        if (id == null) {
            render status: NOT_FOUND
            return
        }

        postService.delete(id)

        render status: NO_CONTENT
    }
}
