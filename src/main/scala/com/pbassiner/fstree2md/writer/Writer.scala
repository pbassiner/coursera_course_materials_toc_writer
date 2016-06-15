package com.pbassiner.fstree2md.writer

import com.pbassiner.fstree2md.model.FsTree

/**
  * Created by pbassiner on 15/06/16.
  */
trait Writer {

  def write(fsTree: FsTree): Unit
}

class StdOutWriter extends Writer {

  override def write(fsTree: FsTree): Unit = ???
}
