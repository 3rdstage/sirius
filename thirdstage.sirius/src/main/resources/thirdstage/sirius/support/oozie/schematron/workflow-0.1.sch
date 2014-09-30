<?xml version="1.0" encoding="utf-8"?>
<schema xmlns="http://purl.oclc.org/dsdl/schematron">

   <ns uri="uri:oozie:workflow:0.2"/>
   <pattern>
      <rule context="action/ok[0]">
         <report test="@to/string() ne 'success'">
         The first ok element doesn't connect to success
         </report>
      </rule>
   </pattern>

</schema>