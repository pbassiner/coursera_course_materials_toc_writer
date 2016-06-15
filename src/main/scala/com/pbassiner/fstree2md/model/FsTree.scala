package com.pbassiner.fstree2md.model

import ammonite.ops.Path

/**
 * Created by pbassiner on 15/06/16.
 */
final case class FsTree(path: Path, children: Seq[FsTree])
