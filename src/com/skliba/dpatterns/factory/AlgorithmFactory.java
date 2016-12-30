package com.skliba.dpatterns.factory;

import com.skliba.algorithms.Algorithm;
import com.skliba.algorithms.MaxPartnerAlgorithm;
import com.skliba.algorithms.RandomAlgorithm;

public class AlgorithmFactory implements AlgorithmInjector {

    public static final String MAX_PARTNER = "MaxPartner";

    public static final String RANDOM = "Random";

    @Override
    public Algorithm inject(String algorithmNick, int seedInt) {

        switch (algorithmNick) {
            case MAX_PARTNER:
                return new MaxPartnerAlgorithm();
            case RANDOM:
                return new RandomAlgorithm(seedInt);
            default:
                throw new IllegalArgumentException("You have tried to start an algorithm that isn't implemented at all: " + algorithmNick);
        }
    }
}
