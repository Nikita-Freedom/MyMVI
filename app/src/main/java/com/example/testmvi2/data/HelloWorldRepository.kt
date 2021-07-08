package com.example.testmvi2.data

import io.reactivex.Observable
import java.util.*

object HelloWorldRepository {

    fun loadHelloWorldText(): Observable<String> = Observable.just(getRandomMessage())

    private fun getRandomMessage(): String {
        val messages = listOf("Hello World", "Hola Mundo", "Hallo Welt", "Bonjour le monde")
        return messages[Random().nextInt(messages.size)]
    }
}