package com.skliba.dpatterns.visitor;

import com.skliba.dpatterns.mvc.model.Diver;
import com.skliba.dpatterns.observer.Agency;

public interface Visitor {
    void visitDiver(Diver diver);

    void visitAgency(Agency agency);
}
