package thevoiceless.realmplayground.mvp

import io.reactivex.disposables.CompositeDisposable
import thevoiceless.realmplayground.persistence.RealmPersistence
import javax.inject.Inject

interface MvpPresenter<V : MvpView> {
    fun attachView(view: V)
    fun detachView()
}

interface MvpView


interface RealmPresenter : MvpPresenter<RealmView> {

}

interface RealmView : MvpView


class RealmPresenterImpl @Inject constructor(
    private val realm: RealmPersistence
) : RealmPresenter {

    private var view: RealmView? = null

    private val disposables = CompositeDisposable()

    override fun attachView(view: RealmView) {
        this.view = view

        loadData()
    }

    override fun detachView() {
        view = null
        disposables.dispose()
    }

    private fun loadData() {

    }
}
