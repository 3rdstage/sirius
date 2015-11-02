<?xml version="1.0" encoding="utf-8"?>
<schema xmlns="http://purl.oclc.org/dsdl/schematron">

   <ns uri="uri:oozie:workflow:0.2" prefix="wf"/>
   <pattern>
      <rule context="/wf:workflow-app/wf:action/wf:ok">
         <assert test=".[@to ne 'sqoopIncrImport']">
         Invalid target action name.
         </assert>
      </rule>
   </pattern>

</schema>