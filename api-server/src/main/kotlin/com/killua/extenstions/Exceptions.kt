package com.killua.extenstions

data class UserNotFoundException(val messageInit: String = "No User Found To complete the action") :
    Exception(messageInit)
data class UserInfoExption(val messageInit: String = "No User Found To complete the action") :
    Exception(messageInit)