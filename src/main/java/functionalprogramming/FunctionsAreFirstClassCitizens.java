package functionalprogramming;

import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * this means that functions can be stored in variables and passed as parameters
 */
public class FunctionsAreFirstClassCitizens {
    public static void main(String[] args) {
        BiFunction<String, String, String> concatenationFunction;

        /*
            inline assign function
         */
        concatenationFunction = (s, t) -> s + t;
        System.out.println(concatenationFunction.apply("hello", " world 1"));

        /*
            assigning a static class method to a variable
         */
        concatenationFunction = FunctionsAreFirstClassCitizens::concat1;
        System.out.println(concatenationFunction.apply("hello", " world 2"));

        concatenationFunction = new FunctionsAreFirstClassCitizens()::concat2;
        System.out.println(concatenationFunction.apply("hello", " world 3"));

        concatenationFunction = new FunctionsAreFirstClassCitizens()::concat2;
        Function<String, String> transformUpper = String::toUpperCase;
        Function<String, String> transformLower = String::toLowerCase;

        System.out.println(transformAndConcatenate("Hello ", "World! 1", transformUpper, concatenationFunction));
        System.out.println(transformAndConcatenate("Hello ", "World! 2", transformLower, concatenationFunction));

        Supplier<String> highOrderFunctionTransformer = HighOrderFunctions.concatAndTransform("Hello ",
                "world from a high order function", transformUpper);


        Supplier<String> highOrderFunctionTransformer2 = () -> HighOrderFunctions.concatAndTransform2("Hello ",
                "world from a high order function", transformLower);

        System.out.println(highOrderFunctionTransformer.get());
        System.out.println(highOrderFunctionTransformer2.get());


    }

    private static String transformAndConcatenate(String a, String b,
                                                  Function<String, String> transform,
                                                  BiFunction<String, String, String> concatenate) {
        return concatenate.apply(transform.apply(a), transform.apply(b));
    }

    private static String concat1(String one, String two) {
        return one + two;
    }

    private String concat2(String one, String two) {
        return one + two;
    }

    /**
     * functions that take functions as parameters or return functions
     */
    static class HighOrderFunctions {
        static Supplier<String> concatAndTransform(String a, String b, Function<String, String> transformer) {
            return () -> transformer.apply(a) + transformer.apply(b);
        }

        static String concatAndTransform2(String a, String b, Function<String, String> transformer) {
            return transformer.apply(a) + transformer.apply(b);
        }
    }
}
