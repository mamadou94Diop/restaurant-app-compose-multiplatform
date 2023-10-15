import config.API_HOST
import config.API_KEY
import io.ktor.client.HttpClient
import io.ktor.client.engine.darwin.Darwin
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.DEFAULT
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.request.header
import io.ktor.http.ContentType
import io.ktor.http.URLProtocol
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import moe.tlaster.precompose.PreComposeApplication

fun MainViewController() = PreComposeApplication { App() }

actual fun getHttpClient() : HttpClient =  HttpClient(Darwin) {
    install(ContentNegotiation) {
        json(contentType = ContentType.Application.Json, json = Json {
            prettyPrint = true
            explicitNulls = false
            ignoreUnknownKeys = true
        })
    }
    install(Logging) {
        logger = Logger.DEFAULT
        level = LogLevel.ALL
    }

    defaultRequest {
        url {
            protocol = URLProtocol.HTTPS
        }
        header("X-RapidAPI-Key", API_KEY)
        header("X-RapidAPI-Host", API_HOST)
    }
}