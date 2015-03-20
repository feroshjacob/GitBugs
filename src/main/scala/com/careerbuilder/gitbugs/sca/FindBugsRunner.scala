package com.careerbuilder.gitbugs.sca

import grizzled.slf4j.Logger
import java.io.File
import java.io.BufferedReader
import java.io.InputStreamReader

trait Logging {
  @transient lazy val logger: Logger = Logger(getClass)
}
trait OutputXMLLocation {
  val projectsLocation = "projects"
    def getOutputFile(projectName:String):String= {
   //new File("lib").getAbsolutePath+ File.separator + "findbugs.jar"
  new File(projectsLocation).getAbsolutePath+ File.separator +projectName+"-OutPut.xml"
 }
}
class FindBugsRunner(url:String) extends Logging with OutputXMLLocation {

 
 
 run()
 
 def run()={
 //  cloneProject()
 //  buildProject()
   runFindBugs()
 }
 // java -jar lib/findbugs.jar -textui -output out.txt ~/workspaces/workspace-schools2/SchoolServiceAPI
 def runFindBugs() = {
       val commandList = List("java","-jar", getFindBugsJar(), "-textui", "-output", getOutputFile(),"-xml", getProjectLocation())
       logger.info(commandList.mkString(" "))
    runCommand(commandList, new File(getProjectLocation()))
 }
 
 def getFindBugsJar()= {
   //new File("lib").getAbsolutePath+ File.separator + "findbugs.jar"
   "/Users/fjacob/Downloads/findbugs-3.0.1/lib/findbugs.jar"
 }

 def getOutputFile():String= {
  getOutputFile(getProjectName())
 }
  def getProjectName()= {
    val parts =url.substring(url.lastIndexOf("/")+1)
    parts.split("""\.""").head
 }
  def getProjectLocation() = new File(projectsLocation).getAbsolutePath+ File.separator + getProjectName()
 
 def removeProjectFolder(directoryPath:String)= {
   val directory = new File(directoryPath)
   if(directory.exists()) directory.delete()
  
 }
 
  private def runCommand(commandList:List[String], workingDir:File)= {
    logger.info("executing command:" + commandList.mkString(" "))
   val r = Runtime.getRuntime();
    val p = r.exec(commandList.toArray, null, workingDir);
        //p.waitFor();
    val b = new BufferedReader(new InputStreamReader(p.getInputStream()));
    val b1 = new BufferedReader(new InputStreamReader(p.getErrorStream()));
    var line = "";
   var line1 = "";
    while (line!=null) {
      
     logger.info(line);
     line = b.readLine()
    }
      while (line1!=null) {
      
     logger.error(line1);
     line1 = b1.readLine()
    }
    b.close();
    b1.close();
  }
 
  def cloneProject() = {
   
    val commandList = List("git","clone", url)
    removeProjectFolder(getProjectLocation)
    runCommand(commandList,new File(projectsLocation))
   
  }
  def buildProject() = {
   logger.info("Building project at" + getProjectLocation())
    val commandList = List("mvn","compile")
    removeProjectFolder(getProjectLocation)
    runCommand(commandList, new File(getProjectLocation()))
   
  }
 
  
}