package mx.com.cfe.SSAC.api.datasyncapi.Models.Requests;

public class SICOSSByRegistrationModel {

    private String beginDate;
    private String endDate;
    private String zone;
    private String center;
    private String typeOrder;

    public String getBeginDate() {
        return beginDate;
    }

    public SICOSSByRegistrationModel setBeginDate(String beginDate) {
        this.beginDate = beginDate;
        return this;
    }

    public String getEndDate() {
        return endDate;
    }

    public SICOSSByRegistrationModel setEndDate(String endDate) {
        this.endDate = endDate;
        return this;
    }

    public String getZone() {
        return zone;
    }

    public SICOSSByRegistrationModel setZone(String zone) {
        this.zone = zone;
        return this;
    }

    public String getCenter() {
        return center;
    }

    public SICOSSByRegistrationModel setCenter(String center) {
        this.center = center;
        return this;
    }

    public String getTypeOrder() {
        return typeOrder;
    }

    public SICOSSByRegistrationModel setTypeOrder(String typeOrder) {
        this.typeOrder = typeOrder;
        return this;
    }
}
