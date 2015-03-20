package com.careerbuilder.gitbugs

import org.scalatra._
import scalate.ScalateSupport
import com.careerbuilder.gitbugs.sca.FindBugsRunner
import com.careerbuilder.gitbugs.sca.BugReportReader._
import com.careerbuilder.gitbugs.sca.OutputXMLLocation


class AnalyzeProject extends GitbugsStack with OutputXMLLocation {
 
  get("/") {
   contentType="text/html"

  ssp("/index", "title"-> "Home Page  ")
  }
  post("/analyze") {
   contentType="text/html"
   val runner = new FindBugsRunner(params("githuburl"))
   val summary = getSummary(runner.getOutputFile())
   val projectName =runner.getProjectName()
  
   ssp("analyze", "loc"-> summary.lines, "classes"->summary.classes, "packages"-> summary.packages, "projectName"-> projectName,
         "hptotal"-> summary.hptotal,  "mptotal"-> summary.mptotal, "categories"-> summary.categories)

  }
    get("/category") {
   contentType="text/html"
  
   val categories = getCategory(getOutputFile(params("project")),params("category")).toArray
  
   ssp("/category", "categories"-> categories)

  }
   
   
}
