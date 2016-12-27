package com.skliba.dpatterns.observer;

import com.skliba.dpatterns.visitor.Diver;

public interface Observer {

    void update(Diver diver, int diveDepth);
}
