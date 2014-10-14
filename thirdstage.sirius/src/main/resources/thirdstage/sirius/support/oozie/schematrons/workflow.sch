<?xml version="1.0" encoding="utf-8"?>

<schema xmlns="http://purl.oclc.org/dsdl/schematron">

   <!-- 
   Do NOT use other namespaces. 
   With elements from other namespaces such as dublin core, SchematronResourceSch
   would throw exception reclaiming invalid scheamtron, although SchematronResourcePure
   can treat them correctrly
   -->
   <!-- 
   
   * title : Schematron schema for Oozie workflow definition
   * creator : Oh Sangmoon
   * version : 0.9, 2014-10-13
   * subject : Rules for Oozie workflow definition
   * type : Schematron schema
   * summary
      Structure
         Oozie Job = Workflow | Coordinator
         Workflow = Workflow Node*
         Workflow Node = Control Node | Action Node
         Control Node = Start | Decision | Fork | Join | Kill | End
         Coordinator Predicate = Data Predicate | Time Predicate | Event Predicate

      Constraints
         Isolated node is NOT allowed
         Broken link is NOT allowed 
            start/@to
            action/ok/@to
            action/error/@to
            decision/switch/case/@to
            decision/switch/default/@to
            fork/path/@start
            join/@to

         Self recursion is NOT allowed
            action/ok/@to != action/@name ???
            action/error/@to != action/@name ???
            decision/switch/case/@to != decision/switch/@name
            decision/switch/default/@to != decision/switch/@name
            fork/path/@start != fork/@name
            join/@to != @join/@name
         
         Uniqueness
            node name
            fork/path/@start in the fork   
            decision/switch/case/@to in the decision - duplication may be allowed
   -->

   <ns prefix="saxon" uri="http://saxon.sf.net/" />

   <!-- global variables -->
   <let name="root" value="/*[local-name()='workflow-app']"/>

   <let name="nodeTags" value="('action', 'decision', 'fork', 'join', 'kill', 'end')" /> <!-- exclude start which never has name attrib -->
   <let name="nodeNames" value="/*[local-name()='workflow-app']/*[exists(index-of($nodeTags, local-name()))]/@name/string()" />
   
   <let name="startTo" 
      value="$root/*[local-name()='start']/@to/string()"/>
   <let name="actionOkTo" 
      value="$root/*[local-name()='action']/*[local-name()='ok']/@to/string()"/>
   <let name="actionErrorTo" 
      value="$root/*[local-name()='action']/*[local-name()='error']/@to/string()"/>
   <let name="decisionCaseTo"
      value="$root/*[local-name()='decision']/*[local-name()='switch']/*[local-name()='case']/@to/string()"/>
   <let name="decisionDefaultTo"
      value="$root/*[local-name()='decision']/*[local-name()='switch']/*[local-name()='default']/@to/string()"/>      
   <let name="forkPathStart" 
      value="$root/*[local-name()='fork']/*[local-name()='path']/@start/string()"/>
   <let name="joinTo" 
      value="$root/*[local-name()='join']/@to/string()" />
   <let name="wantedNodeNames" 
         value="($startTo, $joinTo, $forkPathStart, $decisionCaseTo, $decisionDefaultTo, $actionOkTo, $actionErrorTo)"/>

   <pattern id='node-target'>
      <rule context="/*[local-name()='workflow-app']/*[local-name()='start']"
            role="start-target-existence">
         <assert test="exists(index-of($nodeNames, ./@to/string()))"
            role="start-target-existence"
            subject=".">
            start/@to node has non-existing node name of '<value-of select="./@to/string()" />'.
            nodeNames : <value-of select="string-join($nodeNames, ', ')"/>
            wantedNodeNames : <value-of select="string-join($wantedNodeNames, ', ')"/>
         </assert>
      </rule>
      <rule context="/*[local-name()='workflow-app']/*[local-name()='action']/*[local-name()='ok']"
            role="action-ok-target-existence">
         <assert test="exists(index-of($nodeNames, ./@to/string()))"
            role="action-ok-target-existence"
            subject=".">
            action[@name='<value-of select="../@name/string()"/>']/ok/@to has non-existing node name of '<value-of select="./@to/string()" />'.
         </assert>
      </rule>
      <rule context="/*[local-name()='workflow-app']/*[local-name()='action']/*[local-name()='error']"
            role="action-error-target-existence">
         <assert test="exists(index-of($nodeNames, ./@to/string()))"
            role="action-error-target-existence"
            subject=".">
            action[@name='<value-of select="../@name/string()"/>']/error/@to has non-existing node name of '<value-of select="./@to/string()" />'.
         </assert>
      </rule>
      <rule context="/*[local-name()='workflow-app']/*[local-name()='decision']/*[local-name()='switch']/*[local-name()='case']"
            role="decision-case-target-existence">
         <assert test="exists(index-of($nodeNames, ./@to/string()))"
            role="decision-case-target-existence"
            subject=".">
            decision[@name='<value-of select="../../@name/string()"/>']/switch/case/@to has non-existing node name of '<value-of select="./@to/string()" />'.
         </assert>
      </rule>
      <rule context="/*[local-name()='workflow-app']/*[local-name()='decision']/*[local-name()='switch']/*[local-name()='default']"
            role="decision-default-target-existence">
         <assert test="exists(index-of($nodeNames, ./@to/string()))"
            role="decision-default-target-existence"
            subject=".">
            decision[@name='<value-of select="../../@name/string()"/>']/switch/default/@to has non-existing node name of '<value-of select="./@to/string()" />'.
         </assert>
      </rule>
      
      <rule context="/*[local-name()='workflow-app']/*[local-name()='fork']/*[local-name()='path']"
            role="fork-path-target-existence">
         <assert test="exists(index-of($nodeNames, ./@start/string()))"
            role="fork-path-target-existence"
            subject=".">
            fork[@name='<value-of select="../@name/string()"/>']/path/@start has non-existing node name of '<value-of select="./@start/string()" />'.
         </assert>
      </rule>
      <rule context="/*[local-name()='workflow-app']/*[local-name()='join']"
            role="join-target-existence">
         <assert test="exists(index-of($nodeNames, ./@to/string()))"
            role="join-target-existence"
            subject=".">
            join[@name='<value-of select="./@name/string()"/>']/@to has non-existing node name of '<value-of select="./@to/string()" />'.
         </assert>
      </rule>      
   </pattern>
   
   <pattern id="node-name">
      <rule context="/*[local-name()='workflow-app']/*[exists(index-of($nodeTags, local-name()))]">
         <assert test="exists(index-of($wantedNodeNames, ./@name/string()))"
            role="node-unisolated" subject=".">
            <value-of select="node-name(.)"/>[@name='<value-of select="./@name/string()"/>'] is isolated (not linked with any other node in the workflow).
         </assert>
         
         <assert test="count(index-of($nodeNames, ./@name/string())) le 1"
            role="node-name-unduplicated" subject=".">
            <value-of select="node-name(.)"/>[@name='<value-of select="./@name/string()"/>'] has duplicated name with other nodes. (Node name should be unique.)   
         </assert>
      </rule>
   </pattern>
   
    <pattern id="decision">
      <!-- duplication may be allowed -->
      <rule context="/*[local-name()='workflow-app']/*[local-name()='decision']">
         <let name="decisionCaseLocal" value="./*[local-name()='switch']/*[local-name()='case']/@to/string()"/>
         <let name="duplicateDecisionCaseLocal" value="distinct-values(for $x in $decisionCaseLocal return (if (exists(index-of($decisionCaseLocal, $x)[2])) then $x else ()))"/>
         <assert test="count($duplicateDecisionCaseLocal) eq 0"
            role="decision-case-unduplicated">
            decision[@name='<value-of select="./@name/string()"/>'] has duplicated case :  <value-of select="string-join($duplicateDecisionCaseLocal, ', ')"/>
         </assert>
      </rule>
   </pattern>
 
  
   <pattern id="fork">
      <rule context="/*[local-name()='workflow-app']/*[local-name()='fork']">
         <let name="pathStartLocal" value="./*[local-name()='path']/@start/string()"/>
         <let name="duplicatePathStartLocal" value="distinct-values(for $x in $pathStartLocal return (if (exists(index-of($pathStartLocal, $x)[2])) then $x else ()))"/>
         <assert test="count($duplicatePathStartLocal) eq 0"
            role="fork-path-unduplicated">
            fork[@name='<value-of select="./@name/string()"/>'] has duplicated case : <value-of select="string-join($duplicatePathStartLocal, ', ')"/>
         </assert>
      </rule>
   </pattern> 
</schema>
