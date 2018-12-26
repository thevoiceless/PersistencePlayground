package thevoiceless.realmplayground.mvp

import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import thevoiceless.realmplayground.model.BlackjackHand
import thevoiceless.realmplayground.network.Network
import thevoiceless.realmplayground.persistence.Persistence
import thevoiceless.realmplayground.util.ResourceProvider
import timber.log.Timber
import javax.inject.Inject

interface MvpPresenter<V : MvpView> {
    fun attachView(view: V)
    fun detachView()
}

interface MvpView


interface MainPresenter : MvpPresenter<MainView> {

}

interface MainView : MvpView {
    fun setData(items: List<BlackjackHand>)
}


class MainPresenterImpl @Inject constructor(
    private val persistence: Persistence,
    private val network: Network,
    private val resources: ResourceProvider
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
        disposables.add(persistence.loadCards()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                Timber.i("Loaded ${it.size} items")
                view?.setData(it)
            }, { throwable ->
                Timber.e(throwable)
            }))

        disposables.add(network.getCards()
            .flatMapCompletable { cards -> persistence.saveCards(cards) }
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                Timber.i("Saved cards")
            }, { throwable ->
                Timber.e(throwable)
            }))
    }
}
