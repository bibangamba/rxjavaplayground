package observable;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.schedulers.Schedulers;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.FutureTask;

import static java.lang.System.out;

public class FutureObservableCreation {
    public static void main(String[] args) {
        Observable<List<Integer>> observableFutureIntegerList;

        FutureTask<List<Integer>> listFutureTask = new FutureTask<>(() ->
                Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11)
        );
        observableFutureIntegerList = Observable.fromFuture(listFutureTask);

        Schedulers.computation().scheduleDirect(listFutureTask);

        out.println("--------------------------------- Printing out integers received from observable future task ---------------------------------");
        observableFutureIntegerList.subscribe((integers) -> {
            integers.forEach(integer -> out.println("futureInt value: " + integer));
        });
    }

}
