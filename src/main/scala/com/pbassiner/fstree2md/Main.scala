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
    assert(args.size > 2, "Usage: sbt \"run {rootDirectory} {targetMdFileRelativeToRootDir} {pathsToIgnore}\"")

    val wd: Path = Path(args(0))
    override val mdFile: Path = wd / args(1)

    val ignoredPaths = args(2).split(",").toSet

    val fsTree: FsTree = visit(wd, ignoredPaths)
    write(fsTree)
  }

}
