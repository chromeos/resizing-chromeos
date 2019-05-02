/*
 * Copyright 2019 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.google.example.resizecodelab.view

import androidx.lifecycle.*
import com.google.example.resizecodelab.R
import com.google.example.resizecodelab.data.DataProvider
import com.google.example.resizecodelab.data.Review

internal const val KEY_ID = "KEY_ID"
private const val KEY_EXPANDED = "KEY_EXPANDED"

/**
 * Facilitates fetching required data and exposes View related data back to view
 * Survives config changes (e.g. rotation), good place to store data that takes time to recover
 */
class MainViewModel(private val state: SavedStateHandle) : ViewModel() {

    private val appData = DataProvider.fetchData(getIdState())
    val suggestions = DataProvider.fetchSuggestions(getIdState())

    val showControls: LiveData<Boolean> = Transformations.map(appData) { it != null }
    val productName: LiveData<String> = Transformations.map(appData) { it?.title }
    val productCompany: LiveData<String> = Transformations.map(appData) { it?.developer }

    private val isDescriptionExpanded: LiveData<Boolean> = state.getLiveData(KEY_EXPANDED)
    private val _descriptionText = MediatorLiveData<String>().apply {
        addSource(appData) { value = determineDescriptionText() }
        addSource(isDescriptionExpanded) { value = determineDescriptionText() }
    }

    val descriptionText: LiveData<String>
        get() = _descriptionText

    val expandButtonTextResId: LiveData<Int> = Transformations.map(isDescriptionExpanded) {
        if (it == true) {
            R.string.button_collapse
        } else {
            R.string.button_expand
        }
    }

    val reviews: LiveData<List<Review>> = Transformations.map(appData) { it?.reviews }

    private fun determineDescriptionText(): String? {
        return appData.value?.let { appData ->
            if (isDescriptionExpanded.value == true) {
                appData.description
            } else {
                appData.shortDescription
            }
        }
    }

    /**
     * Handle toggle button presses
     */
    fun toggleDescriptionExpanded() {
        state.set(KEY_EXPANDED, !getExpandedState())
    }

    private fun getIdState(): Int {
        return state.get(KEY_ID) ?: throw IllegalStateException("MainViewModel must be called with an Id to fetch data")
    }

    private fun getExpandedState(): Boolean {
        return state.get(KEY_EXPANDED) ?: false
    }
}