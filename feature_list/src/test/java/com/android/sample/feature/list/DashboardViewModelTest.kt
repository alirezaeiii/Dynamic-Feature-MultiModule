package com.android.sample.feature.list

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.android.sample.common.util.ViewState
import com.android.sample.common.util.schedulers.TestSchedulerProvider
import com.android.sample.core.database.dashboard.DashboardDao
import com.android.sample.core.network.ApiService
import com.android.sample.core.repository.DashboardRepository
import com.android.sample.core.response.Dashboard
import com.android.sample.core.response.Links
import com.android.sample.core.response.asDatabaseModel
import com.android.sample.feature.list.viewmodel.DashboardViewModel
import io.reactivex.Observable
import org.hamcrest.CoreMatchers.notNullValue
import org.hamcrest.CoreMatchers.nullValue
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.core.Is.`is`
import org.junit.Assert.assertTrue
import org.junit.Assert.fail
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner
import java.util.concurrent.TimeUnit

@RunWith(MockitoJUnitRunner::class)
class DashboardViewModelTest {

    @get:Rule
    val rule: TestRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var service: ApiService

    @Mock
    private lateinit var dao: DashboardDao

    private lateinit var schedulerProvider: TestSchedulerProvider
    private lateinit var dashboardRepository: DashboardRepository
    private lateinit var viewModel: DashboardViewModel

    @Before
    fun setUp() {
        // Make the sure that all schedulers are immediate.
        schedulerProvider = TestSchedulerProvider()

        dashboardRepository = DashboardRepository(service, dao)
        viewModel = DashboardViewModel(dashboardRepository, schedulerProvider)
    }

    @Test
    fun givenDaoReturnCache_whenGetResult_thenReturnSuccessFromCache() {
        val dashboard = Dashboard(Links((emptyList())))
        `when`(dao.getDashboard()).thenReturn(dashboard.asDatabaseModel())
        `when`(service.getDashboard()).thenReturn(Observable.error(Exception("")))

        viewModel.liveData.value.let {
            assertThat(it, `is`(ViewState.Loading))
        }

        schedulerProvider.testScheduler.advanceTimeBy(1, TimeUnit.MILLISECONDS)

        viewModel.liveData.value.let {
            assertThat(it, `is`(notNullValue()))
            if (it is ViewState.Success) {
                it.data?.let { data ->
                    assertTrue(data.links.sections.isEmpty())
                }
            } else {
                fail("Wrong type $it")
            }
        }
    }

    @Test
    fun givenDaoReturnNull_whenGetResult_thenReturnErrorWithNullMessage() {
        `when`(dao.getDashboard()).thenReturn(null)

        viewModel.liveData.value.let {
            assertThat(it, `is`(ViewState.Loading))
        }

        schedulerProvider.testScheduler.advanceTimeBy(1, TimeUnit.MILLISECONDS)

        viewModel.liveData.value.let {
            assertThat(it, `is`(notNullValue()))
            if (it is ViewState.Error) {
                assertThat(it.message, `is`(nullValue()))
            } else {
                fail("Wrong type $it")
            }
        }
    }

    @Test
    fun givenServerReturnError_whenGetResult_thenReturnErrorWithMessage() {
        `when`(service.getDashboard()).thenReturn(Observable.error(Exception("error")))

        viewModel.liveData.value.let {
            assertThat(it, `is`(ViewState.Loading))
        }

        schedulerProvider.testScheduler.advanceTimeBy(1, TimeUnit.MILLISECONDS)

        viewModel.liveData.value.let {
            assertThat(it, `is`(notNullValue()))
            if (it is ViewState.Error) {
                assertThat(it.message, `is`(notNullValue()))
                assertThat(it.message, `is`("error"))
            } else {
                fail("Wrong type $it")
            }
        }
    }
}