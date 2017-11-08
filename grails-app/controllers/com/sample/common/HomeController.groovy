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
    	int max = Math.min(params.max ?: 10, 100)
    	println "post instance " + homeService.getPostWithComments(id,params)
    	println "post instance " + homeService.getPostWithComments(id,params).comments
    	Set<Comment> comments = homeService.getPostWithComments(id,params).comments
       // println "id so " +  comments.sort({a,b-> a.content <=> b.content})
    	println "comment sare kkkkkkkkkkkkkkkkkkkkkk" + comments.first().cuser
        respond ([post:homeService.getPostWithComments(id,params),commentList:comments,totalCount: homeService.count(), max:params.max])
    }

    def posts(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        
        def result = homeService.getAllPosts(params)
        println "result is ${result}"
        respond ([postList: result, totalCount: result.totalCount, max:params.max])
    }
}
