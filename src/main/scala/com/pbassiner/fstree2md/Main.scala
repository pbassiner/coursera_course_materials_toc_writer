package main.com.pbassiner.fstree2md

import ammonite.ops._
import com.pbassiner.fstree2md.visitor.VisitorImpl

/**
 * Created by pbassiner on 15/06/16.
 */
object Main extends VisitorImpl {
  def main(args: Array[String]): Unit = {
    val wd = args(0)
    visit(Path(wd))
  }

}
