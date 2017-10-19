package api
import com.sample.api.*

class BootStrap {

    def init = { servletContext ->

     def adminRole = new SecRole(authority: 'ROLE_ADMIN')
     def userRole = new SecRole(authority: 'ROLE_USER')
     new SecUser(username: 'user', password: 'password',
                email: 'testuser@email.com', authorities: [userRole]).save(flush:true)
     new SecUser(username: 'admin', password: 'password',
                email: 'admin@email.com', authorities: [adminRole,userRole]).save(flush:true)

    }
    def destroy = {
    }
}
