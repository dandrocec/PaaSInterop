<xsl:transform xmlns:xsl="http://www.w3.org/1999/XSL/Transform" 
               xmlns:n1="http://localhost:8080/PaaSOntologyv3.owl#" 
               xmlns:rdf="http://www.w3.org/1999/02/22-rdf-syntax-ns#" 
               xmlns:service="http://localhost:8080/SalesForceServices.sawsdl" version="1.1">
    <xsl:output encoding="iso-8859-1" indent="yes" method="xml" version="1.0"/>
    
    <xsl:template match="/">
       
            <UserEntity>
                <name>
                    <xsl:value-of select="rdf:RDF/n1:UserInfoDataType/n1:userInfoName"/>
                </name>
                
                <email>
                    <xsl:value-of select="rdf:RDF/n1:UserInfoDataType/n1:userInfoEmailAddress"/>
                </email>     
               
            </UserEntity>
       
    </xsl:template>
</xsl:transform>