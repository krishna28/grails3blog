package com.sample.api

import grails.gorm.services.Service


interface CommentServiceI {

    Comment get(Serializable id)

    List<Comment> list(Map args)

    Long count()

    void delete(Serializable id)

    Comment save(Comment comment)

    Comment getCommentByPostId(Serializable id,Serializable postid)

    Long getCommentCountByPost(Serializable postid)

}

@Service(Comment)
abstract class CommentService implements CommentServiceI{
	@Override
	List<Comment> list(Map args){
		println "yes inside the grails comments tag ${args}"
        args.sort = args.sort?: "dateCreated"
        args.order = args.order?: "desc"
        String postId = args.PostId
    	Post post = Post.where{
    		id == postId
    	}.find()
    	println "post is ${post}"
    	List<Comment> result = Comment.findAllByPost(post,args)
    	println "result is ${result}"
        result
	}

    @Override
    Long getCommentCountByPost(Serializable postid){
        Post post = Post.where{
            id == postid
        }.find()
        long commentCount = Comment.countByPost(post)
        commentCount
    }

	@Override
	Comment getCommentByPostId(Serializable id,Serializable postid){
        Post postInstance = Post.where{
            id == postid
        }.find()
        Comment comment = Comment.where{
            id == id && post == postInstance
        }.find()
        comment
	}
}