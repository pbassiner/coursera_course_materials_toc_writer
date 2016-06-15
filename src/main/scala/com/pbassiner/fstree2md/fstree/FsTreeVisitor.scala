package com.pbassiner.fstree2md.fstree

import ammonite.ops._
import com.pbassiner.fstree2md.model.FsTree

/**
 * Created by pbassiner on 15/06/16.
 */
trait FsTreeVisitor {

  def visit(path: Path): FsTree
}

trait FsTreeVisitorImpl extends FsTreeVisitor {

  override def visit(path: Path): FsTree = {
    visitRec(path, 0)
  }

  private[this] def visitRec(path: Path, depth: Int): FsTree = {
    val listed = ls ! path
    FsTree(path, listed.filter(interesting(_)).sortBy(_.isDir).map(_ match {
      case currentPath if currentPath.isDir => {
        visitRec(currentPath, depth + 1)
      }
      case currentPath if currentPath.isFile => FsTree(currentPath, Seq.empty[FsTree])
    }))
  }

  def interesting(p: Path): Boolean = !isHidden(p) && !isAssignmentFiles(p)

  def isHidden(p: Path): Boolean = p.last.startsWith(".")

  def isAssignmentFiles(p: Path): Boolean = p.segments.contains("assignment_files")

}
