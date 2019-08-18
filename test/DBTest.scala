import org.scalatestplus.play.PlaySpec
import play.api.inject.guice.GuiceApplicationBuilder
import play.api.inject.bind

class DBTest extends PlaySpec {
    val app: Application = new GuiceApplicationBuilder()
    val geneData: GeneData = app.injector.instanceOf[GeneData]
}
