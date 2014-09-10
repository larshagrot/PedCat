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
	return true;
}
</script> 	
</head>
<body style="{background-color:#EFEFEF;padding:0 0 0 0;}">
<div >
<!-- 
<%

/*java.util.Enumeration e = request.getHeaderNames();
while (e.hasMoreElements())
{
	String key =  (String)e.nextElement();
	out.println(key +":" +request.getHeader(key));
}*/
%>
 -->
<f:view>

<h:form>
	<t:panelGrid columns="1" width="560px"    headerClass="header">
		
	<t:panelGrid border="2" columns="1" rendered="#{medlemJSFBean.hasMedlem}" width="100%">¨
		<t:panelGrid columns="2" columnClasses="left" width="100%">
		<t:panelGrid columns="1" columnClasses="left">
			
			<t:outputText styleClass="label" value="Medlemsnummer"></t:outputText>
			<t:inputText displayValueOnly="#{!medlemJSFBean.admin}" forceId="true" id="medlemsnummer" size="14" value="#{medlemJSFBean.currentMedlem.medlemsnummer}"/>
			<t:outputText styleClass="label" value="Förnamn"></t:outputText>
			<t:inputText forceId="true" id="fornamn" size="20" value="#{medlemJSFBean.currentMedlem.fornamn}" />
			<t:outputText styleClass="label" value="Efternamn"></t:outputText>
			<t:inputText forceId="true" id="efternamn" size="20" value="#{medlemJSFBean.currentMedlem.efternamn}"/>
		
			
			<t:outputText styleClass="label" value="Personnummer"></t:outputText>
			<t:inputText forceId="true" id="personnummer" size="14" value="#{medlemJSFBean.currentMedlem.personnummer}"/>
			<t:outputText styleClass="label" value="Telefonnummer"></t:outputText>
			<t:inputText forceId="true" id="telefonnummer" size="20" value="#{medlemJSFBean.currentMedlem.telefonnummer}"/>
			<t:outputText styleClass="label" value="Mobilnummer"></t:outputText>
			<t:inputText forceId="true" id="mobilnummer" size="20" value="#{medlemJSFBean.currentMedlem.mobilnummer}"/>
		
			<t:outputText styleClass="label" value="Adress"></t:outputText>
			<t:inputText forceId="true" id="adress" size="40" value="#{medlemJSFBean.currentMedlem.adress}">
				
			</t:inputText>
			<t:outputText styleClass="label" value="Postnummer"></t:outputText>
			<t:inputText forceId="true" id="postnummer" size="7" value="#{medlemJSFBean.currentMedlem.postnummer}"/>
		
			<t:outputText styleClass="label" value="Ort"></t:outputText>
			<t:inputText forceId="true" id="ort" size="20" value="#{medlemJSFBean.currentMedlem.ort}"/>
			
			
			<t:outputText styleClass="label" value="Epost"></t:outputText>
			<t:panelGroup>
				<t:inputText  size="40" forceId="true" 
								id="epost" 
								 value="#{medlemJSFBean.currentMedlem.epost}">
					<t:validateEmail  message="Ange korrekt epostadress!" />
				</t:inputText>
				
				<f:verbatim><br/></f:verbatim>
				<t:message style="color:red;" for="epost"></t:message>
			</t:panelGroup>
			<t:panelGroup>
				<t:commandButton tabindex="99" value="Spara" onclick="javascript: if (!validate()) return false" action="#{medlemJSFBean.save}"  ></t:commandButton>
				<t:outputText value=" " ></t:outputText>
				<t:commandButton tabindex="100" value="Avbryt" action="#{medlemJSFBean.cancel}"  ></t:commandButton>
			</t:panelGroup>
			
		</t:panelGrid>
		<t:panelGrid columns="1" border="2" rendered="#{!medlemJSFBean.currentMedlem.new}">
			<t:panelGrid border="2" columns="1"></t:panelGrid>
				<t:outputText styleClass="label" value="Arrangemang"></t:outputText>
				<t:selectOneMenu value="#{medlemJSFBean.choosenArrangemangId}">
					<f:selectItems value="#{medlemJSFBean.arrangemangsLista}"/>
				</t:selectOneMenu>
				<t:outputText styleClass="label" value="Funktion"></t:outputText>
				<t:inputText forceId="true" id="funktion" size="20" value="#{medlemJSFBean.funktion}"/>
				<t:commandButton action="#{medlemJSFBean.addFunktionar}" value="Anmäl funktionär"/>
			</t:panelGrid>
			<t:panelGrid>
				<t:dataTable 	var="funktionar" 
					value="#{medlemJSFBean.funktionarerLista}" 
					width="100%" 
					border="1"
					columnClasses="medlemTable"
					>
				<t:column>
					<f:facet name="header">
						<t:outputText value="Arrangemang"></t:outputText>
					</f:facet>
					<t:outputText value="#{funktionar.arrangemang.datumAsString} #{funktionar.arrangemang.namn}"/>
				</t:column>
				<t:column>
					<f:facet name="header">
						<t:outputText value="Funktion"></t:outputText>
					</f:facet>
					<t:outputText value="#{funktionar.funktion} #{funktionar.arrangemang.namn}"/>
				</t:column>
				<t:column>
				<t:commandLink rendered="#{medlemJSFBean.loggedIn}" immediate="true" action="#{medlemJSFBean.updateFunktionar}"> 
					 		<t:outputText value=" Ändra "/>
					 		<t:updateActionListener property="#{medlemJSFBean.currentFunktionarId}" value="#{funktionar.objectId}" />
				</t:commandLink>
				<t:commandLink rendered="#{medlemJSFBean.loggedIn}" immediate="true" action="#{medlemJSFBean.deleteFunktionar}">
				 		<t:outputText value=" Ta bort "/>
				 		<t:updateActionListener property="#{medlemJSFBean.currentFunktionarId}" value="#{funktionar.objectId}" />
				</t:commandLink>
			</t:column>
				
				
				</t:dataTable>				
			</t:panelGrid>
		</t:panelGrid>	
	</t:panelGrid>
	<t:panelGrid columns="1" rendered="#{!medlemJSFBean.hasMedlem}">
		<t:commandButton value="Skapa ny medlem!" action="#{medlemJSFBean.nytt}"  ></t:commandButton>
	</t:panelGrid>
	
	<t:dataTable 	var="medlem" 
					value="#{medlemJSFBean.medlemmar}" 
					width="100%" 
					border="1"
					columnClasses="medlemTable"
					>
		<t:column>
			<f:facet name="header">
				<t:outputText value="Namn"></t:outputText>
			</f:facet>
			<t:outputText value="#{medlem.fornamn} #{medlem.efternamn}" />
		</t:column>
		<t:column>
			<f:facet name="header">
				<t:outputText value="Epost"></t:outputText>
			</f:facet>
			<t:outputText value="#{medlem.epost}" />
		</t:column>
		
		<t:column>
			<f:facet name="header">
				<t:outputText value="Medlemsnummer"></t:outputText>
			</f:facet>
			<t:outputText value="#{medlem.medlemsnummer}" />
		</t:column>
		<t:column>
			<t:commandLink rendered="#{medlemJSFBean.loggedIn}" immediate="true" action="#{medlemJSFBean.update}">
					 		<t:outputText value=" Ändra "/>
					 		<t:updateActionListener property="#{medlemJSFBean.currentMedlemId}" value="#{medlem.objectId}" />
					 	</t:commandLink>
					 	<t:commandLink rendered="#{medlemJSFBean.loggedIn}" immediate="true" action="#{medlemJSFBean.delete}">
					 		<t:outputText value=" Ta bort "/>
					 		<t:updateActionListener property="#{medlemJSFBean.currentMedlemId}" value="#{medlem.objectId}" />
					 	</t:commandLink>
		</t:column>

	</t:dataTable>
	</t:panelGrid>
</h:form>




</f:view>
</div>
</body>