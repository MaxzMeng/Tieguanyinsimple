/*
 * Copyright (C) 2018 Bennyhuo.
 *
 * Licensed under the MIT License (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://opensource.org/licenses/MIT
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 *
 */

package me.maxandroid.tieguanyinsimple.demo

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_repository.*
import me.maxandroid.tieguanyinsimple.R
import me.maxandroid.tieguanyinsimple.annotations.Builder
import me.maxandroid.tieguanyinsimple.annotations.Optional
import me.maxandroid.tieguanyinsimple.annotations.Required

@Builder
class RepositoryActivity: AppCompatActivity() {

    @Required
    lateinit var name: String

    @Required
    lateinit var owner: String

    @Optional(stringValue = "")
    lateinit var url: String

    @Optional(intValue = 123)
    var createAt: Long = 0L

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_repository)
        nameView.text = name
        ownerView.text = owner
        urlView.text = url
        createAtView.text = createAt.toString()
    }
}