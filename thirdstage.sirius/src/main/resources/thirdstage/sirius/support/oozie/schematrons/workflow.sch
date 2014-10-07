<?xml version="1.0" encoding="utf-8"?>

<schema xmlns="http://purl.oclc.org/dsdl/schematron" xmlns:dc="http://purl.org/dc/elements/1.1/">

   <dc:title>Schematron schema for Oozie workflow definition</dc:title>
   <dc:creator>Oh Sangmoon</dc:creator>
   <dc:date>2014-10-02</dc:date>
   <dc:language>en</dc:language>
   <dc:subject>Rules for Oozie workflow definition</dc:subject>
   <dc:type>Schematron schema</dc:type>
   <dc:description>
      Structure
         Oozie Job = Workflow | Coordinator
         Workflow = Workflow Node*
         Workflow Node = Control Node | Action Node
         Control Node = Start | Decision | Fork | Join | Kill | End
         Coordinator Predicate = Data Predicate | Time Predicate | Event Predicate

      Constraints
         No broken link neither isolated node
            start/@to, decision/switch/case/@to, decision/switch/default/@to, fork/path/@start, join/@to, action/ok/@to, action/error/@to

         No loop in danger
            join/@to != @join/@name
            fork/path/@start != fork/@name
            decision/switch/case/@to != decision/switch/@name
            decision/switch/default/@to != decision/switch/@name
            action/ok/@to != action/@name ???
            action/error/@to != action/@name ???
         
         Uniqueniss
            node name
            fork/path/@start
   </dc:description>

   <ns prefix="saxon" uri="http://saxon.sf.net/" />

   <!-- global variables -->
   <let name="root" value="/*[local-name()='workflow-app']"/>

   <let name="nodeTags" value="('action', 'decision', 'fork', 'join', 'kill', 'end')" /> <!-- exclude start which never has name attrib -->
   <let name="nodeNames" value="/*[local-name()='workflow-app']/*[index-of($nodeTags, local-name()) > 0]/@name/string()" />
   
   <let name="startTo" 
      value="/*[local-name()='workflow-app']/*[local-name()='start']/@to/string()"/>
   <let name="joinTo" 
      value="/*[local-name()='workflow-app']/*[local-name()='join']/@to/string()" />
   <let name="forkPathStart" 
      value="/*[local-name()='workflow-app']/*[local-name()='fork']/*[local-name()='path']/@start/string()"/>
   <let name="decisionCaseTo"
      value="/*[local-name()='workflow-app']/*[local-name()='decision']/*[local-name()='switch']/*[local-name()='case']/@to/string()"/>
   <let name="decisionDefaultTo"
      value="/*[local-name()='workflow-app']/*[local-name()='decision']/*[local-name()='switch']/*[local-name()='default']/@to/string()"/>      
   <let name="actionOkTo" 
      value="/*[local-name()='workflow-app']/*[local-name()='action']/*[local-name()='ok']/@to/string()"/>
   <let name="actionErrorTo" 
      value="/*[local-name()='workflow-app']/*[local-name()='action']/*[local-name()='error']/@to/string()"/>
   <let name="wantedNodeNames" 
         value="($startTo, $joinTo, $forkPathStart, $decisionCaseTo, $decisionDefaultTo, $actionOkTo, $actionErrorTo)"/>

   <pattern id='linkage'>
      <rule context="/*[local-name()='workflow-app']/*[local-name()='action']/*[local-name()='ok']/@to">
         <assert test=".[index-of($nodeNames, string(.)) lt 1]">
            Non-existing node name '<value-of select="string(.)" />' at  '<value-of select="./parent::node()/name()"/>'
            Number of nodeNames : <value-of select="count($nodeNames)"/>
            Number of wantedNodeNames : <value-of select="string-join($wantedNodeNames, ' ')"/>
         </assert>
      </rule>
   </pattern>

</schema>
