package com.example.testmvi2

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.testmvi2.domain.HelloWorldViewState
import com.hannesdorfmann.mosby3.mvi.MviActivity
import com.jakewharton.rxbinding2.view.clicks
import com.jakewharton.rxbinding2.view.visibility
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : MviActivity<HelloWorldView, HelloWorldPresenter>(), HelloWorldView {
    //private val swipeRefresh: SwipeRefreshLayout? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }



    override fun createPresenter() = HelloWorldPresenter()


    override fun sayHelloWorldIntent() = helloWorldButton.clicks()

    override fun render(state: HelloWorldViewState) {
        when(state){
            is HelloWorldViewState.LoadingState -> renderLoadingState()
            is HelloWorldViewState.DataState -> renderDataState(state)
            is HelloWorldViewState.ErrorState -> renderErrorState(state)
        }
    }

    private fun renderLoadingState(){
        loadingIndicator.visibility = View.VISIBLE
        helloWorldTextview.visibility = View.GONE
    }

    private fun renderDataState(dataState: HelloWorldViewState.DataState){
        loadingIndicator.visibility = View.GONE
        helloWorldTextview.apply {
            visibility = View.VISIBLE
            text = dataState.greeting
        }
    }

    private fun renderErrorState(errorState: HelloWorldViewState.ErrorState) {
        loadingIndicator.visibility = View.GONE
        helloWorldTextview.visibility = View.GONE
        Toast.makeText(this, "error ${errorState.error}", Toast.LENGTH_LONG).show()
    }
}