/*
 * Copyright (c) 2014 Mateusz Szczap
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
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
