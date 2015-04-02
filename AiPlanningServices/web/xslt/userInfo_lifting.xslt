<xsl:transform xmlns:xsl="http://www.w3.org/1999/XSL/Transform" 
               xmlns:n1="http://localhost:8080/PaaSOntologyv3.owl#" 
               xmlns:rdf="http://www.w3.org/1999/02/22-rdf-syntax-ns#" 
               xmlns:service="http://localhost:8080/SalesForceServices.sawsdl" version="1.1">
    <xsl:output encoding="iso-8859-1" indent="yes" method="xml" version="1.0"/>
    
    <xsl:template match="/">
        <rdf:RDF>
            <n1:UserInfoDataType>
                <n1:userInfoName>
                    <xsl:value-of select="/return/userFullName"/>
                </n1:userInfoName>
                
                <n1:userInfoEmailAddress>
                    <xsl:value-of select="/return/userEmail"/>
                </n1:userInfoEmailAddress>
                
                 <n1:userInfoUserName>
                    <xsl:value-of select="/return/userName"/>
                </n1:userInfoUserName>
               
            </n1:UserInfoDataType>
        </rdf:RDF>
    </xsl:template>
</xsl:transform>