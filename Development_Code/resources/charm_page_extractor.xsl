<?xml version="1.0" encoding="utf-8"?>
<!--  Description:  When combined with an appropriate commands, this file can extract the charm names from a given set of XML files.
      Software Requirements:  find, xsltproc, grep, tr, & mktemp.
      Usage:

charmNames=$(mktemp /tmp/charmNames.XXXXXX;);
find [PATH TO data DIRECTORY] -iname "*.xml" -exec xsltproc [PATH TO charm_page_extractor.xsl] {} \; |\
  grep -Ev '<[^>]*>' |\
  tr " " "\n" > $charmNames;
      
      As should be obvious, replace [PATH TO data Directory] and [PATH TO charm_page_extractor.xsl] with values the correspond
        to the repository setup on your machine.  The resulting values will be written to the /tmp directory in a relatively
        unique file and should be deleted when the data is extracted.

      For those using TextMate, you can grab everything from "find" to the just before the last ">" symbol, modify as appropriate,
        paste back into a blank language file, "CMD+A", and "ctrl+R" to fill the page.  (Be sure to delete the scripting prior to distribution.)
-->
<xsl:stylesheet xmlns:chrm="http://anathema.sourceforge.net/charms" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0">
	<xsl:template match="chrm:charmlist">
		<!--xsl:text> ##Charms##  </xsl:text>
		<xsl:for-each select="chrm:genericCharm">
			<xsl:value-of select="@id" /><xsl:text>= </xsl:text>
		</xsl:for-each>
		<xsl:for-each select="chrm:charm">
			<xsl:value-of select="@id" /><xsl:text>= </xsl:text>
		</xsl:for-each-->
		<xsl:text> ##Pages##  </xsl:text>
		<xsl:for-each select="chrm:genericCharm">
			<xsl:value-of select="chrm:source/@source" /><xsl:text>.</xsl:text>
			<xsl:value-of select="@id" /><xsl:text> </xsl:text>
		</xsl:for-each>
		<xsl:for-each select="chrm:charm">
			<xsl:value-of select="chrm:source/@source" /><xsl:text>.</xsl:text>
			<xsl:value-of select="@id" /><xsl:text>.Page= </xsl:text>
		</xsl:for-each>
		<!--xsl:text> ##Other##  </xsl:text>
		<xsl:for-each select="chrm:charm">
			<xsl:for-each select="chrm:charmAttribute[@visualize='true']">
				<xsl:text>Keyword.</xsl:text><xsl:value-of select="@attribute" /><xsl:text> </xsl:text>
			</xsl:for-each>
		</xsl:for-each-->
	</xsl:template>
</xsl:stylesheet>