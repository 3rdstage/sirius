<?xml version="1.0" encoding="utf-8"?>
<schema xmlns="http://purl.oclc.org/dsdl/schematron">

   <pattern>
      <rule context="/*[local-name()='workflow-app']/*[local-name()='action']/*[local-name()='ok']">
         <assert test=".[@to ne 'sqoopIncrImport']">
         Invalid target action name.
         </assert>
      </rule>
   </pattern>

</schema>