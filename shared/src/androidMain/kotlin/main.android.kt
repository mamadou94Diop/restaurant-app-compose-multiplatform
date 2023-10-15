import androidx.compose.runtime.Composable
import config.API_HOST
import config.API_KEY
import io.ktor.client.HttpClient
import io.ktor.client.engine.android.Android
import io.ktor.client.engine.cio.CIO
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

@Composable fun MainView() = App()

actual fun getHttpClient() : HttpClient = HttpClient(Android) {
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