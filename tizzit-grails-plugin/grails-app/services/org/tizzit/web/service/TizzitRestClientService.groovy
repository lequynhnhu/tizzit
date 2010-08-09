package org.tizzit.web.service

import groovyx.net.http.HTTPBuilder
import groovy.xml.XmlUtil

class TizzitRestClientService {
	def grailsApplication
	static transactional = false

	def navigationXml(depth, since, viewComponentId) {
		def xml

		withHttp(uri: grailsApplication.config.tizzit.restServer) {
			def resp = get(path: "/$grailsApplication.config.tizzit.restServerContextPath/navigationxml/$viewComponentId/$since/$depth/false")
			resp = "<root>$resp</root>"
			xml = new XmlParser().parseText(resp)
		}
		return xml
	}

	def actionData(host, requestPath, safeguardUsername, safeguardPassword) {
		//http://localhost:8080/remote/action?host=www.hsg-wennigsen-gehrden.de&requestPath=de/UnsereMannschaften&safeguardUsername=null&safeguardPassword=null
		def xml
		withHttp(uri: grailsApplication.config.tizzit.restServer) {
			def psafeguardUsername = (safeguardUsername) ?: "null"
			def psafeguardPassword = (safeguardPassword) ?: "null"

			xml = get(path: "/$grailsApplication.config.tizzit.restServerContextPath/action", query: [host: host, requestPath: requestPath, safeguardUsername: psafeguardUsername, safeguardPassword: psafeguardPassword])
		}

		xml = xml.getText()
		//log.info xml
		return xml
	}
}