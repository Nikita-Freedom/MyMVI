package com.example.testmvi2

import com.example.testmvi2.data.HelloWorldRepository
import com.example.testmvi2.domain.HelloWorldInteractor
import com.example.testmvi2.domain.HelloWorldViewState
import com.hannesdorfmann.mosby3.mvi.MviBasePresenter
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit

class HelloWorldPresenter: MviBasePresenter<HelloWorldView, HelloWorldViewState>() {
    /**
     * Эта функция bindIntents() связующее звено между View и доменной частью.
     * Она получает действие от пользователя, сравнивает его с состоянием, подписываясь на него.
     * Преобразует состояние в нужный тип в зависимости от переданного действия с помощью метода getHelloWorldText(), который в свою очередь лезет в наши данные(Repository)
     * Возвращает различные состояния пользовательского интерфейса в зависимости от последней(актуальной версии) helloWorldState
     */
    override fun bindIntents() {
        // Получаем intent(действие) от View и записываем в переменную helloWorldState и сопоставляем с состоянием(Observable<HelloWorldViewState>)
        // Определяет как намерения обрабатываются бизнес логикой
        val helloWorldState: Observable<HelloWorldViewState> = intent(HelloWorldView::sayHelloWorldIntent) // переменная helloWorldState должна иметь тип как раз испускаемого состояния
            .subscribeOn(Schedulers.io()) // Подписываем View на поток из состояний Observable<HelloWorldViewState>
            .debounce(100, TimeUnit.MILLISECONDS) // Чтобы избежать быстрой последовательности нажатия кнопок
            .switchMap { getHelloWorldText() } // Сопоставляет эти intent с доменным уровнем. вызывает уровень домена для получения новой модели
            .observeOn(AndroidSchedulers.mainThread())
        // Визуализирую каждое испущенное состояние (состояние загрузки, состояние возвращенных данных или состояние ошибки) в пользовательский интерфейс.
        subscribeViewState(helloWorldState, HelloWorldView::render)
    }


    /** Перенес эту функцию в Presenter, чтобы убедиться в том, что использовать object(а он же singleton) необязательно, но является хорошим тоном
     *
     *
     *
     * /**
     * Эта функция генерирует новое состояние модели на основе данных из Repository:
     * Либо состояние показа данных .map<HelloWorldViewState> { HelloWorldViewState.DataState(it) }
     * Либо состояие загрузки (начало загрузки) .startWith(HelloWorldViewState.LoadingState)
     * Либо состояние ошибки .onErrorReturn { HelloWorldViewState.ErrorState(it) }
    */
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