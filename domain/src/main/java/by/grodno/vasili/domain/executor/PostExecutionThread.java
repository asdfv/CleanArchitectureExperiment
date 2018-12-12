package by.grodno.vasili.domain.executor;

import io.reactivex.Scheduler;

/**
 * Thread abstraction for update UI
 */
public interface PostExecutionThread {
    Scheduler getScheduler();
}
