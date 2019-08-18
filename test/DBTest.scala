import models.{Gene, GeneData}
import org.scalatestplus.play.PlaySpec
import play.api.inject.guice.GuiceApplicationBuilder



class DBTest extends PlaySpec {
    val app: GuiceApplicationBuilder = new GuiceApplicationBuilder()
    val geneData: GeneData = app.injector.instanceOf[GeneData]


    "AutoSuggest" must {
        "must find 5 items" in {
            val data: Option[Seq[String]] = geneData.autoSuggestRecords("BR")
            data.nonEmpty must be(true)
            data.get.length must be(5)
        }
        "must find no suggest" in {
            val data: Option[Seq[String]] = geneData.autoSuggestRecords("BRXXXXX")
            data.isEmpty must be(true)

        }
    }
    "Find Whole Records" must {
        "must find items" in {
            val data: Seq[Gene] = geneData.findGeneRecords("BR")
            data.nonEmpty must be(true)
            data.length > 2000 must be(true)
        }
        "must find no suggest" in {
            val data: Seq[Gene] = geneData.findGeneRecords("BRXXXXX")
            data.isEmpty must be(true)

        }
        "Validate a full Record" in {
            val data: Seq[Gene] = geneData.findGeneRecords("BRAF")
            data.nonEmpty must be(true)
            data.head.gene must be("BRAF")
            data.head.nucleotideChange.head must be("NM_004333.4:c.1068A>G")
        }
        "Consolidation worked" in {
            val data: Seq[Gene] = geneData.findGeneRecords("BRAF")
            val checks: Seq[Gene] = data.filter(i => i.nucleotideChange.length>1)
            checks.nonEmpty must be(true)
        }
    }


}
