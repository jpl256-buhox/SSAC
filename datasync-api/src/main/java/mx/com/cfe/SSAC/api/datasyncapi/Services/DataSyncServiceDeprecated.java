package mx.com.cfe.SSAC.api.datasyncapi.Services;

import mx.com.cfe.SSAC.api.datasyncapi.Models.Requests.SICOSSByRegistrationModel;
import mx.com.cfe.SSAC.api.datasyncapi.Responses.SICOSSByRegistration;
import org.apache.commons.collections4.MultiMap;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;

@Service
public class DataSyncServiceDeprecated {
    public String URLSICOSS = "http://10.4.18.1/sicossweb/";
    public String URLPETICION = "http://10.4.18.1/cgi-bin/sicossweb/solicitudes/solsfra.cgi";
    public String URLNUMBERSICOSS = "http://10.4.18.1/cgi-bin/sicossweb/consulta/consulta.cgi?K1804107822";


    public void extraerInformacion() {
        SICOSSByRegistrationModel sicossByRegistrationModel = new SICOSSByRegistrationModel();
        sicossByRegistrationModel.setBeginDate("20210322");
        sicossByRegistrationModel.setEndDate("20210323");
        sicossByRegistrationModel.setCenter("K18");
        sicossByRegistrationModel.setTypeOrder("E");
        sicossByRegistrationModel.setZone("K18");


        try{
        System.out.println("hola");
        //RequestSICOSSByRegistrationDate(sicossByRegistrationModel,URLSICOSS,URLPETICION);
            System.out.println("entro");
        } catch (Exception   e) {
            e.printStackTrace();
        }


    }

    private void RequestSICOSSByRegistrationDate(SICOSSByRegistrationModel params, String URLConnection, String URLResponse) throws IOException {
        String centers = "OC";
        String graf = "Generar";

        String userAgent = "Mozilla/5.0 (Windows NT 10.0; WOW64; rv:65.0) Gecko/20100101 Firefox/65.0";


        Connection.Response response = Jsoup
                                        .connect(URLConnection)
                                        .method(Connection.Method.GET)
                                        .execute();

        Document serviceResponse = Jsoup
                .connect(URLResponse)
                .userAgent(userAgent)
                .ignoreContentType(true)
                .timeout(100 * 1000)
                .cookies(response.cookies())
              //  .data(params)
                .data("cen", centers)
                .data("graf", graf)
                .data("centros", centers)

            //    .data("fd", .P)
                .data("fh", "20210323")
                .data("zona", "K18")
                .data("sol", "E")
                .data("zn", "K18")
                .get();

        Elements tables = serviceResponse.select("table");
        if (tables.size() > 1) {
            Elements rows = tables.get(3).select("tr");

             rows.stream().forEach(p->{
                Elements value = p.select("td");
                if(value.size()>0) {
                    if (isNumeric(value.get(0).text())) {
                        SICOSSByRegistration sicossByRegistration = new SICOSSByRegistration();
                        sicossByRegistration.setRegistrationDate(textToTimesStamp(value.get(0).text() + " " +  value.get(1).text() ));
                        System.out.println(sicossByRegistration.getRegistrationDate());

                    }
                }
            });
        }
    }

    private Timestamp textToTimesStamp(String text) {
        return Timestamp.valueOf(text);

    }

    private void RequestSICOSSByNumber(Map<String, String> params, String URLConnection, String URLResponse) throws IOException {

        Connection.Response response = Jsoup
                .connect(URLConnection)
                .method(Connection.Method.GET)
                .execute();

        Document serviceResponse = Jsoup
                .connect(URLResponse)
                .userAgent("Mozilla/5.0")
                .timeout(10 * 1000)
                .cookies(response.cookies())
                .data(params)
                .data()

                .get();

        Elements tables = serviceResponse.select("table");
        if (tables.size() > 1) {
            Elements rows = tables.get(3).select("tr");

            rows.stream().forEach(p->{
                Elements value = p.select("td");
                if(value.size()>0) {
                    if (isNumeric(value.get(0).text())) {
                        System.out.println(value.get(0).text() + " - "+
                                value.get(1).text() + " - "+
                                value.get(2).text() + " - "+
                                value.get(3).text() + " - "+
                                value.get(4).text() + " - "+
                                value.get(5).text() + " - "+
                                value.get(6).text() + " - "+
                                value.get(7).text() + " - "+
                                value.get(8).text() + " - "+
                                value.get(9).text() + " - ");
                    }
                }
            });
        }
    }

    private boolean isNumeric(String text) {
        try {
            Integer.parseInt(text);
            return true;
        }catch (NumberFormatException exception){
            return false;
        }
    }


}
