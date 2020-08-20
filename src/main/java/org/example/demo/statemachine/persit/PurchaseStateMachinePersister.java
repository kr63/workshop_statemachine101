package org.example.demo.statemachine.persit;

import org.example.demo.statemachine.event.PurchaseEvent;
import org.example.demo.statemachine.event.PurchaseState;
import org.springframework.statemachine.StateMachineContext;
import org.springframework.statemachine.StateMachinePersist;

import java.util.HashMap;

public class PurchaseStateMachinePersister implements StateMachinePersist<PurchaseState, PurchaseEvent, String> {

    private final HashMap<String, StateMachineContext<PurchaseState, PurchaseEvent>> contexts = new HashMap<>();

    @Override
    public void write(final StateMachineContext<PurchaseState, PurchaseEvent> context, final String contextObj) {
        contexts.put(contextObj, context);
    }

    @Override
    public StateMachineContext<PurchaseState, PurchaseEvent> read(final String contextObj) {
        return contexts.get(contextObj);
    }
}
