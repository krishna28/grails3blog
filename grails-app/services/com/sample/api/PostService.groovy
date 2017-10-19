package com.sample.api

import grails.gorm.services.Service


interface PostServiceI {

    Post get(Serializable id)

    List<Post> list(Map args)

    Long count()

    void delete(Serializable id)

    Post save(Post post)

    Post getPostByUserId(Serializable id,Serializable userid)

    Long getPostCountByUser(Serializable userid)

}

@Service(Post)
abstract class PostService implements PostServiceI{
	@Override
	List<Post> list(Map args){
    String userid = args.SecUserId
    SecUser user = SecUser.where{
    		id == userid
    	}.find()
    	List<Post> result = Post.findAllByUser(user,args)
        result
	}

    @Override
    Long getPostCountByUser(Serializable userid){
    SecUser user = SecUser.where{
            id == userid
        }.find()
        long postCount = Post.countByUser(user)
        postCount
    }

	@Override
	Post getPostByUserId(Serializable id,Serializable userid){
        SecUser userinstance = SecUser.where{
            id == userid
        }.find()
        Post post = Post.where{
            id == id && user == userinstance
        }.find()
        post
	}
}