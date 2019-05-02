/*      Copyright 2018 Google LLC

        Licensed under the Apache License, Version 2.0 (the "License");
        you may not use this file except in compliance with the License.
        You may obtain a copy of the License at

        http://www.apache.org/licenses/LICENSE-2.0

        Unless required by applicable law or agreed to in writing, software
        distributed under the License is distributed on an "AS IS" BASIS,
        WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
        See the License for the specific language governing permissions and
        limitations under the License.
*/
package com.google.example.resizecodelab.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.google.example.resizecodelab.R
import com.google.example.resizecodelab.data.Review

class ReviewAdapter : androidx.recyclerview.widget.RecyclerView.Adapter<ReviewAdapter.ReviewViewHolder>() {

    private val reviews = mutableListOf<Review>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReviewViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.list_item_review, parent, false)
        return ReviewViewHolder(view)
    }

    override fun onBindViewHolder(holder: ReviewViewHolder, position: Int) {
        val review = reviews[position]
        holder.nameText.text = review.name
        holder.reviewText.text = review.review
    }

    override fun getItemCount(): Int {
        return reviews.size
    }

    fun onReviewsLoaded(reviews: List<Review>) {
        this.reviews.clear()
        this.reviews.addAll(reviews)
        notifyDataSetChanged() // DiffUtil is recommended for the best user experience in a production application.
    }

    class ReviewViewHolder(itemView: View) : androidx.recyclerview.widget.RecyclerView.ViewHolder(itemView) {
        val nameText: TextView = itemView.findViewById(R.id.review_name_text_view)
        val reviewText: TextView = itemView.findViewById(R.id.review_text_view)
    }
}
