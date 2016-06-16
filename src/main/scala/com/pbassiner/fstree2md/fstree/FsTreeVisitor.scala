package com.pbassiner.fstree2md.fstree

import ammonite.ops._
import com.pbassiner.fstree2md.model.FsTree

/**
 * Created by pbassiner on 15/06/16.
 */
trait FsTreeVisitor {

  def visit(path: Path, ignored: Set[String]): FsTree
}

trait FsTreeVisitorImpl extends FsTreeVisitor {

  override def visit(path: Path, ignored: Set[String]): FsTree = {
    visitRec(path, ignored, 0)
  }

  private[this] def visitRec(path: Path, ignored: Set[String], depth: Int): FsTree = {
    val listed = ls ! path
    FsTree(path, listed.filter(interesting(_, ignored)).sortBy(_.isDir).map(_ match {
      case currentPath if currentPath.isDir => {
        visitRec(currentPath, ignored: Set[String], depth + 1)
      }
      case currentPath if currentPath.isFile => FsTree(currentPath, Seq.empty[FsTree])
    }))
  }

  private[this] def interesting(p: Path, ignored: Set[String]): Boolean = !isHidden(p) && !isIgnored(p, ignored)

  private[this] def isHidden(p: Path): Boolean = p.last.startsWith(".")

  private[this] def isIgnored(p: Path, ignored: Set[String]): Boolean = !p.segments.toSet.intersect(ignored).isEmpty

}
