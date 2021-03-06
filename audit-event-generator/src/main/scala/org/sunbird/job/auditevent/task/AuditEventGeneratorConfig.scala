package org.sunbird.job.auditevent.task

import java.util
import com.typesafe.config.Config
import org.apache.flink.api.common.typeinfo.TypeInformation
import org.apache.flink.api.java.typeutils.TypeExtractor
import org.apache.flink.streaming.api.scala.OutputTag
import org.sunbird.job.BaseJobConfig

class AuditEventGeneratorConfig(override val config: Config) extends BaseJobConfig(config, "audit-event-generator") {

  private val serialVersionUID = 2905979434303791379L

  implicit val mapTypeInfo: TypeInformation[util.Map[String, AnyRef]] = TypeExtractor.getForClass(classOf[util.Map[String, AnyRef]])
  implicit val stringTypeInfo: TypeInformation[String] = TypeExtractor.getForClass(classOf[String])

  // Kafka Topics Configuration
  val kafkaInputTopic: String = config.getString("kafka.input.topic")
  val kafkaOutputTopic: String = config.getString("kafka.output.topic")
  override val kafkaConsumerParallelism: Int = config.getInt("task.consumer.parallelism")
  override val parallelism: Int = config.getInt("task.parallelism")
  val kafkaProducerParallelism: Int = config.getInt("task.producer.parallelism")

  val auditOutputTag: OutputTag[String] = OutputTag[String]("audit-event-tag")

  val defaultChannel: String =config.getString("channel.default")

  // Metric List
  val totalEventsCount = "total-events-count"
  val successEventCount = "success-events-count"
  val failedEventCount = "failed-events-count"
  val skippedEventCount = "skipped-events-count"
  val emptySchemaEventCount = "empty-schema-events-count"
  val emptyPropsEventCount = "empty-props-events-count"

  // Consumers
  val auditEventConsumer = "audit-event-generator-consumer"
  val auditEventGeneratorFunction = "audit-event-generator-function"
  val auditEventProducer = "audit-event-generator-producer"

  val basePath = config.getString("schema.basePath")
  val configVersion = "1.0"
}
