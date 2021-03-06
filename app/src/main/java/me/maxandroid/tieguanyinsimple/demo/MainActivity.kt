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
import kotlinx.android.synthetic.main.activity_main.*
import me.maxandroid.tieguanyinsimple.R

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        goToRepositoryActivity.setOnClickListener {
            startRepositoryActivity("Kotlin", "JetBrains", url = "https://github.com/jetbrains/kotlin")
        }
        goToUserActivity.setOnClickListener {
            UserActivityBuilder.start(this, 30, "bennyhuo", "Kotliner", "打杂的", "北京")
        }
    }
}
