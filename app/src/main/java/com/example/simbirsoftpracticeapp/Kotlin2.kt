package com.example.simbirsoftpracticeapp

import android.util.Log
import java.lang.Exception
import java.util.*


// 1
enum class Type {
    DEMO,
    FULL
}

// 2
data class User(
    val id: Int,
    val name: String,
    val age: Int,
    val type: Type
) {

    val startTime: Date by lazy {
        Calendar.getInstance().time
    }
}

class UserTest {

    val user = User(7, "User7", 20, Type.FULL)

    fun userTestMain() {

        // 3
        val user = User(0, "Rustam", 20, Type.DEMO)
        Log.e("", user.startTime.toString())
        Thread.sleep(1_000)
        Log.e("", user.startTime.toString())

        // 4
        val users = MutableList(1) { User(1, "User1", 20, Type.DEMO) }.apply {
            add(User(1, "User1", 20, Type.FULL))
            add(User(2, "User2", 20, Type.DEMO))
            add(User(3, "User3", 20, Type.FULL))
            add(User(4, "User4", 20, Type.DEMO))
            add(User(5, "User5", 20, Type.FULL))
            add(User(6, "User6", 20, Type.DEMO))
        }

        // 5
        val userWithFullAccess = users.filter { user -> user.type == Type.FULL }

        // 6
        val usersNames  = users.map { user -> user.name }

        Log.e("", "First user is ${usersNames.first()}, last one is ${usersNames.last()}")

    }

    // 8
    val authCallback = object : AuthCallback{
        override fun authSuccess() {
            Log.e("", "Auth is success")
        }

        override fun authFailed() {
            Log.e("", "Auth is failed")
        }
    }

    // 9
    inline fun auth(updateCache: () -> Unit){
        try {
            user.checkAge()
            authCallback.authSuccess()
            updateCache.invoke()
        } catch (e: Exception){
            authCallback.authFailed()
        }
    }

    fun updateCache(){
        Log.e("", "Cache has been updated")
    }


    // 12
    fun doAction(action: Action){
        when (action){
            Registration -> Log.e("", "Registration")
            is Login -> auth { updateCache() }
            Logout -> Log.e("", "Logout")
        }
    }
}

// 7
fun User.checkAge(){
    if (this.age >= 18){
        Log.e("", this.toString())
    } else{
        throw Exception("User is under 18 y.o.")
    }
}

// 8

interface AuthCallback{

    fun authSuccess()

    fun authFailed()
}

// 11

sealed class Action{

}

object Registration : Action() {

}

class Login(
    private val user: User
) : Action(){

}

object Logout : Action()
