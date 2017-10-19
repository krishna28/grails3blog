package com.sample.api

enum UserTypeEnum{

      EMPLOYER('employer'),
      SEEKER('seeker')

      UserTypeEnum(String utype) {
		this.uType = utype
	   }
	private final String uType
	String getUType() {
		uType
	}
}