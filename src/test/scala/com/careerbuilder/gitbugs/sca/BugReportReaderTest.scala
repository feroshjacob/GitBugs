package com.careerbuilder.gitbugs.sca

import org.scalatest.FunSuite
import org.scalatest.Matchers
import scala.xml.XML
import scala.xml.Node

class BugReportReaderTest  extends FunSuite  with Matchers { 
  
  test("bug report reader"){
    
      val summary = BugReportReader.getSummary("projects/SchoolServiceAPI-OutPut.xml")
      
     println(summary.categories.mkString(" "))
     
  }

    test("bug category reader"){
    
      val summary = BugReportReader.getCategory("projects/SchoolServiceAPI-OutPut.xml", "STYLE")
      
     println(summary.mkString(" "))
     
  } 

}