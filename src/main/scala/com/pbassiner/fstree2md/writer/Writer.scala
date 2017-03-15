package com.pbassiner.fstree2md.writer

import ammonite.ops._
import ammonite.ops.write.append
import com.pbassiner.fstree2md.model.FsTree

/**
 * Created by pbassiner on 15/06/16.
 */
trait Writer {

  def write(fsTree: FsTree): Unit
}

trait MarkdownWriter extends Writer {

  val mdFile: Path

  override def write(fsTree: FsTree): Unit = {
    rm ! mdFile
    ammonite.ops.write(mdFile, s"# ${fsTree.path.last}\n---\n")

    writeRec(fsTree.path, fsTree, 0)
  }

  private[this] def writeRec(root: Path, fsTree: FsTree, depth: Int): Unit = {
    writeFiles(root, fsTree.path, depth)
    fsTree.children.foreach(_ match {
      case FsTree(p, c) if p.isDir => {
        writeDirName(p, depth)
        writeRec(root, FsTree(p, c), depth + 1)
      }
      case _ =>
    })
  }

  private[this] def writeDirName(p: Path, depth: Int): Unit = append(mdFile, s"${"\t" * depth}* ${p.last}\n")

  private[this] def writeFiles(root: Path, p: Path, depth: Int): Unit = {
    val files = ls ! p filter (x => x.isFile && x != mdFile)
    val grouped = files.groupBy(filenameWithoutExtension(_))
    grouped.toList.sortBy(_._1).foreach(x => append(mdFile, s"${"\t" * depth}* ${x._1} ${buildLinks(root, x._2)}\n"))
  }

  private[this] def filenameWithoutExtension(p: Path): String = {
    val split = p.last.split("\\.")
    split.take(split.length - 1).mkString(".")
  }

  private[this] def fileExtension(p: Path): String = {
    val split = p.last.split("\\.")
    split(split.length - 1)
  }

  private[this] def buildLinks(root: Path, files: Seq[Path]): String = {
    val buf = new StringBuilder
    buf.append("(")
    val refs = files.map(file => buildRef(root, file)).toList.sortBy(_.fileType.priority)
    buf.append(refs.map(toString(_)).mkString(", "))
    buf.append(")")
    buf.toString
  }

  private[this] def buildRef(root: Path, file: Path): Ref = Ref(fileExtension(file), file.relativeTo(root).toString.replaceAll("\\s", "%20"))

  private[this] final case class Ref(fileType: FileType, link: String)

  private[this] sealed trait FileType {
    val priority: Int
  }

  private[this] case object Markdown extends FileType { override val priority = 0 }
  private[this] case object Slides extends FileType { override val priority = 1 }
  private[this] case object Video extends FileType { override val priority = 2 }
  private[this] case object Subtitles extends FileType { override val priority = 3 }
  private[this] case object Html extends FileType { override val priority = 4 }
  private[this] case object Text extends FileType { override val priority = 5 }
  private[this] case object Zip extends FileType { override val priority = 6 }

  import scala.language.implicitConversions

  private[this] implicit def toFileType(extension: String): FileType = extension match {
    case "md" => Markdown
    case "pdf" => Slides
    case "mp4" => Video
    case "srt" => Subtitles
    case "html" => Html
    case "txt" => Text
    case "zip" => Zip
  }

  private[this] def toString(ref: Ref): String = s"[${ref.fileType}](${ref.link})"
}
