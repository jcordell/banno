package banno.banno.util

import cats.effect.Concurrent
import io.circe.{Decoder, Encoder}
import org.http4s.circe.{jsonEncoderOf, jsonOf}
import org.http4s.{EntityDecoder, EntityEncoder}

object ImplicitEntityCodecs {
  implicit def jsonDecoder[F[_]: Concurrent, A: Decoder]: EntityDecoder[F, A] =
    jsonOf[F, A]

  implicit def jsonEncoder[F[_], A: Encoder]: EntityEncoder[F, A] =
    jsonEncoderOf[F, A]

}
