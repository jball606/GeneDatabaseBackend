package models

import play.api.libs.json.{Json, OFormat}
import ai.x.play.json.Jsonx

case class Gene(
    gene:String,
    nucleotideChange:Seq[String] = Seq(),
    proteinChange:String = "",
    otherMappings:Seq[String] = Seq(),
    alias:Seq[String] = Seq(),
    transcripts:Seq[String] = Seq(),
    region:Seq[String] = Seq(),
    reported:Seq[String] = Seq(),
    inferredClassification:Seq[String] = Seq(),
    source:Seq[String] = Seq(),
    lastEvaluated:Seq[String] = Seq(),
    lastUpdated:Seq[String] = Seq(),
    url:Seq[String] = Seq(),
    submitterComment:Seq[String] = Seq(),
    assembly:Seq[String] = Seq(),
    chr:Seq[String] = Seq(),
    genomicStart:Seq[String] = Seq(),
    genomicStop:Seq[String] = Seq(),
    ref:Seq[String] = Seq(),
    alt:Seq[String] = Seq(),
    accession:Seq[String] = Seq(),
    reportedRef:Seq[String] = Seq(),
    reportedAlt:Seq[String] = Seq()
)
object Gene {
    implicit val format: OFormat[Gene] = Jsonx.formatCaseClass[Gene]
}