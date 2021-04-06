package mx.com.cfe.SSAC.api.datasyncapi.Services;

import mx.com.cfe.SSAC.api.datasyncapi.Models.Requests.SICOSSByRegistrationModel;
import mx.com.cfe.SSAC.api.datasyncapi.Responses.SICOSSByRegistration;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
public class DataSyncService {
    public String URLSICOSS = "http://10.4.18.1/sicossweb/";
    public String URLPETICION = "http://10.4.18.1/cgi-bin/sicossweb/solicitudes/solsfra.cgi";
    public String URLNUMBERSICOSS = "http://10.4.18.1/cgi-bin/sicossweb/consulta/consulta.cgi?K1804107822";

    public void extraerInformacion() {

        SICOSSByRegistrationModel params = new SICOSSByRegistrationModel();
        params.setBeginDate("20210323");
        params.setEndDate("20210324");
        params.setCenter("K18");
        params.setTypeOrder("E");
        params.setZone("K18");


        try{
            RequestSICOSSByRegistrationDate(params);
        }catch(Exception e){
            System.out.println(e.getMessage());
        }


    }

    private void RequestSICOSSByRegistrationDate(SICOSSByRegistrationModel params) throws IOException {
        try {
            Connection.Response response = Jsoup
                    .connect(URLSICOSS)
                    .method(Connection.Method.GET)
                    .execute();

            Document serviceResponse = Jsoup
                    .connect(URLPETICION)
                    .userAgent("Mozilla/5.0")
                    .timeout(10 * 1000)
                    .cookies(response.cookies())
                    .data("fd", params.getBeginDate())
                    .data("fh", params.getEndDate())
                    .data("zona", params.getCenter())
                    .data("cen", "OC")
                    .data("sol", params.getTypeOrder())
                    .data("zn", params.getCenter())
                    .data("centros", "OC")
                    .get();

            Elements tables = serviceResponse.select("table");
            if (tables.size() > 1) {
                Elements rows = tables.get(3).select("tr");

                SICOSSByRegistration sicossByRegistration = rowsElementsExtractionRegistrationDate(rows);

              //  System.out.println(serviceResponse.outerHtml());
            }
        }catch(Exception e){
            System.out.println(e.getMessage());
        }


    }

    private SICOSSByRegistration rowsElementsExtractionRegistrationDate(Elements rows){
        SICOSSByRegistration regresar = rows.stream().filter(p->p.select("td").size()>0).map(p -> {
            Elements value = p.select("td");
            if (value.size() > 0) {
                if (isNumeric(value.get(0).text())) {
                    String registrationDateText = value.get(1).text().replace("/","-") + " " + value.get(2).text()+":00.00000";
                    String endDateText = value.get(12).text().replace("/","-") + " " + value.get(13).text()+":00.00000";

                    Timestamp registrationDate=textToTimesStamp(registrationDateText);
                    String registrationRPE= value.get(3).text().trim();
                    String registrationType= value.get(4).text().trim();
                    String orderSICOSS= value.get(5).text().trim();
                    String status= value.get(6).text().trim();
                    String centerOrigin= value.get(7).text().trim();
                    String visits= value.get(8).text().trim();
                    String rate= value.get(9).text().trim();
                    String threads= value.get(10).text().trim();
                    String workplace= value.get(11).text().trim();
                    Timestamp endDate=textToTimesStamp(endDateText);
                    String endRPE= value.get(14).text().trim();
                    String endType= value.get(15).text().trim();
                    String rpu= value.get(16).text().trim();
                    String account= value.get(17).text().trim();
                    String name= value.get(18).text().trim();
                    String address= value.get(19).text().trim();
                    String colonie= value.get(20).text().trim();
                    String agency= value.get(21).text().trim();
                    String firstCauseOfTermination= value.get(22).text().trim();
                    String secondCauseOfTermination= value.get(23).text().trim();
                    String thirdCauseOfTermination= value.get(24).text().trim();
                    String fourthCauseOfTermination= value.get(25).text().trim();
                    String fifthCauseOfTermination= value.get(26).text().trim();

                    if(Optional.ofNullable(registrationDate).isPresent() && Optional.ofNullable(endDate).isPresent()){
                        System.out.println("la conversion de la fecha " + registrationDateText + " es correcta");

                        SICOSSByRegistration sicossByRegistration = new SICOSSByRegistration();
                        sicossByRegistration.setRegistrationDate(registrationDate);
                        sicossByRegistration.setRegistrationRPE(registrationRPE);
                        sicossByRegistration.setRegistrationType(registrationType);
                        sicossByRegistration.setOrderSICOSS(orderSICOSS);
                        sicossByRegistration.setStatus(status);
                        sicossByRegistration.setCenterOrigin(centerOrigin);
                        sicossByRegistration.setVisits(visits);
                        sicossByRegistration.setRate(rate);
                        sicossByRegistration.setThreads(threads);
                        sicossByRegistration.setWorkplace(workplace);
                        sicossByRegistration.setEndRPE(endRPE);
                        sicossByRegistration.setEndType(endType);
                        sicossByRegistration.setRpu(rpu);
                        sicossByRegistration.setAccount(account);
                        sicossByRegistration.setName(name);
                        sicossByRegistration.setAddress(address);
                        sicossByRegistration.setColonie(colonie);
                        sicossByRegistration.setAgency(agency);
                        sicossByRegistration.setFirstCauseOfTermination(firstCauseOfTermination);
                        sicossByRegistration.setSecondCauseOfTermination(secondCauseOfTermination);
                        sicossByRegistration.setThirdCauseOfTermination(thirdCauseOfTermination);
                        sicossByRegistration.setFourthCauseOfTermination(fourthCauseOfTermination);
                        sicossByRegistration.setFifthCauseOfTermination(fifthCauseOfTermination);

                        return sicossByRegistration;
                    }
                }
            }
            return new SICOSSByRegistration();
        }).collect(Collectors.toList());
        return regresar;
    }

        private boolean isNumeric(String text){
            try {
                Integer.parseInt(text);
                return true;
            } catch (NumberFormatException exception) {
                return false;
            }
        }

        private Timestamp textToTimesStamp(String text) {
                try {
                    return Timestamp.valueOf(text);
                }catch(Exception e){
                    return null;
                }


        }
    }