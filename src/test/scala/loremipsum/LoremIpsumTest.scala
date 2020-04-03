/*
 * Copyright 2020 David Crosson
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

import org.scalatest.flatspec._
import org.scalatest.matchers._
import org.scalatest.OptionValues._

class LoremIpsumTest extends AnyFlatSpec with should.Matchers {

  "LoremIpsum" should "generate nothing with zero word count" in {
    LoremIpsum.generate(0) should equal(Nil)
  }

  it should "generate nothing with negative word count" in {
    LoremIpsum.generate(-100) should equal(Nil)
  }

  it should "be able to generate a paragraph of words" in {
    val paragraphs = LoremIpsum.generate(5)
    paragraphs.size should be(1)
    paragraphs.head.words.size should be(5)
  }

  it should "generate the right words count" in {
    LoremIpsum.generate(42).map(_.words.size).sum should equal(42)
    LoremIpsum.generate(1001).map(_.words.size).sum should equal(1001)
  }

  it should "always start with Lorem ipsum by default" in {
    LoremIpsum.generate(2) should equal(Paragraph(List("Lorem", "ipsum"))::Nil)
  }

  it should "generate paragraph starting with a Capitalize word" in {
    val paragraphsText = LoremIpsum.generate(5).map(_.text)
    val firstWord = paragraphsText.headOption.value
    firstWord.headOption.value.isUpper shouldBe true
    firstWord.tail.exists(_.isUpper) shouldBe false
  }

  it should "generate paragraphs text with last sentence ending with a dot" in {
    LoremIpsum.generate(5).headOption.value.text.split("\\s+").last should fullyMatch regex "[a-zA-Z]+[.]"
  }

  it should "be able to generate several paragraphs" in {
    LoremIpsum.generate(1042).size should be > (1)
  }

  it should "not generate an empty paragraphs" in {
    val paragraphs = LoremIpsum.generate(1042)
    LoremIpsum.generate(paragraphs.headOption.value.words.size).size shouldBe 1
  }

  it should "always generate a first paragraph starting with Lorem Ipsum" in {
    val tmpParagraphs = LoremIpsum.generate(1042)
    val firstParagraphSize = tmpParagraphs.headOption.value.words.size
    val secondParagraphSize = tmpParagraphs.drop(1).headOption.value.words.size

    val paragraphs = LoremIpsum.generate(firstParagraphSize + secondParagraphSize, alwaysStartWithLorem = true)
    paragraphs.headOption.value.text() should startWith regex "Lorem ipsum"
  }

  it should "be able to regenerate paragraphs from sentences" in {
    val paragraphs = LoremIpsum.generate(200,sentencesBased=true, randomize=true, alwaysStartWithLorem=true)
    paragraphs.flatMap(_.words).find(_.size == 0) shouldBe None
  }

  for { (mode, value) <- List("sentences"->true, "paragraphs"->false)} {
    it should s"generate coherent punctuation in $mode mode" in {
      val text = LoremIpsum.generate(2000, sentencesBased = value, truncate = false).map(_.text()).mkString("\n\n")
      text should not include regex("[,?.;:]{2,}")
      text should not include regex("[,][^ ]")
      text should not include regex("[^a-zA-Z][.]")
    }
  }
}
