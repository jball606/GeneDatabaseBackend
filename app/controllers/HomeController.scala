package controllers

import com.fasterxml.jackson.databind.JsonNode
import javax.inject._
import models.{Gene, GeneData}
import play.api.libs.json.{JsObject, JsValue, Json}
import play.api.mvc._

import scala.collection.immutable
import scala.io.{BufferedSource, Source}

/**
 * This controller creates an `Action` to handle HTTP requests to the
 * application's home page.
 */

class HomeController @Inject()(cc: ControllerComponents  , geneData: GeneData ) extends AbstractController(cc) {

  /**
   * Create an Action to render an HTML page.
   *
   * The configuration in the `routes` file means that this method
   * will be called when the application receives a `GET` request with
   * a path of `/`.
   */
    def index(): Action[AnyContent] = Action { implicit request: Request[AnyContent] =>

            Ok("I am now working and ready to go")
    }

    /**
     * This is the route that will let you get any auto suggest you want based on a request
     * @param gene
     * @return
     */
    def autoSuggestGene(gene:String): Action[AnyContent] = Action {

        val test: Option[Seq[String]] = geneData.autoSuggestRecords(gene)

        if(test.nonEmpty) {
            val objects: Seq[JsObject] = test.get.map(i => Json.obj("value"->i,"label"->i))
            Ok(Json.toJson(objects))
        } else {
            Ok("[]")
        }

    }

    /**
     * Gets all the gene names, in cause you want to pre-fill the auto suggest
     * @return
     */
    def getAllGeneNames: Action[AnyContent] = Action {
        val genes: Seq[String] = geneData.getAllGeneNames
        val objects: Seq[JsObject] = genes.map(i => Json.obj("id"->i,"name"->i))


        Ok(Json.toJson(objects))
    }

    /**
     * Here is the one for finding the Genes, with the proten marker being the second unique identifier
     * @param gene
     * @return
     */
    def findGene(gene:String): Action[AnyContent] = Action {

        val found_data: Seq[Gene] = geneData.findGeneRecords(gene).sortBy(i => i.gene)

        val bob: JsValue = Json.toJson(found_data)
        Ok(bob)
    }

    /**
     * Have to be able to have nagios hit this thing to make sure it is up
     * @return
     */
    def amIWorking: Action[AnyContent] = Action {
        Ok("YES")
    }

}
