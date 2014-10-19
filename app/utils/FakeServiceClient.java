package utils;

import play.libs.F;
import play.libs.ws.WS;

public class FakeServiceClient {

    public static F.Promise<String> call(final String serviceName) {
        return WS.url("http://localhost:9000/mock/" + serviceName).get().map(response -> response.getBody());
    }

}
