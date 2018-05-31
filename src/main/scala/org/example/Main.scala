package org.example

import org.apache.flink.streaming.api.functions.sink.SinkFunction
import org.apache.flink.streaming.api.scala.DataStream
import org.codefeedr.pipeline.PipelineBuilder
import org.codefeedr.stages.OutputStage
import org.codefeedr.stages.utilities.{StringInput, StringType}

object Main {
  def main(args: Array[String]): Unit = {
    new PipelineBuilder()
      .append(new StringInput("Hello\n" +
        "World!\n" +
        "How\n" +
        "are\n" +
        "you\n" +
        "doing?"))
      .append (new StringTypePrintOutput)
      .build()
      .startMock()
  }
}

class StringTypePrintOutput extends OutputStage[StringType] {
  override def main(source: DataStream[StringType]): Unit = {
    source.addSink(new StringTypePrintSink)
  }
}

class StringTypePrintSink extends SinkFunction[StringType] {
  override def invoke(item: StringType): Unit = {
      println(item.value)
  }
}