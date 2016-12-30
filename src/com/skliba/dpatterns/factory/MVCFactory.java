package com.skliba.dpatterns.factory;

import com.skliba.dpatterns.mvc.controller.TerminalController;
import com.skliba.dpatterns.mvc.controller.TerminalControllerImpl;
import com.skliba.dpatterns.mvc.view.TerminalView;

public class MVCFactory {

    public static TerminalController getController(TerminalView view) {
        return new TerminalControllerImpl(view);
    }
}
