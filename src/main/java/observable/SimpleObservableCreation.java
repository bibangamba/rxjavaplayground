package observable;

import io.reactivex.rxjava3.core.Observable;

public class SimpleObservableCreation {
    public static void main(String[] args) {
        Observable<Integer> someObserverInteger = null;

        //observable creation from single value
        someObserverInteger = Observable.just(31);
        System.out.println("--------------------------------- Observable creation from single value ---------------------------------");
        someObserverInteger.subscribe((integer) -> {
            System.out.println("Printing observed integer: " + integer);
        });

        someObserverInteger = Observable.fromArray(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11);
        System.out.println("--------------------------------- Observable creation from single value ---------------------------------");
        someObserverInteger.subscribe(integer -> {
            System.out.println("Printing integers from observed array ---> " + integer);
        });

    }
}
