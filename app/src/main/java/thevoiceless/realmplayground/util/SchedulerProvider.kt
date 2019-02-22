package thevoiceless.realmplayground.util

import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import io.reactivex.schedulers.TestScheduler
import javax.inject.Inject

interface SchedulerProvider {
    fun io(): Scheduler
    fun computation(): Scheduler
    fun ui(): Scheduler
}

class AndroidSchedulerProvider @Inject constructor() : SchedulerProvider {
    override fun io(): Scheduler = Schedulers.io()
    override fun computation(): Scheduler = Schedulers.computation()
    override fun ui(): Scheduler = AndroidSchedulers.mainThread()
}

class TrampolineSchedulerProvider @Inject constructor() : SchedulerProvider {
    override fun io() = Schedulers.trampoline()
    override fun computation() = Schedulers.trampoline()
    override fun ui() = Schedulers.trampoline()
}

class TestSchedulerProvider @Inject constructor() : SchedulerProvider {
    val testScheduler: TestScheduler = TestScheduler()

    override fun io() = testScheduler
    override fun computation() = testScheduler
    override fun ui() = testScheduler
}
