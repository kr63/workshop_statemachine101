package org.example.demo.statemachine.config;

import org.example.demo.statemachine.action.BuyAction;
import org.example.demo.statemachine.action.CancelAction;
import org.example.demo.statemachine.action.ErrorAction;
import org.example.demo.statemachine.action.ReservedAction;
import org.example.demo.statemachine.event.PurchaseEvent;
import org.example.demo.statemachine.state.PurchaseState;
import org.example.demo.statemachine.guard.HideGuard;
import org.example.demo.statemachine.listener.PurchaseStateMachineApplicationListener;
import org.example.demo.statemachine.persit.PurchaseStateMachinePersister;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.statemachine.action.Action;
import org.springframework.statemachine.config.EnableStateMachineFactory;
import org.springframework.statemachine.config.EnumStateMachineConfigurerAdapter;
import org.springframework.statemachine.config.builders.StateMachineConfigurationConfigurer;
import org.springframework.statemachine.config.builders.StateMachineStateConfigurer;
import org.springframework.statemachine.config.builders.StateMachineTransitionConfigurer;
import org.springframework.statemachine.guard.Guard;
import org.springframework.statemachine.persist.DefaultStateMachinePersister;
import org.springframework.statemachine.persist.StateMachinePersister;

import java.util.EnumSet;


@Configuration
@EnableStateMachineFactory
public class StateMachineConfig extends EnumStateMachineConfigurerAdapter<PurchaseState, PurchaseEvent> {

    @Override
    public void configure(final StateMachineConfigurationConfigurer<PurchaseState, PurchaseEvent> config) throws Exception {
        config
                .withConfiguration()
                .autoStartup(true)
                .listener(new PurchaseStateMachineApplicationListener());
    }

    @Override
    public void configure(final StateMachineStateConfigurer<PurchaseState, PurchaseEvent> states) throws Exception {
        states
                .withStates()
                .initial(PurchaseState.NEW)
                .end(PurchaseState.PURCHASE_COMPLETE)
                .states(EnumSet.allOf(PurchaseState.class));

    }

    @Override
    public void configure(final StateMachineTransitionConfigurer<PurchaseState, PurchaseEvent> transitions) throws Exception {
        transitions
                .withExternal()
                .source(PurchaseState.NEW)
                .target(PurchaseState.RESERVED)
                .event(PurchaseEvent.RESERVE)
                .action(reservedAction(), errorAction())

                .and()
                .withExternal()
                .source(PurchaseState.RESERVED)
                .target(PurchaseState.CANCEL_RESERVED)
                .event(PurchaseEvent.RESERVE_DECLINE)
                .action(cancelAction(), errorAction())

                .and()
                .withExternal()
                .source(PurchaseState.RESERVED)
                .target(PurchaseState.PURCHASE_COMPLETE)
                .event(PurchaseEvent.BUY)
                .guard(hideGuard())
                .action(buyAction(), errorAction());
    }

    @Bean
    public Action<PurchaseState, PurchaseEvent> reservedAction() {
        return new ReservedAction();
    }

    @Bean
    public Action<PurchaseState, PurchaseEvent> cancelAction() {
        return new CancelAction();
    }

    @Bean
    public Action<PurchaseState, PurchaseEvent> buyAction() {
        return new BuyAction();
    }

    @Bean
    public Action<PurchaseState, PurchaseEvent> errorAction() {
        return new ErrorAction();
    }

    @Bean
    public Guard<PurchaseState, PurchaseEvent> hideGuard() {
        return new HideGuard();
    }

    @Bean
    public StateMachinePersister<PurchaseState, PurchaseEvent, String> persister() {
        return new DefaultStateMachinePersister<>(new PurchaseStateMachinePersister());
    }
}
