package main.com.pbassiner.fstree2md

import ammonite.ops._
import com.pbassiner.fstree2md.fstree.FsTreeVisitorImpl
import com.pbassiner.fstree2md.model.FsTree
import com.pbassiner.fstree2md.writer.MarkdownWriter

/**
 * Created by pbassiner on 15/06/16.
 */
object Main {
  def main(args: Array[String]): Unit = new FsTreeVisitorImpl with MarkdownWriter {
    assert(args.size > 0, "Need to specify the root directory")

    val wd: Path = Path(args(0))
    override val mdFile: Path = wd / "README.md"

    val fsTree: FsTree = visit(wd)
    write(fsTree)
  }

}
