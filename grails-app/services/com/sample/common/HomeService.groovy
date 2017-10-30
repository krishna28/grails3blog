package com.sample.common

import grails.gorm.transactions.Transactional
import grails.gorm.services.Service
import com.sample.api.Post

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

}