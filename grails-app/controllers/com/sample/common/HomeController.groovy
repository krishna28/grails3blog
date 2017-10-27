package com.sample.common


import grails.rest.*
import grails.converters.*

class HomeController {
	static responseFormats = ['json', 'xml']
	
    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond ([postList: postService.list(params) , totalCount: postService.getPostCountByUser(params.SecUserId), max:params.max])
    }
}
