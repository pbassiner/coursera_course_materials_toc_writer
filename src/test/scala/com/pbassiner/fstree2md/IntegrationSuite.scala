package com.pbassiner.fstree2md

import com.pbassiner.fstree2md.visitor.VisitorImpl
import org.scalatest.FunSuite

import ammonite.ops._

/**
 * Created by pbassiner on 15/06/16.
 */
class IntegrationSuite extends FunSuite {

  val wd: Path = cwd / 'target / "scala-2.11" / "test-classes"
  val fixtures: Path = wd / 'integration

  test("integration") {
    new VisitorImpl {
      visit(fixtures)
    }
  }
}
