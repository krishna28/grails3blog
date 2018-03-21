
package com.sample.api

import grails.plugin.springsecurity.rest.token.storage.jwt.JwtTokenStorageService

import org.springframework.beans.factory.annotation.Autowired
import  grails.plugin.springsecurity.rest.JwtService

class CustomJwtTokenStorageService extends JwtTokenStorageService{

@Autowired
JwtService jwtService



} 