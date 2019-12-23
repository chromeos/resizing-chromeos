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

import android.os.Bundle
import android.view.Gravity
import android.widget.Button
import android.widget.PopupWindow
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.core.widget.NestedScrollView
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.example.resizecodelab.R
import com.google.example.resizecodelab.data.AppData
import com.google.example.resizecodelab.data.DataProvider

class MainActivity : AppCompatActivity() {

    private lateinit var mainNestedScrollView: NestedScrollView
    private lateinit var loadingProgress: ProgressBar
    private lateinit var purchaseButton: Button
    private lateinit var expandDescriptionButton: Button
    private lateinit var productNameTextView: TextView
    private lateinit var productCompanyTextView: TextView
    private lateinit var productDescriptionTextView: TextView

    // DELETE NEXT 2 LINES IN STEP 1 - After adding ViewModel
    private var isDescriptionExpanded: Boolean = false
    private var appData: AppData? = null


    private val reviewAdapter = ReviewAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mainNestedScrollView = findViewById(R.id.main_nested_scroll)
        loadingProgress = findViewById(R.id.loading_progress)
        purchaseButton = findViewById(R.id.purchase_button)
        expandDescriptionButton = findViewById(R.id.expand_description_button)
        productNameTextView = findViewById(R.id.product_name_text_view)
        productCompanyTextView = findViewById(R.id.product_company_text_view)
        productDescriptionTextView = findViewById(R.id.product_description_text_view)
        val reviewsRecyclerView: RecyclerView = findViewById(R.id.reviews_recycler_view)
        val suggestedRecyclerView: RecyclerView = findViewById(R.id.suggested_recycler_view)

        val suggestionAdapter = SuggestionAdapter()

        val dataId = 1 // Normally you would receive this as an argument to this Activity.

        /////////////////////////////////////////////////////
        // STEP 2 - State (onSaveInstanceState) - 3 of 3
        /*
        val viewModel = ViewModelProviders.of(this,
            SavedStateVMFactory(this, Bundle().apply { putInt(KEY_ID, dataId) }))
            .get(MainViewModel::class.java)
        */
        /////////////////////////////////////////////////////

        /////////////////////////////////////////////////////
        // STEP 1 - State (ViewModel)
        /*
        val viewModel =
            ViewModelProviders.of(this).get(MainViewModel::class.java) // REPLACE ME in STEP 2

        viewModel.reviews.observe(this,
            NullFilteringObserver(reviewAdapter::onReviewsLoaded))
        viewModel.suggestions.observe(this,
            NullFilteringObserver(suggestionAdapter::updateSuggestions))
        viewModel.showControls.observe(this,
            NullFilteringObserver(::updateControlVisibility))
        viewModel.expandButtonTextResId.observe(this,
            NullFilteringObserver<Int>(expandDescriptionButton::setText))
        viewModel.productName.observe(this,
            NullFilteringObserver(productNameTextView::setText))
        viewModel.productCompany.observe(this,
            NullFilteringObserver(productCompanyTextView::setText))
        viewModel.descriptionText.observe(this,
            NullFilteringObserver(productDescriptionTextView::setText))
        */
        /////////////////////////////////////////////////////

        /////////////////////////////////////////////////////
        // STEP 1 - State (ViewModel)
        //
        // REMOVE the following lines
        expandDescriptionButton.setOnClickListener {
            toggleExpandButton()
            updateDescription()
        }
        handleAppDataUpdate(null) // Default state.
        /////////////////////////////////////////////////////
        // and REPLACE with the ViewModel equivalent
        //
        // expandDescriptionButton.setOnClickListener { viewModel.toggleDescriptionExpanded() }
        //
        /////////////////////////////////////////////////////

        // DELETE NEXT LINE IN STEP 1 - After adding ViewModel
        DataProvider.fetchData(dataId).observe(this, Observer(::handleAppDataUpdate))

        DataProvider.fetchSuggestions(dataId)
            .observe(this, NullFilteringObserver(suggestionAdapter::updateSuggestions))

        reviewsRecyclerView.layoutManager = LinearLayoutManager(this)
        suggestedRecyclerView.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)

        /////////////////////////////////////////////////////
        // STEP 3 - Multiple Layouts
        /*
        val isLandscape = resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE
        val isSmall = resources.configuration.screenWidthDp < 600

        reviewsRecyclerView.layoutManager =
            if (isLandscape) {
                GridLayoutManager(this, 2)
            } else {
                LinearLayoutManager(this)
            }

        suggestedRecyclerView.layoutManager =
            when {
                isSmall -> LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
                isLandscape -> GridLayoutManager(this, 3)
                else -> GridLayoutManager(this, 2)
            }
        */
        /////////////////////////////////////////////////////

        reviewsRecyclerView.adapter = reviewAdapter
        suggestedRecyclerView.adapter = suggestionAdapter

        /////////////////////////////////////////////////////
        // STEP 4 - Advanced: Window Coordinates - 1 of 2
        /*
        purchaseButton.setOnClickListener { showPurchaseDialog() }
        */
        /////////////////////////////////////////////////////
    }

    /////////////////////////////////////////////////////
    // STEP 4 - Advanced: Window Coordinates - 2 of 2
    /*
    private fun showPurchaseDialog() {
        val popupView = layoutInflater
            .inflate(R.layout.dialog_purchase, mainNestedScrollView, false)

        //Get window size
        val displayMetrics = Resources.getSystem().displayMetrics // INCORRECT
        // val displayMetrics = resources.displayMetrics // CORRECT
        val screenWidthPx = displayMetrics.widthPixels
        val screenHeightPx = displayMetrics.heightPixels

        //Popup should be 50% of window size
        val popupWidthPx = screenWidthPx / 2
        val popupHeightPx = screenHeightPx / 2

        //Place it in the middle of the window
        val popupX = (screenWidthPx / 2) - (popupWidthPx / 2)
        val popupY = (screenHeightPx / 2) - (popupHeightPx / 2)

        //Show the window
        val popupWindow = PopupWindow(popupView, popupWidthPx, popupHeightPx, true)
        popupWindow.elevation = 10f
        popupWindow.showAtLocation(mainNestedScrollView, Gravity.NO_GRAVITY, popupX, popupY)
    }
    */
    /////////////////////////////////////////////////////

    private fun updateControlVisibility(showControls: Boolean) {
        loadingProgress.isVisible = !showControls
        purchaseButton.isVisible = showControls
        expandDescriptionButton.isVisible = showControls
    }


    /////////////////////////////////////////////////////
    // STEP 1 - State (ViewModel)
    //
    // DELETE following 3 methods after adding ViewModel
    private fun handleAppDataUpdate(appData: AppData?) {
        this.appData = appData
        updateControlVisibility(appData != null)
        appData?.let {
            productNameTextView.text = it.title
            productCompanyTextView.text = it.developer
            reviewAdapter.onReviewsLoaded(it.reviews)
            updateDescription()
        }
    }
    private fun toggleExpandButton() {
        isDescriptionExpanded = !isDescriptionExpanded
        expandDescriptionButton.setText(
            if (isDescriptionExpanded) R.string.button_collapse else R.string.button_expand
        )
    }

    private fun updateDescription() {
        productDescriptionTextView.text =
            if (isDescriptionExpanded) appData?.description else appData?.shortDescription
    }
    /////////////////////////////////////////////////////
}