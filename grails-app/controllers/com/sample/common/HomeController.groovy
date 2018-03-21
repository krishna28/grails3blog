package com.sample.common

import com.sample.api.*
import com.sample.common.HomeService
import grails.rest.*
import grails.converters.*
import grails.validation.ValidationException
import static org.springframework.http.HttpStatus.*

class HomeController {
	static responseFormats = ['json', 'xml']
    SecUserService secUserService
	HomeService homeService
    def springSecurityService
    def index(Integer max) {
         def user = springSecurityService.currentUser
         // println "user is ${user} >>>>>>>>>> ${tokenStorageService}"

        params.max = Math.min(max ?: 10, 100)
        println "posts are" + homeService.list(params)
        respond ([postList: homeService.list(params) , totalCount: homeService.count(), max:params.max])
    }

    def show(String id) {
        Integer maxp = params.max as Integer
    	int max = Math.min(maxp ?: 10, 100)
    	// println "post instance " + homeService.getPostWithComments(id,params)
    	// println "post instance " + homeService.getPostWithComments(id,params).comments
    	// Set<Comment> comments = homeService.getPostWithComments(id,params).comments
        def result = homeService.getCommentByPost(id,params)
       // println "id so " +  comments.sort({a,b-> a.content <=> b.content})
    	// println "comment sare kkkkkkkkkkkkkkkkkkkkkk" + comments.first().cuser
        respond ([post:result.post,commentList:result.result,totalCount: result.totalCount, max:params.max])
    }

    def posts(Integer max) {
        def user = springSecurityService.currentUser
         // println "user is ${user} ${tokenStorageService}"
         println "user token is " + request.getHeader("authorization")
        // request.getHeaderNames().each {        
        //  println(it+":"+request.getHeader("authorization"))
        // }
        // println tokenStorageService.loadUserByToken(request.getHeader("authorization"));
        params.max = Math.min(max ?: 10, 100)
        
        def result = homeService.getAllPosts(params)
        println "result is ${result}"
        respond ([postList: result, totalCount: result.totalCount, max:params.max])
    }

    List<Post> search(String search_term){
        println "search krishna = ${search_term}"
         def result = homeService.searchPost(search_term,params)
         println "posts controller = ${result.posts}"
         render(view: "index", model: [postList: result.posts, totalCount: result.count, max:params.max])
         // respond ([postList: result, totalCount: result.count, max:params.max])
    }


    List<Post> my(){
        // SecUser user = SecUser.get("5a153c9417c3661dec98c99")
        // println params
        // def res = homeService.allUserPost(user,params)
        // println "res is ${res}"

        // def c = Post.createCriteria()
        // def results = c.list {
        //     eq('user',user)
        //     order("dateCreated", "asc")
        // }
        // println  "krishna result si " + results
        // respond results
        // println params.tag
        // Integer maxp = params.max as Integer
        // int max = Math.min(maxp ?: 10, 100)
        // // def result = Post.findAllByTagsIlike(params.tag,params)
        // def result =  homeService.searchPostByTag(params.tag,params)   
        // // println Post.findAllByTags(params.tags,params)
        // println result
        def file = new File("C:/word/AddOAuthCheckingToRoutes.docx")
        def bytesResponse = file.bytes
        def m = [f:bytesResponse]
        respond m
        // respond result
    }

    List<Post> searchByTag(){
        String tag = params.tag 
        Integer maxp = params.max as Integer
        int max = Math.min(maxp ?: 25, 100)
        def result =  homeService.searchPostByTag(tag,params)   
        render(view: "index", model: [postList: result.posts, totalCount: result.count, max:max])
    }

    List<Post> searchByUser(){
        String user = params.user 
        Integer maxp = params.max as Integer
        int max = Math.min(maxp ?: 25, 100)
        def result =  homeService.searchPostByUser(user,params)   
        println "search by user uis ${result}"
        render(view: "index", model: [postList: result.posts, totalCount: result.count, max:max])
    }

    List<Comment> getAllCommentsByPost(){
        println params
        println params.PostId
        String postid = params.PostId

        respond homeService.getCommentByPost(id,postid)

    }

    def register(){
        def resObject = [:]
         SecUser user = new SecUser()
         println "yes params " +params
         println request.JSON
         user.username = params.username
         user.password = params.password
         user.email = params.username
         def userRole = new SecRole(authority: 'ROLE_USER')
         user.authorities = [userRole]
        try {
           def res= secUserService.save(user)
           println "res is ${res}"
        } catch (ValidationException e) {
            respond user.errors, view:'create'
            return
        }
        resObject['user'] = user.id

        respond resObject, [status: OK, view:"show"]
    }
}
