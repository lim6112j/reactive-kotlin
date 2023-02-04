/*
 * This Kotlin source file was generated by the Gradle 'init' task.
 */
package reactive.kotlin

import exampleOf
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.kotlin.subscribeBy
import io.reactivex.rxjava3.disposables.CompositeDisposable
import kotlin.math.roundToInt
class App {
    val greeting: String
        get() {
            return "Hello World!"
        }
}

fun main() {
    println(App().greeting)
    exampleOf("just") {
      val observable = Observable.just(1)
      val observable2 = Observable.just(1,2,3)
    }
    exampleOf("list") { 
      val observable = Observable.just(listOf(1,2,3))
    }
    exampleOf("fromIterable") {
      val observable = Observable.fromIterable(listOf(1,2,3))
    }
    exampleOf("subscribe") {
      val observable = Observable.just(1,2,3)
      observable.subscribe {println(it)}
    }
    exampleOf("empty") {
      val observable = Observable.empty<Unit>()
      observable.subscribeBy(
        onNext = {println(it)},
        onComplete = {println("completed")}
      )
    }
    // exampleOf("never") {
    //   val observable = Observable.never<Any>()
    //   observable.subscribeBy() { 
    //     onNext = {println(it)}, 
    //     onComplete = {println("completed")} }
    // }
    exampleOf("range") {
      val observable: Observable<Int> = Observable.range(1, 10)
      observable.subscribe {
        val n = it.toDouble()
        val fibo = ((Math.pow(1.61803, n)-Math.pow(0.61803, n)) / 2.23606).roundToInt()
        println(fibo)
      }
    }
    exampleOf("dispose") {
      val mostPopular: Observable<String> = Observable.just("A", "B", "C")
      val subscription = mostPopular.subscribe {
        println(it)
      }
      subscription.dispose()
    }
    exampleOf("compositeDisposables") {
      val subscriptions = CompositeDisposable()
      val disposable = Observable.just("A", "B", "C")
          .subscribe {
            println(it)
          }
      subscriptions.add(disposable)
    }
    exampleOf("create") {
      val disposables = CompositeDisposable()
      Observable.create<String> { emitter ->
        emitter.onNext("1")
        emitter.onComplete()
        emitter.onNext("2")
      }
      .subscribeBy (
        onNext = {println(it)},
        onComplete = {println("Completed")},
        onError = {println(it)}
      )
    }

    exampleOf("defer") {
      val disposables = CompositeDisposable()
      var flip = false
      val factory: Observable<Int> = Observable.defer {
        flip = !flip
        if (flip) {
          Observable.just(1,2,3)
        } else {
          Observable.just(4,5,6)
        }
      }
      for(i in 0..3) {
        disposables.add(
          factory.subscribe {
            println(it)
          }
        )
      }
    }
}
