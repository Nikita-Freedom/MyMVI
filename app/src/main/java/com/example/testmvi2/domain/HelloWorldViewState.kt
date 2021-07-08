package com.example.testmvi2.domain

/**
 * Класс описания состояния View.
 * В данном случае наше View может пребывать в 3-х абстрактных состояниях:
 * 1. Загрузки
 * 2. Отображения данных
 * 3. Ошибки загрузки данных
 * data class используется для наших объектов, с которыми мы оперируем и которые используем(напр. greeting - наши приветствия)
 * data class - данные
 * object - состояние
 */


sealed class HelloWorldViewState{
    object LoadingState: HelloWorldViewState()
    data class DataState(val greeting: String): HelloWorldViewState()
    data class ErrorState(val error: Throwable): HelloWorldViewState()
}
