import zio.*
import zio.stream.*
import zio.Console.printLine

import java.io.*
import zio.http.*
import zio.http.model.*
import fs2.io.file.{Files, Path}
import zio.stream.interop.fs2z.*

object Main extends ZIOAppDefault {
  val app: HttpApp[Any, Nothing] = Http.collect[Request] {
    case Method.GET -> !! / "text" =>
      val fs2Stream = fs2
        .Stream(1 to 100)
        .map(x => s"""{"name": "John","age": $x, "city": "New York"}""")
      val stream: ZStream[Any, Throwable, String] = fs2Stream.toZStream()
      val start: Chunk[Byte] = Chunk.fromArray("[".getBytes)
      val delimiter: Chunk[Byte] = Chunk.fromArray(",\n".getBytes)
      val end: Chunk[Byte] = Chunk.fromArray("]".getBytes)
      Response(
        Status.Ok,
        Headers(HeaderNames.contentType, HeaderValues.applicationJson),
        Body.fromStream(
          stream.map(x => Chunk.fromArray(x.getBytes)).intersperse(start, delimiter, end).flattenChunks
        )
      )
  }


  override def run: ZIO[Environment & ZIOAppArgs & Scope, Any, Any] = {
    Server.serve(app).provide(Server.default)
  }
}