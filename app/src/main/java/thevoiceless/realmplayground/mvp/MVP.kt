package thevoiceless.realmplayground.mvp

import io.reactivex.disposables.CompositeDisposable
import thevoiceless.realmplayground.network.Network
import thevoiceless.realmplayground.persistence.Persistence
import timber.log.Timber
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
    private val network: Network
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
            .subscribe({
                Timber.i("Loaded ${it.size} items")
            }, { throwable ->
                Timber.e(throwable)
            }))

        disposables.add(network.getCards()
            .map { cards -> persistence.saveCards(cards) }
            .subscribe({
                Timber.i("Saved cards")
            }, { throwable ->
                Timber.e(throwable)
            }))
    }
}
