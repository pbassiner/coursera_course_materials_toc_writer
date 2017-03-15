package com.pbassiner.fstree2md.writer

import ammonite.ops._
import com.pbassiner.fstree2md.model.FsTree
import org.scalatest.{ FunSuite, Matchers }

/**
 * Created by pbassiner on 15/06/16.
 */
class MarkdownWriterSuite extends FunSuite with Matchers {

  val wd: Path = cwd / 'target / "scala-2.11" / "test-classes"
  val fixtures: Path = wd / 'fstree

  val fsTree: FsTree = FsTree(fixtures, Seq(
    FsTree(fixtures / "file0.mp4", Seq.empty[FsTree]),
    FsTree(fixtures / "file0.pdf", Seq.empty[FsTree]),
    FsTree(fixtures / "file0.srt", Seq.empty[FsTree]),
    FsTree(fixtures / "filename with spaces.html", Seq.empty[FsTree]),
    FsTree(fixtures / 'dir1, Seq(
      FsTree(fixtures / 'dir1 / "file1-1.mp4", Seq.empty[FsTree]),
      FsTree(fixtures / 'dir1 / "file1-1.pdf", Seq.empty[FsTree]),
      FsTree(fixtures / 'dir1 / "file1-1.srt", Seq.empty[FsTree])
    )),
    FsTree(fixtures / 'dir2, Seq(
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
    )),
    FsTree(fixtures / 'dir3, Seq(
      FsTree(fixtures / 'dir3 / "file3-1.mp4", Seq.empty[FsTree]),
      FsTree(fixtures / 'dir3 / "file3-1.pdf", Seq.empty[FsTree]),
      FsTree(fixtures / 'dir3 / "file3-1.srt", Seq.empty[FsTree]),
      FsTree(fixtures / 'dir3 / "dir3-1", Seq(
        FsTree(fixtures / 'dir3 / "dir3-1" / "file3-1-1.mp4", Seq.empty[FsTree]),
        FsTree(fixtures / 'dir3 / "dir3-1" / "file3-1-1.pdf", Seq.empty[FsTree]),
        FsTree(fixtures / 'dir3 / "dir3-1" / "file3-1-1.srt", Seq.empty[FsTree])
      ))
    ))
  ))

  val expectedMdFile = cwd / 'src / 'test / 'resources / "md.md"

  test("fstree") {
    new MarkdownWriter {
      override val mdFile = wd / "md.md"

      write(fsTree)

      read ! mdFile shouldEqual (read ! expectedMdFile)
    }
  }
}
