package by.grodno.vasili.presentation.thread;

import by.grodno.vasili.domain.executor.SubscriberThread;
import io.reactivex.Scheduler;
import io.reactivex.schedulers.Schedulers;

public class IOThread implements SubscriberThread {
    @Override
    public Scheduler getScheduler() {
        return Schedulers.io();
    }
}
