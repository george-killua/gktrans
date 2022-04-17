package com.killua.extenstions

data class UserNotFoundException(val messageInit: String = "No User Found To complete the action") :
    Exception(messageInit)