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
object LoremIpsum {
  // The following sentences come from : http://fr.lipsum.com/
  private val samples = Array(
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
  )
    .map(_.stripMargin.trim.replaceAll("""\s{2,}""", " "))
    .map(_.split("""\s+"""))
    .map(txt => Paragraph(txt.toList) -> txt.size)

  /**
   * Generate paragraphs of lorem ipsum
   *
   * @param wordCount how many words all generated paragraph must contains
   * @return the list of generated paragraphs
   */
  def generate(wordCount: Int): List[Paragraph] = {
    @annotation.tailrec
    def worker(remain: Int, iteration: Int, accu: List[Paragraph]): List[Paragraph] = {
      samples(iteration % samples.size) match {
        case (paragraph, count) if remain <= count => Paragraph(paragraph.words.take(remain)) :: accu
        case (paragraph, count) => worker(remain - count, iteration + 1, paragraph :: accu)
      }
    }
    if (wordCount <= 0) Nil else worker(wordCount, 0, List.empty)
  }

}
