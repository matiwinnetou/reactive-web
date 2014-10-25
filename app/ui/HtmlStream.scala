/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2014 Yevgeniy Brikman
*/
package ui

import play.Logger
import play.api.http.{ContentTypeOf, Writeable}
import play.api.libs.concurrent.Execution.Implicits._
import play.api.libs.iteratee.{Enumeratee, Enumerator, Iteratee}
import play.api.mvc.{Codec, Result}
import play.libs.F
import play.mvc.Results.Chunks
import play.mvc.Results.Chunks.Out
import play.twirl.api.{Format, Html, HtmlFormat}

import scala.collection.immutable.Seq
import scala.concurrent.Future
import scala.language.implicitConversions
import scala.util.{Failure, Success}

/**
 * A custom Appendable that lets us have .scala.stream templates instead of .scala.html. These templates can mix Html
 * markup with Enumerators that contain Html markup. We add this class as a custom template type in build.sbt.
 *
 * @param enumerator
 */
case class HtmlStream(enumerator: Enumerator[Html]) extends play.twirl.api.Appendable[HtmlStream] {

  def +=(other: HtmlStream): HtmlStream = andThen(other)

  def andThen(other: HtmlStream): HtmlStream = HtmlStream(enumerator.andThen(other.enumerator))

  /**
   * Java API. Creates a Chunks object that can be returned from a Java controller to stream out the HtmlStream.
   */
  def toChunks(): Chunks[Html] = {
    val utf8 = Codec.javaSupported("utf-8")

    new Chunks[Html](Writeable.writeableOf_Content(utf8, ContentTypeOf.contentTypeOf_Html(utf8))) {
      def onReady(out: Out[Html]) {
        val htmlF = enumerator.run(Iteratee.foreach { html =>
          if (!html.toString.isEmpty) {
            out.write(html)
          }
        })
        htmlF onComplete {
          case Success(html) => out.close()
          case Failure(t) => {
            Logger.error("Rendering exception", t);
            out.close()
          }
        }
      }
    }
  }

  def fold(): Future[Html] = enumerator.run(Iteratee.fold(HtmlFormat.empty)( {(a, b) => Html.apply(a.body + b.body) }))

  /** Java API */
  def foldJ(): F.Promise[Html] = F.Promise.wrap(fold())

}

/**
 * Companion object for HtmlStream that contains convenient factory and composition methods.
 */
object HtmlStream {

  /**
   * Create an HtmlStream from a String
   *
   * @param text
   * @return
   */
  def apply(text: String): HtmlStream = apply(Html.apply(text))

  def empty(): HtmlStream = apply(HtmlFormat.empty)

  /**
   * Create an HtmlStream from Html
   *
   * @param html
   * @return
   */
  def apply(html: Html): HtmlStream = HtmlStream(Enumerator(html))

  /**
   * Create an HtmlStream from a Future that will eventually contain Html
   *
   * @param eventuallyHtml
   * @return
   */
  def apply(eventuallyHtml: Future[Html]): HtmlStream = flatten(eventuallyHtml.map(html => apply(html)))

  def apply(eventuallyHtml: F.Promise[Html]): HtmlStream = apply(eventuallyHtml.wrapped())

  /**
   * Create an HtmlStream from the body of the SimpleResult.
   *
   * @param result
   * @return
   */
  def fromResult(result: Result): HtmlStream = HtmlStream(result.body.map(bytes => Html(new String(bytes, "UTF-8"))))

  /**
   * Create an HtmlStream from a the body of a Future[SimpleResult].
   *
   * @param result
   * @return
   */
  def fromResult(result: Future[Result]): HtmlStream = flatten(result.map(fromResult))

  /**
   * Interleave multiple HtmlStreams together. Interleaving is done based on whichever HtmlStream next has input ready,
   * if multiple have input ready, the order is undefined.
   *
   * @param streams
   * @return
   */
  def interleave(streams: HtmlStream*): HtmlStream = HtmlStream(Enumerator.interleave(streams.map(_.enumerator)))

  /**
   * Create an HtmlStream from a Future that will eventually contain an HtmlStream.
   *
   * @param eventuallyStream
   * @return
   */
  def flatten(eventuallyStream: Future[HtmlStream]): HtmlStream = {
    val fut: Future[Enumerator[Html]] = eventuallyStream.map(stream => stream.enumerator)

    HtmlStream(Enumerator.flatten(fut))
  }

  /**
   * Java API. Provides a convenience method for interleaving streams from a Java controller that doesn't rely on
   * scala's Seq
   *
   * @param streams
   * @return
   */
  def interleave(streams: java.util.List[HtmlStream]): HtmlStream = {
    import scala.collection.JavaConverters._
    HtmlStream(Enumerator.interleave(streams.asScala.map(_.enumerator)))
  }

  def interleaves(streams: HtmlStream*): HtmlStream = {
    HtmlStream(Enumerator.interleave(streams.toList.map(_.enumerator)))
  }

}

/**
 * A custom Format that lets us have .scala.stream templates instead of .scala.html. These templates can mix Html
 * markup with Enumerators that contain Html markup.
 */
object HtmlStreamFormat extends Format[HtmlStream] {

  def raw(text: String): HtmlStream = HtmlStream(text)

  def escape(text: String): HtmlStream = raw(HtmlFormat.escape(text).body)

  def empty: HtmlStream = raw("")

  def fill(elements: Seq[HtmlStream]): HtmlStream = elements.foldLeft(empty)((a, b) => a.andThen(b))

}

/**
 * Useful implicits when working with HtmlStreams
 */
object HtmlStreamImplicits {

  // Implicit conversion so HtmlStream can be passed directly to Ok.feed and Ok.chunked
  implicit def toEnumerator(stream: HtmlStream): Enumerator[Html] = {
    // Skip empty chunks, as these mean EOF in chunked encoding
    stream.enumerator.through(Enumeratee.filter(!_.body.isEmpty))
  }

}
