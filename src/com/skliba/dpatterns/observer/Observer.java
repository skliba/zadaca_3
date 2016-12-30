package com.skliba.dpatterns.observer;

import com.skliba.dpatterns.mvc.model.Diver;

public interface Observer {

    void update(Diver diver, int diveDepth);
}
