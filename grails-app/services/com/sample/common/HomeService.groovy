package com.sample.common

import grails.gorm.transactions.Transactional
import grails.gorm.services.Service
import com.sample.api.Post
import com.sample.api.Comment

import com.mongodb.client.FindIterable
import static com.mongodb.client.model.Filters.*


interface HomeServiceI {

    Post get(Serializable id)

    List<Post> list(Map args)

    Long count()

}
@Service(Post)
abstract class HomeService implements HomeServiceI{

	@Override
	List<Post> list(Map args){
    	List<Post> result = Post.findAll(args)
        result
	}

  Post getPostWithComments(Serializable id,Map args){
          // Post.get(id)
          println Post.find(eq('_id',id)).first()
          return Post.find(eq('_id',id)).first()
  }
  

  def getAllPosts(Map args){
     def c = Post.createCriteria()
     String defaultSort = args.sort?: "dateCreated"
     String defaultOrder = args.order?: "desc"
      def results = c.list (max: args.max, offset: args.offset) {          
          order(defaultSort, defaultOrder)
      }
  }


  def getCommentByPost(Serializable id,Map args){
      Post post = Post.where{
        id == id
      }.find()
      args.max = args.max as Integer
      if(!args.sort && !args.order){
        args.sort = "dateCreated"
        args.order = "desc"
      }
      List<Comment> result = Comment.findAllByPost(post,args)
      [result:result, post:post]
  }

}