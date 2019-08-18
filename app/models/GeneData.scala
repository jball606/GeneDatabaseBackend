package models

import com.google.inject.Singleton
import play.api.libs.json.{JsObject, Json}

import scala.io.{BufferedSource, Source}



/**
 * Class for loading data at program start and searching
 */
class GeneData
{

    private val my_data: Seq[Gene] = loadData()

    /**
     * Method to do search for the genes I am looking for, based on name
     * @param request:String
     * @return
     */
    def findGeneRecords(request:String):Seq[Gene] = {

        val len: Int = request.length

        val found_data: Seq[Gene] = my_data.filter {
        search =>
            if(search.gene.length >= len) {
                search.gene.toLowerCase.substring(0, len) == request.toLowerCase
            } else {
                false
            }
    }

        found_data
    }

    /**
     * To make the auto suggest more testable
     * @param request:String
     * @return
     */
    def autoSuggestRecords(request:String):Option[Seq[String]] = {
        val found_data: Seq[Gene] = this.findGeneRecords(request)

        val test: Option[Seq[String]] = found_data.map(i => i.gene).sorted.distinct.grouped(5).toList.headOption

        return test
    }

    /**
     * For get the exact gene, only one
     * @param gene:String
     * @return
     */
    def getExactMatchGene(gene:String):Option[Gene] = {
        val data: Seq[Gene] = my_data.filter(_.gene == gene)
        data.headOption
    }

    /**
     * if you need a list of all the genes in the system
     * @return
     */
    def getAllGeneNames:Seq[String] = {

        my_data.map(_.gene).distinct
    }

    /**
     * Load data from file and put it into a name array, must consolidate data that is the same gene
     * @return
     */
    private def loadData(): Seq[Gene] = {
        val source: String = getClass.getResource("/variants.tsv").getPath
        val code: BufferedSource = Source.fromInputStream(getClass.getResourceAsStream("/variants.tsv"))


        val my_string: Seq[Array[String]] = code.mkString.split("\n").toSeq.map(gene => gene.split("\t"))
        val raw_genes: Seq[Gene] = my_string.tail.flatMap{
            i =>
                if(i.head == "") {
                    None
                } else {



                    Some(Gene(i.head,
                        getOrBlank(i,1),
                        i(2),
                        getOrBlank(i,3),
                        getOrBlank(i,4),
                        getOrBlank(i,5),
                        getOrBlank(i,6),
                        getOrBlank(i,7),
                        getOrBlank(i,8),
                        getOrBlank(i,9),
                        getOrBlank(i,10),
                        getOrBlank(i,11),
                        getOrBlank(i,12),
                        getOrBlank(i,13),
                        getOrBlank(i,14),
                        getOrBlank(i,15),
                        getOrBlank(i,16),
                        getOrBlank(i,17),
                        getOrBlank(i,18),
                        getOrBlank(i,19),
                        getOrBlank(i,20)


                    ))
                }
        }

        val gen_ids: Seq[String] = raw_genes.map(_.gene).distinct


        val data: Seq[Gene] = gen_ids.flatMap {
            gen_id =>
                //Find at the Gene Level
                val data: Seq[Gene] = raw_genes.filter(i => i.gene == gen_id)

                //Get the protein differences
                val proteins: Seq[String] = data.map(i => i.proteinChange).distinct



                val my_genes: Seq[Gene] = {
                    if(proteins.nonEmpty) {
                   //     data
                        proteins.map {
                            protein =>

                                val gene_protein_data: Seq[Gene] = {
                                    if(protein.isEmpty) {
                                        data.filter(_.proteinChange == "")
                                    } else {
                                        data.filter{
                                            i =>
                                                if(i.proteinChange == "") {
                                                    false
                                                } else {
                                                    if(i.proteinChange == protein) {
                                                        true
                                                    } else {
                                                        false
                                                    }
                                                }
                                        }
                                    }

                                }

                                Gene(
                                    data.head.gene,
                                    gene_protein_data.flatMap(_.nucleotideChange).distinct,
                                    protein,
                                    gene_protein_data.flatMap(_.otherMappings).distinct,
                                    gene_protein_data.flatMap(_.alias).distinct,
                                    gene_protein_data.flatMap(_.transcripts).distinct,
                                    gene_protein_data.flatMap(_.region).distinct,
                                    gene_protein_data.flatMap(_.reported).distinct,
                                    gene_protein_data.flatMap(_.inferredClassification).distinct,
                                    gene_protein_data.flatMap(_.source).distinct,
                                    gene_protein_data.flatMap(_.lastEvaluated).distinct,
                                    gene_protein_data.flatMap(_.lastUpdated).distinct,
                                    gene_protein_data.flatMap(_.url).distinct,
                                    gene_protein_data.flatMap(_.submitterComment).distinct,
                                    gene_protein_data.flatMap(_.assembly).distinct,
                                    gene_protein_data.flatMap(_.chr).distinct,
                                    gene_protein_data.flatMap(_.genomicStart).distinct,
                                    gene_protein_data.flatMap(_.genomicStop).distinct,
                                    gene_protein_data.flatMap(_.ref).distinct,
                                    gene_protein_data.flatMap(_.alt).distinct,
                                    gene_protein_data.flatMap(_.accession).distinct,
                                    gene_protein_data.flatMap(_.reportedRef).distinct,
                                    gene_protein_data.flatMap(_.reportedAlt).distinct,
                                )
                        }


                    } else {
                        data
                    }
                }



                my_genes


        }

        data
    }

    /**
     * Not every row in the TSV have 21 columns, these helps to clean that up
     * @param array:Array[String]
     * @param index:Int
     * @return
     */
    private def getOrBlank(array:Array[String],index:Int): Seq[String] = {
        if(array.length > index){
            val value: String = array(index)
            if(value == "" || value == "-") {
                Seq()
            } else {
                Seq(value)
            }

        } else {
            Seq()
        }
    }
}
