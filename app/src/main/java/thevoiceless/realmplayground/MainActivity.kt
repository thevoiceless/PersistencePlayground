package thevoiceless.realmplayground

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_main.*
import thevoiceless.realmplayground.di.dependencies
import thevoiceless.realmplayground.model.BlackjackHand
import thevoiceless.realmplayground.mvp.MainPresenter
import thevoiceless.realmplayground.mvp.MainView

class MainActivity : AppCompatActivity(), MainView {

    private val presenter: MainPresenter by lazy { dependencies.mainPresenter() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        presenter.attachView(this)
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.detachView()
    }

    override fun setData(items: List<BlackjackHand>) {
        mainLayout.addView(TextView(this).apply {
            text = "Got ${items.size} items"
        })
    }
}
