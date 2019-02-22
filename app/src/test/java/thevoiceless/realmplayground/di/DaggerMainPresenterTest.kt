package thevoiceless.realmplayground.di

import com.nhaarman.mockitokotlin2.*
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Answers
import org.mockito.junit.MockitoJUnitRunner
import thevoiceless.realmplayground.model.BlackjackHand
import thevoiceless.realmplayground.mvp.MainPresenter
import thevoiceless.realmplayground.mvp.MainView
import thevoiceless.realmplayground.network.Network
import thevoiceless.realmplayground.persistence.Persistence
import thevoiceless.realmplayground.util.TrampolineSchedulerProvider

@RunWith(MockitoJUnitRunner::class)
class DaggerMainPresenterTest {

    private val scheduler = SchedulerForTest(TrampolineSchedulerProvider())
    private val mockPersistence = mock<Persistence>(defaultAnswer = Answers.RETURNS_DEEP_STUBS) { }
    private val mockNetwork = mock<Network>(defaultAnswer = Answers.RETURNS_DEEP_STUBS) { }

    private val providers = object : ProvidersForTest() {
        override fun mockPersistence() = mockPersistence
        override fun mockNetwork() = mockNetwork
    }

    private val dependencies = DaggerTestDependencies.builder()
        .schedulerForTest(scheduler)
        .providersForTest(providers)
        .build()

    private lateinit var presenter: MainPresenter
    private val mockView: MainView = mock { }

    @Before
    fun setUp() {
        presenter = dependencies.mainPresenter()
    }

    @After
    fun tearDown() {
        presenter.detachView()
    }

    @Test
    fun `Load data on attach`() {
        val persisted: List<BlackjackHand> = listOf(mock { }, mock { })
        val fetched: List<BlackjackHand> = listOf(mock { }, mock { })
        whenever(mockPersistence.loadCards()).thenReturn(Observable.just(persisted))
        whenever(mockNetwork.getCards()).thenReturn(Single.just(fetched))
        whenever(mockPersistence.saveCards(any())).thenReturn(Completable.complete())


        presenter.attachView(mockView)


        verify(mockPersistence, atLeastOnce()).loadCards()
        verify(mockView).setData(persisted)
        verify(mockNetwork).getCards()
        verify(mockPersistence).saveCards(fetched)
    }
}
