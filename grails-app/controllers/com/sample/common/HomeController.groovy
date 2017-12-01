package com.sample.common

import com.sample.api.*
import com.sample.common.HomeService
import grails.rest.*
import grails.converters.*

class HomeController {
	static responseFormats = ['json', 'xml']
	HomeService homeService
    def index(Integer max) {
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
}
