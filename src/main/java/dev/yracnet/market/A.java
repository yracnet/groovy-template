/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dev.yracnet.market;

import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Stream;

/**
 *
 * @author yracnet
 */
public interface A<T> {
    
    <R> Stream<R> mapper(Function<? super T, ? extends R> mapper);
    
}
