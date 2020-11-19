package com.example.mvpbase.rxEventBus

import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject


object RxBus {

    private val publisher = PublishSubject.create<Any>()

    fun publish(event: Any) {
        publisher.onNext(event)
    }

    // Listen should return an Observable and not the publisher
    // Using ofType we filter only events that match that class type
    fun <T> listen(eventType: Class<T>): Observable<T> = publisher.ofType(eventType)
}


class RxEvent private constructor() {
    private val mSubject: PublishSubject<Any> = PublishSubject.create()
    fun sendEvent(item: Any) {
        mSubject.onNext(item)
    }

    val observable: Observable<Any>
        get() = mSubject

    companion object {
        var instance = RxEvent()
    }

}
