/**
 * Apache 2
 * Copyright 2014 The Apache Software Foundation
 *
 * This product includes software developed at
 * The Apache Software Foundation (http://www.apache.org/).
 */
package utils;

/**
 * Created by Mateusz Szczap on 19/10/14.
 */
import play.libs.F;

public class Promises {

    public static <T1, T2, R> F.Promise<R> zip(final F.Promise<? extends T1> a, F.Promise<? extends T2> b, final F.Function2<? super T1, ? super T2, ? extends R> zipFunction) {
        return a.flatMap(aa -> b.map(bb -> zipFunction.apply(aa, bb)));
    }

    public static <T1, T2, T3, R> F.Promise<R> zip(final F.Promise<? extends T1> a,
                                                   final F.Promise<? extends T2> b,
                                                   final F.Promise<? extends T3> c,
                                                   final F.Function3<? super T1, ? super T2, ? super T3, ? extends R> zipFunction) {
        return a.flatMap(aa -> b.flatMap(bb -> c.map(cc -> zipFunction.apply(aa, bb, cc))));
    }

    public static <T1, T2, T3, T4, R> F.Promise<R> zip(final F.Promise<? extends T1> a,
                                                       final F.Promise<? extends T2> b,
                                                       final F.Promise<? extends T3> c,
                                                       final F.Promise<? extends T4> d,
                                                       final Function4<? super T1, ? super T2, ? super T3, ? super T4, ? extends R> zipFunction) {
        return a.flatMap(aa -> b.flatMap(bb -> c.flatMap(cc -> d.map(dd -> zipFunction.apply(aa, bb, cc, dd)))));
    }

    public static interface Function4<A,B,C,D,R> {
        public R apply(A a, B b, C c, D d) throws Throwable;
    }

}
