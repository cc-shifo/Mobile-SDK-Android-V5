/*
 * Copyright (c) 2018-2020 DJI
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 *
 */

package com.example.mylibrary.core.widget.flightmode
import com.example.mylibrary.core.base.DJISDKModel
import com.example.mylibrary.core.base.WidgetModel
import com.example.mylibrary.core.communication.ObservableInMemoryKeyedStore
import com.example.mylibrary.core.util.DataProcessor
import dji.sdk.keyvalue.key.FlightControllerKey
import dji.sdk.keyvalue.key.KeyTools
import io.reactivex.rxjava3.core.Flowable


/**
 * Widget Model for the [FlightModeWidget] used to define
 * the underlying logic and communication
 */
class FlightModeWidgetModel(
    djiSdkModel: DJISDKModel,
    keyedStore: ObservableInMemoryKeyedStore
) : WidgetModel(djiSdkModel, keyedStore) {

    //region Fields
    private val flightModeStringProcessor: DataProcessor<String> = DataProcessor.create("")
    private val flightModeStateProcessor: DataProcessor<FlightModeState> = DataProcessor.create(
        FlightModeState.ProductDisconnected
    )
    //endregion

    //region Data

    /**
     * Get the flight mode state
     */
    val flightModeState: Flowable<FlightModeState>
        get() = flightModeStateProcessor.toFlowable()

    //endregion

    //region Lifecycle
    override fun inSetup() {
        bindDataProcessor(
            KeyTools.createKey(
                FlightControllerKey.KeyFlightModeString), flightModeStringProcessor)
    }

    override fun inCleanup() {
        // Nothing to clean
    }

    override fun updateStates() {
        if (productConnectionProcessor.value) {
            flightModeStateProcessor.onNext(
                FlightModeState.FlightModeUpdated(
                    flightModeStringProcessor.value
                )
            )
        } else {
            flightModeStateProcessor.onNext(FlightModeState.ProductDisconnected)
        }
    }

    //endregion

    /**
     * Class to represent states of flight mode
     */
    sealed class FlightModeState {
        /**
         * When the product is disconnected
         */
        object ProductDisconnected : FlightModeState()

        /**
         * When the product is connected and current flight mode string
         */
        data class FlightModeUpdated(val flightModeString: String) : FlightModeState()
    }
}
