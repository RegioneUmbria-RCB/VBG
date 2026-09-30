package it.gruppoinit.pal.gp.core.features.scheduler;

import java.util.Set;

import it.gruppoinit.pal.gp.core.domain.Taskschedulerparametri;

public interface IParameter<T> {

    T getValueFromParameters(Set<Taskschedulerparametri> parametri);
}
