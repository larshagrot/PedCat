<%@ taglib uri="http://myfaces.apache.org/tomahawk" prefix="t" %>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>

<html>
<head>
<META NAME="AUTHOR" CONTENT="Lars Hagrot, Signifikant AB">
<META HTTP-EQUIV="CACHE-CONTROL" CONTENT="NO-CACHE">
<META HTTP-EQUIV="EXPIRES" CONTENT="0">
<META NAME="KEYWORDS" CONTENT="Sundbybergs IK Forum">
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
	font-weight:bold;
	background-color:#EEEEEE;
}

.messageTable {
	font-family:Verdana,sans-serif;
	font-size:9pt;
	font-color:#000000;
	font-weight:bold;
	background-color:#EEEEEE;
}

.top{
vertical-align:top;
}

.left {
	text-align:left;
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
</head>
<body style="{background-color:#EFEFEF;padding:0 0 0 0;}">
<div >
<!-- 
<%

java.util.Enumeration e = request.getHeaderNames();
while (e.hasMoreElements())
{
	String key =  (String)e.nextElement();
	out.println(key +":" +request.getHeader(key));
}
%>
 -->
<f:view>

<h:form>
	<t:panelGrid columns="1" width="560px"    headerClass="header">
		
	<t:panelGrid border="2" columns="1" rendered="#{forumJSFBean.hasMessage}" width="100%">
		<t:panelGrid columns="1" columnClasses="left">
			<t:outputText styleClass="label" value="Namn"></t:outputText>
			<t:inputText forceId="true" id="userName" size="40" value="#{forumJSFBean.currentForumMessage.userName}">
				
			</t:inputText>
			
			<t:outputText styleClass="label" value="Epost"></t:outputText>
			<t:panelGroup>
				<t:inputText  size="40" forceId="true" 
								id="epost" 
								 value="#{forumJSFBean.currentForumMessage.epost}">
					<t:validateEmail  message="Ange korrekt epostadress!" />
				</t:inputText>
				
				<f:verbatim><br/></f:verbatim>
				<t:message style="color:red;" for="epost"></t:message>
			</t:panelGroup>
			<t:outputText styleClass="label" value="Rubrik"></t:outputText>
			<t:inputText forceId="true" size="40" id="rubrik" value="#{forumJSFBean.currentForumMessage.rubrik}" />
			
			<t:outputText styleClass="label" value="Text (max 2000 tecken)"></t:outputText>
			<t:inputTextarea forceId="true" id="message" value="#{forumJSFBean.currentForumMessage.message}" cols="63" rows="20">
			</t:inputTextarea>
			<t:panelGroup>
				<t:commandButton tabindex="99" value="Skicka" onclick="javascript: if (!validate()) return false" action="#{forumJSFBean.save}"  ></t:commandButton>
				<t:outputText value=" " ></t:outputText>
				<t:commandButton tabindex="100" value="Avbryt" action="#{forumJSFBean.cancel}"  ></t:commandButton>
			</t:panelGroup>
			
		</t:panelGrid>
		
	</t:panelGrid>
	<t:panelGrid columns="1" rendered="#{!forumJSFBean.hasMessage}">
		<t:commandButton value="Skriv ett nytt inlägg!" action="#{forumJSFBean.nytt}"  ></t:commandButton>
	</t:panelGrid>
	
	<t:dataTable 	var="message" 
					value="#{forumJSFBean.forumMessages}" 
					width="100%" 
					border="1"
					columnClasses="messageTable"
					>
					
		<t:column>
			<t:panelGrid columns="1" width="100%" >
				<t:panelGrid columns="3" style="font-size:9px;background:#BBBBBB" columnClasses="left,right" width="100%">
					<t:outputText value="#{message.userName}" />
					<t:panelGroup>
						<t:outputText style="{color:red}"  rendered="#{message.registered}" value="Inlägget är anmält " />
					 	<t:outputText title="IP #{message.ip}" value="#{message.datumAsString}" />
					 	<t:commandLink  action="#{forumJSFBean.kommentera}">
					 		<t:outputText value=" Kommentera "/>
					 		<t:updateActionListener property="#{forumJSFBean.currentMessageId}" value="#{message.objectId}" />
					 	</t:commandLink>
					 	<t:commandLink rendered="#{forumJSFBean.ip == message.ip || forumJSFBean.loggedIn}" action="#{forumJSFBean.update}">
					 		<t:outputText value=" Ändra "/>
					 		<t:updateActionListener property="#{forumJSFBean.currentMessageId}" value="#{message.objectId}" />
					 	</t:commandLink>
					 	<t:commandLink rendered="#{forumJSFBean.ip == message.ip || forumJSFBean.loggedIn}" immediate="true" action="#{forumJSFBean.delete}">
					 		<t:outputText value=" Ta bort "/>
					 		<t:updateActionListener property="#{forumJSFBean.currentMessageId}" value="#{message.objectId}" />
					 	</t:commandLink>
					 	<t:commandLink  action="#{forumJSFBean.register}">
					 		<t:outputText value=" Anmäl"/>
					 		<t:updateActionListener property="#{forumJSFBean.currentMessageId}" value="#{message.objectId}" />
					 	</t:commandLink>
					</t:panelGroup>  
				</t:panelGrid>
				<t:outputText styleClass="messageBody"  value="#{message.rubrik}"/>
				<t:inputTextarea rendered="#{!message.html}"  styleClass="messageBody" displayValueOnly="true" value="#{message.message}" cols="65" rows="20">
				</t:inputTextarea>
				<t:outputText rendered="#{message.html}" escape="#{message.html}" styleClass="messageBody"  value="#{message.message}"/>
				
			</t:panelGrid>
		</t:column>
	</t:dataTable>
	</t:panelGrid>
</h:form>




</f:view>
</div>
</body>