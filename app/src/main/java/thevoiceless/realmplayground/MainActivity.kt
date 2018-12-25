package thevoiceless.realmplayground

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import thevoiceless.realmplayground.mvp.RealmPresenter
import thevoiceless.realmplayground.mvp.RealmPresenterImpl
import thevoiceless.realmplayground.mvp.RealmView
import thevoiceless.realmplayground.persistence.RealmPersistence

class MainActivity : AppCompatActivity(), RealmView {

    private lateinit var presenter: RealmPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        presenter = RealmPresenterImpl(
            RealmPersistence(this)
        )
        presenter.attachView(this)
    }

    override fun onDestroy() {
        super.onDestroy()

        presenter.detachView()
    }
}
