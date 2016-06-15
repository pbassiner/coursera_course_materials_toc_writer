package com.pbassiner.fstree2md.fstree

import ammonite.ops._
import com.pbassiner.fstree2md.model.FsTree

/**
 * Created by pbassiner on 15/06/16.
 */
trait FsTreeVisitor {

  def visit(path: Path): FsTree
}

class FsTreeVisitorImpl extends FsTreeVisitor {

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

  //writeToc(path, 0)

  def writeToc(path: Path, depth: Int): Unit = {
    val listed = ls ! path
    listed.filter(interesting(_)).foreach(_ match {
      case currentPath if currentPath.isDir => {
        writeDirName(currentPath, depth)
        val files = listFiles(currentPath)
        writeFiles(files, depth + 1)
        writeToc(currentPath, depth + 1)
      }
      case _ =>
    })
  }

  def listFiles(p: Path): Seq[Path] = ls ! p filter (_.isFile)

  def interesting(p: Path): Boolean = !isHidden(p) && !isAssignmentFiles(p)

  def isHidden(p: Path): Boolean = p.last.startsWith(".")

  def isAssignmentFiles(p: Path): Boolean = p.segments.contains("assignment_files")

  def writeDirName(p: Path, depth: Int): Unit = println(s"${"\t" * depth}* ${p.last}")

  def writeFiles(files: Seq[Path], depth: Int): Unit = {
    val grouped = files.groupBy(filenameWithoutExtension(_))
    grouped.foreach(x => println(s"${"\t" * depth}* ${x._1} -> ${x._2.size}"))
  }

  def filenameWithoutExtension(p: Path): String = {
    val splitted = p.last.split("\\.")
    splitted.take(splitted.length - 1).mkString(".")
  }
}
