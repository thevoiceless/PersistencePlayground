package thevoiceless.realmplayground

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import thevoiceless.realmplayground.di.dependencies
import thevoiceless.realmplayground.mvp.MainPresenter
import thevoiceless.realmplayground.mvp.MainView

class MainActivity : AppCompatActivity(), MainView {

    private val presenter: MainPresenter by lazy { dependencies.mainPresenter() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        presenter.attachView(this)
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.detachView()
    }
}
