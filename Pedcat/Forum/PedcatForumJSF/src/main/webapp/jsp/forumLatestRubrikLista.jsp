<%@ taglib uri="http://myfaces.apache.org/tomahawk" prefix="t" %>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<% if (request.getAttribute("org.portletapi.contextid")==null)
	{%>
<html>
<head>
<META NAME="AUTHOR" CONTENT="Lars Hagrot, Signifikant AB">
<META HTTP-EQUIV="CACHE-CONTROL" CONTENT="NO-CACHE">
<META HTTP-EQUIV="EXPIRES" CONTENT="0">
<META NAME="KEYWORDS" CONTENT="Sundbybergs IK Forum">
<%} %>
<style>

.label
{
	font-weight:bold;
	font-family:Tahoma,sans-serif;
	font-size:9pt;


}
.messageBody {
	font-family:Verdana,sans-serif;
	font-size:9pt;
	font-color:#000000;
}

.messageTable {
	font-family:Verdana,sans-serif;
	font-size:9pt;
	font-color:#000000;
}

.messageRubrik {
	font-family:Verdana,sans-serif;
	font-size:9pt;
	font-color:#008000;
	font-weight:bold;
}


.top{
vertical-align:top;
}

.left {
	text-align:left;
}

.p50p {
	width:50%;
}

.Scroll {
	overflow-x:auto;

	overflow-y:scroll;
	height:300px;	
	width:100%;
}


.right {
	text-align:right;
}
.topleft {
	text-align:left;
	vertical-align:top;
}
.topright {
	text-align:right;
	vertical-align:top;
}

.header {
	background-color:#00FF44;
	color:#FFFFFF;
	font-weight:bold;
	font-family:Tahoma,sans-serif;
	font-size:14pt;
}
.evenRow {
	background:#FFFFFF;
	
}
.oddRow {
	background:#DDDDDD;
	
}
.blackHeader {
	background:#eeeeee;
	color:#111111;
	font-family:Tahoma,sans-serif;
	font-size:10pt;
	
}

</style>
<script>
function validate()
{
	var error = '';
	if (document.getElementById('message').value.length<5)
	{
		alert('Tomma meddelanden kan inte skickas! Minst fem tecken!');
		return false;
	}
	if (document.getElementById('message').value.length>2000)
	{
		alert('Meddelanden får vara högst 2000 tecken detta är på ' + document.getElementById('message').value.length);
		return false;
	}
	if (document.getElementById('userName').value.length<2)
	{
		alert('Namn måste anges! Minst två tecken!');
		return false;
	}
	return true;
}
</script> 	
<% if (request.getAttribute("org.portletapi.contextid")==null)
	{%>

</head>
<body style="{background-color:#EFEFEF;padding:0 0 0 0;}">
<%} %>
<div >
<!-- 
<%

%>
 -->
<f:view>

<h:form>
	<t:panelGrid columns="1" width="230px">
		<t:dataTable
			
			var="message" 
						value="#{forumJSFBean.latestForumMessageHeaders}" 
						rowClasses="evenRow,oddRow"
						width="220px"
			>
			<t:column>
				<t:panelGrid columns="1" width="100%" >
				<t:outputText styleClass="messageBody"  value="#{message.rubrikShort}"/>
				<t:outputText styleClass="messageBody"  value="#{message.userName} #{message.datumAsString}"/>
				<t:outputText styleClass="messageBody"  value="Antal svar:#{message.antalSvar} Läst:#{message.readCount}"/>
				</t:panelGrid> 
			</t:column>			
		</t:dataTable>
	</t:panelGrid>
</h:form>




</f:view>
</div>
<% if (request.getAttribute("org.portletapi.contextid")==null)
	{%>

</body>
<%}%>