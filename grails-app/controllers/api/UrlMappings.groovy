package api

class UrlMappings {

    static mappings = {
        delete "/$controller/$id(.$format)?"(action:"delete")
        get "/$controller(.$format)?"(action:"index")
        get "/$controller/$id(.$format)?"(action:"show")
        post "/$controller(.$format)?"(action:"save")
        put "/$controller/$id(.$format)?"(action:"update")
        patch "/$controller/$id(.$format)?"(action:"patch")

         "/$controller/$action?/$id?"{
            constraints {
                // apply constraints here
            }
        }
        
        "/api/user"(resources:"SecUser"){
            "/posts"(resources:"Post"){
                "/comments"(resources:"Comment")
                "/tags"(resources:"Tag")
            }
        }
        "/api/cmn/posts"(controller:'home', action:'index', method:'GET')
        "/cmn/posts"(controller:'home', action:'posts', method:'GET')
        "/cmn/posts/mytest"(controller:'home', action:'my', method:'GET')
        "/cmn/post/search/tag"(controller:'home', action:'searchByTag', method:'GET')
        "/cmn/post/search/user"(controller:'home', action:'searchByUser', method:'GET')
        "/cmn/comment/post/$id"(controller:'home', action:'show', method:'GET')
        "/cmn/all/posts"(controller:'home', action:'posts', method:'GET')
        "/cmn/register"(controller:'home', action:'register', method:'POST')
        "/blog/$user/$year/$month/$day/$id"(controller: 'home', action: 'show', method:'GET')
        "/all"(resources:"Home")
        "/all/search"(controller:"home", action:'search', method: 'GET') 
        "/api/tag"(resources:"Tag")
        "/api/skill"(resources:"Skill")
        "/"(controller: 'application', action:'index')
        "500"(view: '/error')
        "404"(view: '/notFound')
    }
}
