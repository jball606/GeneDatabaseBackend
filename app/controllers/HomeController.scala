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

            //val bob = new GeneData

            Ok("BOB")


  }

    def autoSuggestGene(gene:String): Action[AnyContent] = Action {

        val found_data: Seq[Gene] = geneData.findGeneRecords(gene)

        val test: Option[Seq[String]] = found_data.map(i => i.gene).sorted.distinct.grouped(5).toList.headOption
        if(test.nonEmpty) {
            val objects: Seq[JsObject] = test.get.map(i => Json.obj("value"->i,"label"->i))


            Ok(Json.toJson(objects))
        } else {
            Ok("[]")
        }

    }

    def getAllGeneNames: Action[AnyContent] = Action {
        val genes: Seq[String] = geneData.getAllGeneNames
        val objects: Seq[JsObject] = genes.map(i => Json.obj("id"->i,"name"->i))


        Ok(Json.toJson(objects))
    }

    def findGene(gene:String): Action[AnyContent] = Action {

        val found_data: Seq[Gene] = geneData.findGeneRecords(gene).sortBy(i => i.gene)

        val bob: JsValue = Json.toJson(found_data)
        Ok(bob)
    }


    def amIWorking: Action[AnyContent] = Action {
        Ok("YES")
    }

}
