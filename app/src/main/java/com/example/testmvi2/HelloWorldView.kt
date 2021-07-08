package com.example.testmvi2

import com.example.testmvi2.domain.HelloWorldViewState
import com.hannesdorfmann.mosby3.mvp.MvpView
import io.reactivex.Observable

interface HelloWorldView: MvpView {
    /**
     * Выдает щелчки по кнопкам как наблюдаемые. Получаем действие от нашего пользователя
     */
    fun sayHelloWorldIntent(): Observable<Unit>

    /**
     * Визуализирует состояние в пользовательском интерфейсе
     */
    fun render(state: HelloWorldViewState)
}