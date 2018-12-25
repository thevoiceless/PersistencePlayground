package thevoiceless.realmplayground.mvp

import com.squareup.moshi.Moshi
import io.reactivex.disposables.CompositeDisposable
import thevoiceless.realmplayground.persistence.Persistence
import javax.inject.Inject

interface MvpPresenter<V : MvpView> {
    fun attachView(view: V)
    fun detachView()
}

interface MvpView


interface MainPresenter : MvpPresenter<MainView> {

}

interface MainView : MvpView


class MainPresenterImpl @Inject constructor(
    private val persistence: Persistence,
    private val moshi: Moshi
) : MainPresenter {

    private var view: MainView? = null

    private val disposables = CompositeDisposable()

    override fun attachView(view: MainView) {
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
