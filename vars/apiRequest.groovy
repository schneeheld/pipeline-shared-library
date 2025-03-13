// vars/apiRequest.groovy
import groovy.json.JsonSlurper
import hudson.AbortException

def call(String url, String method = 'GET', Map headers = [:], String body = '') {
    try {
        def connection = new URL(url).openConnection()
        connection.setRequestMethod(method)
        
        // Set headers
        headers.each { key, value ->
            connection.setRequestProperty(key, value)
        }
        
        // Send request body for POST/PUT methods
        if (['POST', 'PUT'].contains(method) && body) {
            connection.doOutput = true
            connection.outputStream.withWriter("UTF-8") { writer -> writer << body }
        }
        
        // Get response
        def responseCode = connection.responseCode
        def responseStream = (responseCode >= 200 && responseCode < 300) ? connection.inputStream : connection.errorStream
        def responseText = responseStream?.text
        
        // Parse JSON response if applicable
        def jsonResponse = responseText ? new JsonSlurper().parseText(responseText) : null
        
        println "Response Code: ${responseCode}"
        println "Response Body: ${jsonResponse}"
        
        return jsonResponse
    } catch (Exception e) {
        throw new AbortException("API request failed: ${e.message}")
    }
}
