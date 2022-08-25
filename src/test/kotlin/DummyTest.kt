import com.github.tomakehurst.wiremock.client.WireMock.*
import com.github.tomakehurst.wiremock.junit5.WireMockRuntimeInfo
import com.github.tomakehurst.wiremock.junit5.WireMockTest
import okhttp3.OkHttpClient
import okhttp3.Request
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals


@WireMockTest
class DummyTest {
    @Test
    fun `Some other test`(wireMockRuntimeInfo: WireMockRuntimeInfo) {
        configureFor("localhost", 8080)
        stubFor(get(urlEqualTo("/dummy/path"))
            .willReturn(ok("test example")))

        val client = OkHttpClient()

        val request = Request.Builder()
            .url("http://localhost:8080/dummy/path")
            .build()

        client.newCall(request).execute().let {
            val res = it.body()!!.string()

            assertEquals("test example", res)
        }
    }
}