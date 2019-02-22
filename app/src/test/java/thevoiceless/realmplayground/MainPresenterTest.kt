package thevoiceless.realmplayground

import com.nhaarman.mockitokotlin2.*
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single
import org.junit.After
import org.junit.Assert.assertNull
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Answers
import org.mockito.junit.MockitoJUnitRunner
import thevoiceless.realmplayground.model.BlackjackHand
import thevoiceless.realmplayground.mvp.MainPresenterImpl
import thevoiceless.realmplayground.mvp.MainView
import thevoiceless.realmplayground.network.Network
import thevoiceless.realmplayground.persistence.Persistence
import thevoiceless.realmplayground.util.ResourceProvider
import thevoiceless.realmplayground.util.Something
import thevoiceless.realmplayground.util.TrampolineSchedulerProvider

@RunWith(MockitoJUnitRunner::class)
class MainPresenterTest {

    private lateinit var presenter: MainPresenterImpl
    private val mockView: MainView = mock { }

    private val mockPersistence: Persistence = mock(defaultAnswer = Answers.RETURNS_DEEP_STUBS) { }
    private val mockNetwork: Network = mock(defaultAnswer = Answers.RETURNS_DEEP_STUBS) { }
    private val mockResources: ResourceProvider = mock { }
    private val mockSomething: Something = mock { }

    @Before
    fun setUp() {
        presenter = MainPresenterImpl(
            TrampolineSchedulerProvider(),
            mockPersistence,
            mockNetwork,
            mockResources,
            mockSomething
        )
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
