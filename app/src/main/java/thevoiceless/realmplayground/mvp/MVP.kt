package thevoiceless.realmplayground.mvp

import io.reactivex.disposables.CompositeDisposable

interface MvpPresenter<V : MvpView> {
    fun attachView(view: V)
    fun detachView()
}

interface MvpView


interface RealmPresenter : MvpPresenter<RealmView> {

}

interface RealmView : MvpView


class RealmPresenterImpl : RealmPresenter {

    private var view: RealmView? = null

    private val disposables = CompositeDisposable()

    override fun attachView(view: RealmView) {
        this.view = view
    }

    override fun detachView() {
        view = null
        disposables.dispose()
    }

}
