package org.example.demo.statemachine.guard;


import org.example.demo.statemachine.event.PurchaseEvent;
import org.example.demo.statemachine.event.PurchaseState;
import org.springframework.statemachine.StateContext;
import org.springframework.statemachine.guard.Guard;

public class HideGuard implements Guard<PurchaseState, PurchaseEvent> {

    @Override
    public boolean evaluate(StateContext<PurchaseState, PurchaseEvent> context) {
        return false;
    }
}
