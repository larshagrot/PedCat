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

.arrangemangBody {
	font-family:Verdana,sans-serif;
	font-size:9pt;
	font-color:#000000;
	font-weight:bold;
	background-color:#EEEEEE;
}

.arrangemangTable {
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
	if (document.getElementById('arrangemang').value.length<5)
	{
		alert('Tomma meddelanden kan inte skickas! Minst fem tecken!');
		return false;
	}
	if (document.getElementById('arrangemang').value.length>2000)
	{
		alert('Meddelanden får vara högst 2000 tecken detta är på ' + document.getElementById('arrangemang').value.length);
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
	<!--  private String namn;
	private String beskrivning;
	private String kontaktperson;
	private String telefon;
	private String epost;
	private String anmalan;
	private String plats;
	private Date start;
	private Date datum;
	private Date senast; -->
		
	<t:panelGrid border="2" columns="1" rendered="#{arrangemangJSFBean.hasArrangemang}" width="100%">
		<t:panelGrid columns="1" columnClasses="left">
			<t:outputText styleClass="label" value="Namn"></t:outputText>
			<t:inputText forceId="true" id="namn" size="100" value="#{arrangemangJSFBean.currentArrangemang.namn}" />
			<t:outputText styleClass="label" value="Beskrivning (max 1000 tecken)"></t:outputText>
			<t:inputTextarea forceId="true" id="beskrivning" value="#{arrangemangJSFBean.currentArrangemang.beskrivning}" cols="63" rows="20">
			</t:inputTextarea>
			<t:outputText styleClass="label" value="Plats"></t:outputText>
			<t:inputText forceId="true" id="plats" size="20" value="#{arrangemangJSFBean.currentArrangemang.plats}"/>
			
			<t:outputText styleClass="label" value="Datum (yyyy-MM-dd)"></t:outputText>
			<t:inputText forceId="true" id="datum" size="20" value="#{arrangemangJSFBean.currentArrangemang.datumAsString}"/>
			
			<t:outputText styleClass="label" value="Start (yyyy-MM-dd HH:mm:ss)"></t:outputText>
			<t:inputText forceId="true" id="start" size="20" value="#{arrangemangJSFBean.currentArrangemang.startAsString}"/>
			
			<t:outputText styleClass="label" value="Anmälan senast (yyyy-MM-dd HH:mm:ss)"></t:outputText>
			<t:inputText forceId="true" id="senast" size="20" value="#{arrangemangJSFBean.currentArrangemang.senastAsString}"/>
			
			
			<t:outputText styleClass="label" value="Kontaktperson"></t:outputText>
			<t:inputText forceId="true" id="Kontaktperson" size="20" value="#{arrangemangJSFBean.currentArrangemang.kontaktperson}"/>
			<t:outputText styleClass="label" value="Telefonnummer"></t:outputText>
			<t:inputText forceId="true" id="telefonnummer" size="20" value="#{arrangemangJSFBean.currentArrangemang.telefon}"/>
			<t:outputText styleClass="label" value="Webbsida"></t:outputText>
			<t:inputText forceId="true" id="url" size="20" value="#{arrangemangJSFBean.currentArrangemang.url}"/>
		
			<t:outputText styleClass="label" value="Anmalan (max 1000 tecken)"></t:outputText>
			<t:inputTextarea forceId="true" id="anmalan" value="#{arrangemangJSFBean.currentArrangemang.anmalan}" cols="63" rows="20">
			</t:inputTextarea>
			
			<t:outputText styleClass="label" value="Epost"></t:outputText>
			<t:panelGroup>
				<t:inputText  size="40" forceId="true" 
								id="epost" 
								 value="#{arrangemangJSFBean.currentArrangemang.epost}">
					<t:validateEmail  message="Ange korrekt epostadress!" />
				</t:inputText>
				
				<f:verbatim><br/></f:verbatim>
				<t:message style="color:red;" for="epost"></t:message>
			</t:panelGroup>
			<t:panelGroup>
				<t:commandButton tabindex="99" value="Spara" onclick="javascript: if (!validate()) return false" action="#{arrangemangJSFBean.save}"  ></t:commandButton>
				<t:outputText value=" " ></t:outputText>
				<t:commandButton tabindex="100" value="Avbryt" action="#{arrangemangJSFBean.cancel}"  ></t:commandButton>
			</t:panelGroup>
			
		</t:panelGrid>
		
	</t:panelGrid>
	<t:panelGrid columns="1" rendered="#{!arrangemangJSFBean.hasArrangemang}">
		<t:commandButton value="Skapa ny arrangemang!" action="#{arrangemangJSFBean.nytt}"  ></t:commandButton>
	</t:panelGrid>
	
	<t:dataTable 	var="arrangemang" 
					value="#{arrangemangJSFBean.arrangemang}" 
					width="100%" 
					border="1"
					columnClasses="arrangemangTable"
					>
		<t:column>
			<f:facet name="header">
				<t:outputText value="Namn"></t:outputText>
			</f:facet>
			<t:outputText value="#{arrangemang.namn}" />
		</t:column>
		<t:column>
			<f:facet name="header">
				<t:outputText value="Epost"></t:outputText>
			</f:facet>
			<t:outputText value="#{arrangemang.datum}" />
		</t:column>
		
		<t:column>
			<t:commandLink rendered="#{arrangemangJSFBean.loggedIn}" immediate="true" action="#{arrangemangJSFBean.update}">
					 		<t:outputText value=" Ändra "/>
					 		<t:updateActionListener property="#{arrangemangJSFBean.currentArrangemangId}" value="#{arrangemang.objectId}" />
					 	</t:commandLink>
					 	<t:commandLink rendered="#{arrangemangJSFBean.loggedIn}" immediate="true" action="#{arrangemangJSFBean.delete}">
					 		<t:outputText value=" Ta bort "/>
					 		<t:updateActionListener property="#{arrangemangJSFBean.currentArrangemangId}" value="#{arrangemang.objectId}" />
					 	</t:commandLink>
		</t:column>

	</t:dataTable>
	</t:panelGrid>
</h:form>




</f:view>
</div>
</body>