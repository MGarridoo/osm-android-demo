package com.example.osmdemo.core.backend.logging

import okhttp3.Interceptor
import okhttp3.Response
import okio.Buffer
import java.nio.charset.Charset
import java.util.logging.Level
import java.util.logging.Logger

class CustomLoggingInterceptor : Interceptor {

    private val logger = Logger.getLogger(CustomLoggingInterceptor::class.java.name)

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()

        val requestLog = StringBuilder()
        requestLog.append("---- Request ----\n")
        requestLog.append("URL: ${request.url}\n")
        requestLog.append("Method: ${request.method}\n")
        requestLog.append("Headers:\n")
        request.headers.forEach {
            requestLog.append("\t${it.first}: ${it.second}\n")
        }

        // Para obtener el body de la request de manera segura
        val requestBody = request.body?.let { body ->
            val buffer = Buffer()
            body.writeTo(buffer)
            buffer.readString(Charset.forName("UTF-8"))
        } ?: "No body"
        requestLog.append("Body: \n$requestBody\n")

        logger.log(Level.INFO, requestLog.toString())

        val response = chain.proceed(request)
        val responseLog = StringBuilder()
        responseLog.append("---- Response ----\n")
        responseLog.append("Code: ${response.code}\n")
        responseLog.append("Message: ${response.message}\n")
        responseLog.append("Headers:\n")
        response.headers.forEach {
            responseLog.append("\t${it.first}: ${it.second}\n")
        }

        // Lee el cuerpo de la respuesta y permite saltos de línea
        val responseBody = response.body?.let { body ->
            val source = body.source()
            source.request(Long.MAX_VALUE) // Lee todo el contenido
            val buffer = source.buffer
            buffer.clone().readString(Charset.forName("UTF-8"))
        } ?: "No body"
        responseLog.append("Body: \n$responseBody\n")

        logger.log(Level.INFO, responseLog.toString())

        return response
    }
}
