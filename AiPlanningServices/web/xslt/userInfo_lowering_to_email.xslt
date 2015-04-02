<xsl:transform xmlns:xsl="http://www.w3.org/1999/XSL/Transform" 
               xmlns:n1="http://localhost:8080/PaaSOntologyv3.owl#" 
               xmlns:rdf="http://www.w3.org/1999/02/22-rdf-syntax-ns#" 
               xmlns:service="http://localhost:8080/SalesForceServices.sawsdl" version="1.1">
    <xsl:output encoding="iso-8859-1" indent="yes" method="xml" version="1.0"/>
    
    <xsl:template match="/">

	<SingleEmailMessage>

	
		<toAddresses>
			dandrocec@foi.hr
		</toAddresses>

		<subject>
			New user is automatically added to Vosao
		</subject>

		<plainTextBody>
			New user is automatically added to Vosao. Password needs to be generated.

User name: <xsl:value-of select="rdf:RDF/n1:UserInfoDataType/n1:userInfoUserName"/>

Full name: <xsl:value-of select="rdf:RDF/n1:UserInfoDataType/n1:userInfoName"/>
		</plainTextBody> 

	</SingleEmailMessage>    
    </xsl:template>
</xsl:transform>