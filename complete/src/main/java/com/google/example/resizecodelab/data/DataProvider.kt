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
package com.google.example.resizecodelab.data

import android.os.Handler
import android.os.Looper
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.example.resizecodelab.R
import java.util.concurrent.TimeUnit

/**
 * Fake DataProvider to simulate long running network calls
 */
object DataProvider {

    private const val PRODUCT_DESC =
        "This Whizzbuckling Whipplebot is the finest on the market. Bold, bright design meets crisp, clean functionality in a package that is indescribably satisfying. \n\nWithout compromising the finest quality of craftmanship, this whipplebot provides superior functionality in a package that is unbeatable. Made to last, made to use, made to amaze. This whipplebot will provide decades of delight and become one of the family. Don't settle for mediocre when you can own this amazing Whipplebot."
    private const val REVIEW_TEXT_1 =
        "This is simply the best Whipplebot I have ever used. In fact, while cleaning the kitchen, I found this to be indispensable. Excellent style, great colours, and it has a certain satisfying feeling that comes along with its use. I would recommend this to anyone, and in fact, I have. Even young people and those slightly older will benefit from its use. Highly recommended."
    private const val REVIEW_TEXT_2 = "A solid whipplebot, excellent value for price."
    private const val REVIEW_TEXT_3 =
        "Pleasantly surprised. My husband bought this as an anniversary gift in place of the usual tie. At first I had no idea what I would use it for but after getting used to the ergonomic functions, I find I use this whipplebot every day! My only complaint is that I now think we'll have to get one for my mother."
    private const val REVIEW_TEXT_4 =
        "Works as intended. This is no great work of art, rather its ostentatious lines offend contemporary style in the most particular way. However, as it works precisely as described and does exactly as intended, we are prepared to put forth that it is a most excellent Whipplebot. Yours etc."

    private val mainHandler = Handler(Looper.getMainLooper())

    private val appData = AppData(
        title = "Whizzbuckling Whipplebot",
        developer = "Ostentatious Objects Inc.",
        description = PRODUCT_DESC,
        shortDescription = "${PRODUCT_DESC.substring(0, 120)}...",
        reviews = listOf(
            Review("Arthur Dent", REVIEW_TEXT_1),
            Review("Trillian Lancaster", REVIEW_TEXT_2),
            Review("Goody Twoshoes", REVIEW_TEXT_3),
            Review("Maxentius", REVIEW_TEXT_4)
        )
    )

    private val suggestions = listOf(
        Suggestion("Gregarious Grogglestock", R.drawable.gregarious),
        Suggestion("Byzantium Barnacles", R.drawable.byzantium),
        Suggestion("Cratankerous Cribblewelps", R.drawable.cratankerous),
        Suggestion("Sunsaritous", R.drawable.sunsari),
        Suggestion("Squiggalia Scrumptiae", R.drawable.squiggle),
        Suggestion("Tenachaterous Torna", R.drawable.tenacious),
        Suggestion("Venemial Venorae", R.drawable.venemial)
    )

    fun fetchData(dataId: Int): LiveData<AppData> {
        if (dataId != 1) throw IllegalArgumentException("Illegal id argument")

        return MutableLiveData<AppData>().also {
            // Introduce an artificial delay to simulate network traffic
            mainHandler.postDelayed({ it.value = appData }, TimeUnit.SECONDS.toMillis(5))
        }
    }

    fun fetchSuggestions(dataId: Int): LiveData<List<Suggestion>> {
        if (dataId != 1) throw IllegalArgumentException("Illegal id argument")

        return MutableLiveData<List<Suggestion>>().also {
            // Introduce an artificial delay to simulate network traffic
            mainHandler.postDelayed({ it.value = suggestions }, TimeUnit.SECONDS.toMillis(5))
        }
    }
}

/**
 * Main Data model returned by our "Endpoint"
 */
data class AppData(
    val title: String,
    val developer: String,
    val description: String,
    val shortDescription: String,
    val reviews: List<Review>
)

/**
 * Review model
 */
data class Review(val name: String, val review: String)

/**
 * Suggestion model
 */
data class Suggestion(val name: String, val imageId: Int)
