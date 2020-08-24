package org.example.demo.statemachine.action;


import org.example.demo.statemachine.event.PurchaseEvent;
import org.example.demo.statemachine.state.PurchaseState;
import org.springframework.statemachine.StateContext;
import org.springframework.statemachine.action.Action;

public class ErrorAction implements Action<PurchaseState, PurchaseEvent> {
    @Override
    public void execute(final StateContext<PurchaseState, PurchaseEvent> context) {
        System.out.println("Ошибка при переходе в статус " + context.getTarget().getId());
    }
}
