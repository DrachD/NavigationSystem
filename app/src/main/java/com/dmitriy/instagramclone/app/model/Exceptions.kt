package com.dmitriy.instagramclone.app.model

class EmptyFieldException : RuntimeException()

class PasswordMismatchException : RuntimeException()

class AccountAlreadyExistsException(cause: Throwable) : RuntimeException(cause)

class BackendException(val code: Int, message: String) : RuntimeException(message)

class ConnectionException(cause: Throwable) : RuntimeException(cause)

class MissingPassword(cause: Throwable) : RuntimeException(cause)

data class ExceptionInfo(
    val code: Int?,
    val message: String?,
    val cause: Throwable?,
    val screenMassage: String?
)