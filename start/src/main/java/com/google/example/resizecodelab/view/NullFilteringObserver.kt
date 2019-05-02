/*      Copyright 2019 Google LLC

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

import androidx.lifecycle.Observer

/**
 * An Observer that only fires when the value passed in is not null.
 */
class NullFilteringObserver<T : Any>(private val nonNullOnChanged: (T) -> Unit) : Observer<T> {
    override fun onChanged(t: T?) {
        t?.let { nonNullOnChanged(t) }
    }
}
