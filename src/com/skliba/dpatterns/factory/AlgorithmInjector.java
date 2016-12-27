package com.skliba.dpatterns.factory;

import com.skliba.algorithms.Algorithm;

public interface AlgorithmInjector {

    Algorithm inject(String algorithmNick, int seedInt);
}
