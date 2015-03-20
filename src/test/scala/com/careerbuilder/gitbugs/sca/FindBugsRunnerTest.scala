package com.careerbuilder.gitbugs.sca


import org.scalatest.Matchers
import org.scalatest.FunSuite
import org.scalatest.Matchers
import java.io.File
import java.io.BufferedReader
import java.io.InputStreamReader

class FindBugsRunnerTest extends FunSuite with Matchers {


  test("clone project test"){
  val runner = new FindBugsRunner("git@github.com:cbdr/SchoolServiceAPI.git")
  val file = new File("projects/" +runner.getProjectName())
  file.exists should equal(true)
  }
  
  
test("project name check"){
   val url ="git@github.com:cbdr/SchoolServiceAPI.git"
   val parts =url.substring(url.lastIndexOf("/")+1)
    
   parts.split("""\.""").head should equal("SchoolServiceAPI")
  }

  


}