/*
 * Copyright 2017 David Crosson
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package loremipsum

import org.scalatest.Matchers._
import org.scalatest._


class LoremIpsumTest extends FunSuite {

  test("zero value") {
    LoremIpsum.generate(0) should equal(Nil)
  }
  test("negative value") {
    LoremIpsum.generate(-100) should equal(Nil)
  }
  test("one paragraphe with several words") {
    LoremIpsum.generate(1) should equal(Paragraph(List("Lorem"))::Nil)
    LoremIpsum.generate(2) should equal(Paragraph(List("Lorem", "ipsum"))::Nil)
  }
  test("paragraph to text") {
    LoremIpsum.generate(1).map(_.text()) should equal(List("Lorem."))
    LoremIpsum.generate(2).map(_.text()) should equal(List("Lorem ipsum."))
  }

  test("word count check") {
    LoremIpsum.generate(42).map(_.words.size).sum should equal(42)
    LoremIpsum.generate(1001).map(_.words.size).sum should equal(1001)
  }

}
