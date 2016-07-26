package com.pbassiner.fstree2md.fstree

import ammonite.ops._
import com.pbassiner.fstree2md.model.FsTree
import org.scalatest.{ FunSuite, Matchers }

/**
 * Created by pbassiner on 15/06/16.
 */
class FsTreeVisitorSuite extends FunSuite with Matchers {

  val wd: Path = cwd / 'target / "scala-2.11" / "test-classes"
  val fixtures: Path = wd / 'fstree

  val dir1FsTree = FsTree(fixtures / 'dir1, Seq(
    FsTree(fixtures / 'dir1 / "file1-1.mp4", Seq.empty[FsTree]),
    FsTree(fixtures / 'dir1 / "file1-1.pdf", Seq.empty[FsTree]),
    FsTree(fixtures / 'dir1 / "file1-1.srt", Seq.empty[FsTree])
  ))

  val dir2FsTree = FsTree(fixtures / 'dir2, Seq(
    FsTree(fixtures / 'dir2 / "file2-1.html", Seq.empty[FsTree]),
    FsTree(fixtures / 'dir2 / "file2-1.md", Seq.empty[FsTree]),
    FsTree(fixtures / 'dir2 / "file2-1.mp4", Seq.empty[FsTree]),
    FsTree(fixtures / 'dir2 / "file2-1.pdf", Seq.empty[FsTree]),
    FsTree(fixtures / 'dir2 / "file2-1.srt", Seq.empty[FsTree]),
    FsTree(fixtures / 'dir2 / "file2-1.txt", Seq.empty[FsTree]),
    FsTree(fixtures / 'dir2 / "file2-1.zip", Seq.empty[FsTree]),
    FsTree(fixtures / 'dir2 / "file2-2.mp4", Seq.empty[FsTree]),
    FsTree(fixtures / 'dir2 / "file2-2.pdf", Seq.empty[FsTree]),
    FsTree(fixtures / 'dir2 / "file2-2.srt", Seq.empty[FsTree])
  ))

  val dir3FsTree = FsTree(fixtures / 'dir3, Seq(
    FsTree(fixtures / 'dir3 / "file3-1.mp4", Seq.empty[FsTree]),
    FsTree(fixtures / 'dir3 / "file3-1.pdf", Seq.empty[FsTree]),
    FsTree(fixtures / 'dir3 / "file3-1.srt", Seq.empty[FsTree]),
    FsTree(fixtures / 'dir3 / "dir3-1", Seq(
      FsTree(fixtures / 'dir3 / "dir3-1" / "file3-1-1.mp4", Seq.empty[FsTree]),
      FsTree(fixtures / 'dir3 / "dir3-1" / "file3-1-1.pdf", Seq.empty[FsTree]),
      FsTree(fixtures / 'dir3 / "dir3-1" / "file3-1-1.srt", Seq.empty[FsTree])
    ))
  ))

  val fsTree = FsTree(fixtures, Seq(
    FsTree(fixtures / "file0.mp4", Seq.empty[FsTree]),
    FsTree(fixtures / "file0.pdf", Seq.empty[FsTree]),
    FsTree(fixtures / "file0.srt", Seq.empty[FsTree]),
    dir1FsTree,
    dir2FsTree,
    dir3FsTree
  ))

  test("Build FsTree from Path") {
    new FsTreeVisitorImpl {
      val actual = visit(fixtures, Set.empty[String])

      actual shouldEqual (fsTree)
    }
  }

  test("Build FsTree from Path with ignored paths") {
    new FsTreeVisitorImpl {
      val actual = visit(fixtures, Set("dir2"))

      val expected = FsTree(fixtures, Seq(
        FsTree(fixtures / "file0.mp4", Seq.empty[FsTree]),
        FsTree(fixtures / "file0.pdf", Seq.empty[FsTree]),
        FsTree(fixtures / "file0.srt", Seq.empty[FsTree]),
        dir1FsTree,
        dir3FsTree
      ))

      actual shouldEqual (expected)
    }
  }
}
