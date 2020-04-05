// Copyright: 2017 - 2018 Sam Halliday, 2020 Zara Turtle
// License: https://firstdonoharm.dev/version/2/1/license.html

package fommil
package http

import prelude._

import jsonformat.JsDecoder
import eu.timepit.refined.string.Url

import http.encoding._

/**
 * An algebra for issuing basic GET / POST requests to a web server that returns
 * JSON. Errors are captured in the `F[_]`.
 */
trait JsonClient[F[_]] {

  def get[A: JsDecoder](
    uri: String Refined Url,
    headers: IList[(String, String)]
  ): F[A]

  // using application/x-www-form-urlencoded
  def post[P: UrlEncodedWriter, A: JsDecoder](
    uri: String Refined Url,
    payload: P,
    headers: IList[(String, String)]
  ): F[A]

}
object JsonClient extends JsonClientBoilerplate {
  sealed abstract class Error
  final case class ServerError(status: Int)       extends Error
  final case class DecodingError(message: String) extends Error
}
