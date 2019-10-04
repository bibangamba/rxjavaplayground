package observable;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.functions.Action;
import io.reactivex.rxjava3.functions.Consumer;
import io.reactivex.rxjava3.schedulers.Schedulers;
import util.ThreadUtils;

public class HowSubscribeWorksThreadWise {
    public static void main(String[] args) {
        System.out.println("################# DRIVING THREAD: " + ThreadUtils.currentThreadName() + " #################");

        Observable<Integer> someObserverInteger = Observable.range(0, 10);
        Consumer<Integer> onNext = (integer) -> {
            System.out.println("--------------------------------- onNext() thread START: " + ThreadUtils.currentThreadName() + " ---------------------------------");
            System.out.println("Printing integers from observed array ---> " + integer);
            System.out.println("--------------------------------- onNext() thread END:   " + ThreadUtils.currentThreadName() + " ---------------------------------");

        };
        Consumer<Throwable> onError = (throwable) -> {
            System.out.println("--------------------------------- Error occurred, printing stack trace... ---------------------------------");
            throwable.printStackTrace();
        };
        Action onComplete = () -> {
            System.out.println("onCompleted()");
        };

        System.out.println("############################## DEFAULT SUBSCRIBE ##############################");
        //1. runs all onNext() call on the main thread because we don't provide an option.
        //   by default, onNext() calls will be called on the calling/driving thread (usually the main thread)
        someObserverInteger.subscribe(onNext, onError, onComplete);

        System.out.println("############################## NEW THREAD SUBSCRIBE ##############################");
        //2. runs all onNext() call on a new thread. All subsequent onNext() calls will be called on this new thread.
        //   i.e. RxJava creates a new thread, delegates the subscribe onNext() calls to that thread and frees up
        //   the MAIN thread. This is "concurrent" but not "parallel".
        someObserverInteger
                .subscribeOn(Schedulers.newThread())
                .subscribe(onNext, onError, onComplete);

        System.out.println("############################## IO THREAD POOL SUBSCRIBE ##############################");
        //3. runs all onNext() calls using threads taken out of an IO Thread pool. It reuses threads that are freed up.
        someObserverInteger
                .subscribeOn(Schedulers.io())
                .subscribe(onNext, onError, onComplete);

        System.out.println("############################## COMPUTATION THREAD POOL SUBSCRIBE ##############################");
        //4. similar to no.3 above but runs all onNext() calls using threads in the CPU cores.
        //   We're guaranteed a thread for each CPU core.
        someObserverInteger.subscribeOn(Schedulers.computation())
                .subscribe(onNext, onError, onComplete);


    }
}
