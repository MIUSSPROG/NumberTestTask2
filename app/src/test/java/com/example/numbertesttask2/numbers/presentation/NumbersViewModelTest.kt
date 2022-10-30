package com.example.numbertesttask2.numbers.presentation

import android.view.View
import com.example.numbertesttask2.numbers.domain.NumberFact
import com.example.numbertesttask2.numbers.domain.NumberUiMapper
import com.example.numbertesttask2.numbers.domain.NumbersInteractor
import com.example.numbertesttask2.numbers.domain.NumbersResult
import kotlinx.coroutines.*
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class NumbersViewModelTest : BaseTest() {

    private lateinit var viewModel: NumbersViewModel
    private lateinit var communications: TestNumberCommunications
    private lateinit var interactor: TestNumbersInteractor
    private lateinit var manageResources: TestManageResources

    @OptIn(DelicateCoroutinesApi::class)
    private val mainThreadSurrogate = newSingleThreadContext("UI thread")

    @OptIn(ExperimentalCoroutinesApi::class)
    @After
    fun tearDown() {
        Dispatchers.resetMain() // reset the main dispatcher to the original Main dispatcher
        mainThreadSurrogate.close()
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Before
    fun init() {
        Dispatchers.setMain(mainThreadSurrogate)
        communications = TestNumberCommunications()
        interactor = TestNumbersInteractor()
        manageResources = TestManageResources()

        viewModel =
            NumbersViewModel(
                HandleNumbersRequest.Base(
                    TestDispatchersList(),
                    communications,
                    NumbersResultMapper(communications, NumberUiMapper())
                ),
                manageResources,
                communications,
                interactor
            )
    }

    /**
     * Initial test
     * At start fetch data and show it
     * then try to get some data
     * then re-init and check the result
     */
    @Test
    fun `test init and re-init`() = runBlocking {

        interactor.changeExpectedResult(NumbersResult.Success())

        viewModel.init(isFirstRun = true)

        assertEquals(View.VISIBLE, communications.progressCalledList[0])
        assertEquals(2, communications.progressCalledList.size)
        assertEquals(View.GONE, communications.progressCalledList[1])

        assertEquals(1, communications.stateCalledList.size)
        assertEquals(UiState.Success(), communications.stateCalledList[0])

        assertEquals(0, communications.numbersList.size)
        assertEquals(0, communications.timesShowList)

        // get some data
        interactor.changeExpectedResult(NumbersResult.Failure("no internet connection"))
        viewModel.fetchRandomNumberFact()

        assertEquals(View.VISIBLE, communications.progressCalledList[2])

        assertEquals(1, interactor.fetchAboutRandomNumberCalledList.size)

        assertEquals(4, communications.progressCalledList.size)
        assertEquals(View.GONE, communications.progressCalledList[3])

        assertEquals(2, communications.stateCalledList.size)
        assertEquals(UiState.ShowError("no internet connection"), communications.stateCalledList[1])
        assertEquals(0, communications.timesShowList)

        viewModel.init(isFirstRun = false)
        assertEquals(4, communications.progressCalledList.size)
        assertEquals(2, communications.stateCalledList.size)
        assertEquals(0, communications.timesShowList)
    }


    /**
     *
     */
    @Test
    fun `fact about empty number`() = runBlocking {

        manageResources.makeExpectedAnswer("entered number is empty")
        viewModel.fetchNumberFact("")

        assertEquals(0, interactor.fetchAboutNumberCalledList.size)

        assertEquals(0, communications.progressCalledList.size)

        assertEquals(1, communications.stateCalledList.size)

        manageResources.makeExpectedAnswer("entered number is empty")
        assertEquals(UiState.ShowError("entered number is empty"), communications.stateCalledList[0])

        assertEquals(0, communications.timesShowList)
    }

    @Test
    fun `fact about some number`() = runBlocking {

        interactor.changeExpectedResult(
            NumbersResult.Success(
                listOf(
                    NumberFact(
                        "45",
                        "fact about 45"
                    )
                )
            )
        )
        viewModel.fetchNumberFact("45")

        assertEquals(2, communications.progressCalledList.size)
        assertEquals(View.GONE, communications.progressCalledList[1])

//        assertEquals(1, interactor.fetchAboutNumberCalledList.size)
//        assertEquals(NumberFact("45", "fact about 45"), interactor.fetchAboutNumberCalledList[0])
//
//        assertEquals(2, communications.progressCalledList.size)
//        assertEquals(false, communications.progressCalledList[1])

        assertEquals(1, communications.stateCalledList.size)
        assertEquals(UiState.Success(), communications.stateCalledList[0])

        assertEquals(1, communications.timesShowList)
        assertEquals(NumberUi("45", "fact about 45"), communications.numbersList[0])
    }

    @Test
    fun `test clear error`(){
        viewModel.clearError()

        assertEquals(1, communications.stateCalledList.size)
        assertEquals(true, communications.stateCalledList[0] is UiState.ClearError)
    }

    private class TestManageResources : ManageResources {
        private var string = ""

        fun makeExpectedAnswer(expected: String){
            string = expected
        }

        override fun string(id: Int): String {
            return string
        }
    }

    private class TestNumbersInteractor : NumbersInteractor {

        private var result: NumbersResult = NumbersResult.Success()

        val initCalledList = mutableListOf<NumbersResult>()
        val fetchAboutNumberCalledList = mutableListOf<NumbersResult>()
        val fetchAboutRandomNumberCalledList = mutableListOf<NumbersResult>()

        fun changeExpectedResult(newResult: NumbersResult) {
            result = newResult
        }

        override suspend fun init(): NumbersResult {
            initCalledList.add(result)
            return result
        }

        override suspend fun factAboutNumber(number: String): NumbersResult {
            fetchAboutNumberCalledList.add(result)
            return result
        }

        override suspend fun factAboutRandomNumber(): NumbersResult {
            fetchAboutRandomNumberCalledList.add(result)
            return result
        }
    }

    private class TestDispatchersList: DispatchersList{
        override fun io(): CoroutineDispatcher {
            return TestCoroutineDispatcher()
        }

        override fun ui(): CoroutineDispatcher {
            return TestCoroutineDispatcher()
        }
    }
}