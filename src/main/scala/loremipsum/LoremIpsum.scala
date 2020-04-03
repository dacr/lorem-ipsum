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

import scala.util.Random

case class Paragraph(words: List[String]) {
  /**
   * Format the paragraph into a string starting with a capitalize words and a final dot.
   *
   * @return the paragraph as a string
   */
  def text(): String = {
    val last = words.last.replaceAll("[^a-zA-Z]", "") + "."
    if (words.init != Nil) words.init.mkString(" ") + " " + last else last
  }
}



/**
 * LoremIpsum paragraph generator
 */
trait LoremIpsumBase {
  def corpus: Vector[String]

  lazy val paragraphs:Vector[(Paragraph, Int)] =
    corpus
      .map(_.stripMargin.trim.replaceAll("""\s{2,}""", " "))
      .map(_.split("""\s+"""))
      .map(txt => Paragraph(txt.toList) -> txt.size)

  type Sentence = List[String]

  lazy val sentences:Vector[(Sentence,Int)] =
    corpus
      .map(_.trim.replaceAll("""\s{2,}""", " "))
      .flatMap(_.split("""\s*(?<=[?.])\s*"""))
      .map(_.split("""\s+""").toList)
      .map(words => words -> words.size)

  /**
   * Generate paragraphs of lorem ipsum
   *
   * @param wordCount            How many words all generated paragraph must contains
   * @param alwaysStartWithLorem Shall the first paragraph starts with Lorem Ipsum ? default is false because of performance penalty
   * @param truncate             Shall we generate strictly the requested number of words all round it to last sentence/paragraph size ?
   * @param sentencesBased       Regenerate paragraph from sentences extracted from the corpus, a little bit slower.
   * @param randomize            Randomize paragraphs or sentences initial order
   * @return The list of generated paragraphs
   */
  def generate(
    wordCount: Int,
    alwaysStartWithLorem: Boolean = false,
    truncate: Boolean = true,
    sentencesBased: Boolean = false,
    randomize:Boolean = false
  ): List[Paragraph] = {
    if (sentencesBased) generateSentencesBased(wordCount, alwaysStartWithLorem, truncate, randomize)
    else generateParagraphsBased(wordCount, alwaysStartWithLorem, truncate, randomize)
  }

  private def generateSentencesBased(wordCount:Int, alwaysStartWithLorem:Boolean, truncate:Boolean, randomize:Boolean):List[Paragraph] = {
    val sentencesSource: Vector[(Sentence, Int)] = if (randomize) {
      if (alwaysStartWithLorem) sentences.head+:Random.shuffle(sentences.tail) else Random.shuffle(sentences)
    } else sentences

    @annotation.tailrec
    def worker(remain: Int, iteration: Int, accu: List[Sentence]): List[Sentence] = {
      sentencesSource(iteration % sentencesSource.size) match {
        case (sentence, count) if remain <= count && truncate => sentence.take(remain) :: accu
        case (sentence, count) if remain <= count => sentence :: accu
        case (sentence, count) => worker(remain - count, iteration + 1, sentence :: accu)
      }
    }
    def toParagraphs(sentences: List[Sentence]):List[Paragraph] = {
      sentences
        .grouped(5)
        .map(s => Paragraph(s.flatten))
        .toList
    }

    if (wordCount <= 0) Nil else {
      val results = worker(wordCount, 0, List.empty)
      if (alwaysStartWithLorem) toParagraphs(results.reverse) else toParagraphs(results)
    }
  }

  private def generateParagraphsBased(wordCount: Int, alwaysStartWithLorem: Boolean, truncate:Boolean, randomize:Boolean): List[Paragraph] = {
    val paragraphsSource:Vector[(Paragraph,Int)] = if (randomize) {
      if (alwaysStartWithLorem) paragraphs.head+:Random.shuffle(paragraphs.tail) else Random.shuffle(paragraphs)
    } else paragraphs
    @annotation.tailrec
    def worker(remain: Int, iteration: Int, accu: List[Paragraph]): List[Paragraph] = {
      paragraphsSource(iteration % paragraphsSource.size) match {
        case (paragraph, count) if remain <= count && truncate => Paragraph(paragraph.words.take(remain)) :: accu
        case (paragraph, count) if remain <= count => paragraph :: accu
        case (paragraph, count) => worker(remain - count, iteration + 1, paragraph :: accu)
      }
    }
    if (wordCount <= 0) Nil else {
      val results = worker(wordCount, 0, List.empty)
      if (alwaysStartWithLorem) results.reverse else results
    }
  }
}


class LoremIpsum(val corpus: Vector[String]) extends LoremIpsumBase

object LoremIpsum extends LoremIpsumBase {
  // The following sentences come from : http://fr.lipsum.com/
  val corpus: Vector[String] = Vector(
    """Lorem ipsum dolor sit amet, consectetur adipiscing elit,
      |sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.
      |Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris
      |nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in
      |reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla
      |pariatur. Excepteur sint occaecat cupidatat non proident, sunt in
      |culpa qui officia deserunt mollit anim id est laborum.
      |""",
    """Sed ut perspiciatis unde omnis iste natus error sit voluptatem
      |accusantium doloremque laudantium, totam rem aperiam, eaque ipsa
      |quae ab illo inventore veritatis et quasi architecto beatae vitae
      |dicta sunt explicabo. Nemo enim ipsam voluptatem quia voluptas sit
      |aspernatur aut odit aut fugit, sed quia consequuntur magni dolores
      |eos qui ratione voluptatem sequi nesciunt. Neque porro quisquam est,
      |qui dolorem ipsum quia dolor sit amet, consectetur, adipisci velit,
      |sed quia non numquam eius modi tempora incidunt ut labore et dolore
      |magnam aliquam quaerat voluptatem. Ut enim ad minima veniam, quis
      |nostrum exercitationem ullam corporis suscipit laboriosam, nisi
      |ut aliquid ex ea commodi consequatur? Quis autem vel eum iure
      |reprehenderit qui in ea voluptate velit esse quam nihil molestiae
      |consequatur, vel illum qui dolorem eum fugiat quo voluptas nulla pariatur?
      |""",
    """At vero eos et accusamus et iusto odio dignissimos ducimus qui
      |blanditiis praesentium voluptatum deleniti atque corrupti quos
      |dolores et quas molestias excepturi sint occaecati cupiditate non
      |provident, similique sunt in culpa qui officia deserunt mollitia animi,
      |id est laborum et dolorum fuga. Et harum quidem rerum facilis est et
      |expedita distinctio. Nam libero tempore, cum soluta nobis est eligendi
      |optio cumque nihil impedit quo minus id quod maxime placeat facere possimus,
      |omnis voluptas assumenda est, omnis dolor repellendus. Temporibus autem
      |quibusdam et aut officiis debitis aut rerum necessitatibus saepe eveniet
      |ut et voluptates repudiandae sint et molestiae non recusandae. Itaque earum
      |rerum hic tenetur a sapiente delectus, ut aut reiciendis voluptatibus
      |maiores alias consequatur aut perferendis doloribus asperiores repellat.
      |"""
  ).map(_.stripMargin)

  def apply(corpus: Vector[String]): LoremIpsum = new LoremIpsum(corpus)
}
