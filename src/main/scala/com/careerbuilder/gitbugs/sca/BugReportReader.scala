package com.careerbuilder.gitbugs.sca

import scala.xml.XML
import scala.xml.NodeSeq

object BugReportReader {
  
  //"loc"-> "100", "classes"->"200", "packages"-> "430", "hptotal"-> "20", "hpdensity"-> "6", "mptotal"-> "4", "mpdensity"-> "2"
 case class BugReportSummary(lines:Int,classes:Int, packages:Int, hptotal:Int,  mptotal:Int,categories:Array[CategoryAndSize])
  case class CategoryAndSize(name:String, size:Int)
  case class CategoryDescription(content:String,sourcePath:String)
  
 private def getBugInstances(xmlPath:String)= {}  
 def getSummary(xmlPath:String):BugReportSummary= {
    val xml = XML.loadFile(xmlPath)
     val bugsummary = xml \"FindBugsSummary" 
      val lines = (bugsummary\\"@total_size").head.text.toInt
     val classes = (bugsummary\\"@total_classes").head.text.toInt
     val packages =(bugsummary\\"@num_packages").head.text.toInt
          val bugInstances = xml  \ "BugInstance"
     val hp =bugInstances \\ "@priority" filter { _.text == "2" }  
   
     val mp =bugInstances \\ "@priority" filter { _.text == "1" }  
    BugReportSummary(lines,classes, packages, hp.size, mp.size,getCategories(bugInstances))
     
 } 
  
private def getCategories(bugInstances:NodeSeq) = {
     (bugInstances \\ "@category").map(_.text).groupBy { x => x }.map(f=> CategoryAndSize(f._1, f._2.size)).toArray
 } 
 def getCategory(xmlPath:String,category:String) = {
   
    val xml = XML.loadFile(xmlPath)
    val bugInstances = xml  \ "BugInstance"
    val categories =bugInstances filter( x=>(x \\ "@category").text ==category)
         
      categories.flatMap(x=> {
      
      val contentType =(x\\"@type").head.text
      (x\\"@sourcepath").map( x=> CategoryDescription(contentType, x.text))
      
    })
 } 

}