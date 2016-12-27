package com.skliba.dpatterns.visitor;

public interface Visitable {
    void accept(Visitor visitor);
}
