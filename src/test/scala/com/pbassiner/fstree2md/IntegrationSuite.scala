package com.pbassiner.fstree2md

import com.pbassiner.fstree2md.fstree.FsTreeVisitorImpl
import org.scalatest.{ FunSuite, Matchers }
import ammonite.ops._
import com.pbassiner.fstree2md.model.FsTree

/**
 * Created by pbassiner on 15/06/16.
 */
class IntegrationSuite extends FunSuite with Matchers {

  val wd: Path = cwd / 'target / "scala-2.11" / "test-classes"
  val fixtures: Path = wd / 'integration

  val fsTree: FsTree = FsTree(fixtures, Seq(
    FsTree(fixtures / "file0.mp4", Seq.empty[FsTree]),
    FsTree(fixtures / "file0.pdf", Seq.empty[FsTree]),
    FsTree(fixtures / "file0.srt", Seq.empty[FsTree]),
    FsTree(fixtures / 'dir1, Seq(
      FsTree(fixtures / 'dir1 / "file1-1.mp4", Seq.empty[FsTree]),
      FsTree(fixtures / 'dir1 / "file1-1.pdf", Seq.empty[FsTree]),
      FsTree(fixtures / 'dir1 / "file1-1.srt", Seq.empty[FsTree])
    )),
    FsTree(fixtures / 'dir2, Seq.empty[FsTree]),
    FsTree(fixtures / 'dir3, Seq.empty[FsTree])
  ))

  test("integration") {
    new FsTreeVisitorImpl {
      val actual = visit(fixtures)

      actual shouldEqual (fsTree)
    }
  }
}
