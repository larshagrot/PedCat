<%@ taglib uri="http://myfaces.apache.org/tomahawk" prefix="t" %>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<f:view>
<html>
<body>
<h:form>
<t:panelGrid columns="2">
<t:outputText value="Namn"></t:outputText>
<t:inputText forceId="true" id="x" value="#{forumJSFBean.userName}"></t:inputText>
<t:inputSecret forceId="true" id="y" value="#{forumJSFBean.pwd}" />
<t:commandButton action="#{forumJSFBean.xxxxx}"></t:commandButton>
</t:panelGrid>
</h:form>
</body>


</html>
</f:view>