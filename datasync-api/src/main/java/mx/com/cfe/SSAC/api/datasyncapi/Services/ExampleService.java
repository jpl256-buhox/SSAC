package mx.com.cfe.SSAC.api.datasyncapi.Services;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Service
public class ExampleService {

    public String URLSICOSS = "http://10.4.18.1/sicossweb/";
    public String URLPETICION = "http://10.4.18.1/cgi-bin/sicossweb/solicitudes/solsfra.cgi";
    public String URLNUMBERSICOSS = "http://10.4.18.1/cgi-bin/sicossweb/consulta/consulta.cgi?K1804107822";

    public void extraerInformacion() {
        try {
            Connection.Response response = Jsoup
                    .connect("http://10.4.18.1/sicossweb/")
                    .method(Connection.Method.GET)
                    .execute();

            Document serviceResponse = Jsoup
                    .connect("http://10.4.18.1/cgi-bin/sicossweb/solicitudes/solsfra.cgi")
                    .userAgent("Mozilla/5.0")
                    .timeout(10 * 1000)
                    .cookies(response.cookies())
                    .data("fd", "20210324")
                    .data("fh", "20210324")
                    .data("zona", "K18")
                    .data("cen", "OC")
                    .data("sol", "E")
                    .data("zn", "K18")
                    .data("centros", "OC")
                    .get();
            System.out.println(serviceResponse.outerHtml());
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
    }
}
