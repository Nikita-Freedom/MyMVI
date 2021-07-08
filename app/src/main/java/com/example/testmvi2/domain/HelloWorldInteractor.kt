package com.example.testmvi2.domain

import com.example.testmvi2.data.HelloWorldRepository
import io.reactivex.Observable

/**
 * Для простоты мы сделали наш класс HelloWorldInteractor в единичном экземпляре, т.к проект маленький
 * В больших проектах лучше внедрять этот класс в Presenter
 */

object HelloWorldInteractor {

    /**
     * Эта функция генерируетновое состояние модели на основе данных из Repository:
     * Либо состояние показа данных .map<HelloWorldViewState> { HelloWorldViewState.DataState(it) }
     * Либо состояие загрузки (начало загрузки) .startWith(HelloWorldViewState.LoadingState)
     * Либо состояние ошибки .onErrorReturn { HelloWorldViewState.ErrorState(it) }
     */

    fun getHelloWorldText(): Observable<HelloWorldViewState>{

        // Вызов репозитория для получения данных
        return HelloWorldRepository.loadHelloWorldText()

            //Создаем DataState и преобразуем его в HelloWorldViewState

            .map<HelloWorldViewState> {
                HelloWorldViewState.DataState(it)  // сопоставляем полученные данные от HelloWorldRepository.loadHelloWorldText() с DataState(состонием)
            }

            // Передача значения LoadingState перед отправкой данных
            .startWith(HelloWorldViewState.LoadingState)

            // Не выдавать ошибку - вместо этого генерировать ErrorState
            .onErrorReturn {
                HelloWorldViewState.ErrorState(it)
            }

    }
}