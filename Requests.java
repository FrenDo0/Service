

public class Requests {
    private int requestID;
    private int clientID;
    private int carVin;
    private int brandID;
    private int modelID;
    private String dateLeave;
    private String datePickUp;
    private int service;
    private String status;

    public Requests(){};

    public Requests(int clientID,int carVin,int brandID, int modelID,String dateLeave, String datePickUp, int service, String status){
        this.clientID = clientID;
        this.carVin = carVin;
        this.brandID = brandID;
        this.modelID = modelID;
        this.dateLeave = dateLeave;
        this.datePickUp = datePickUp;
        this.service = service;
        this.status = status;
    }

    public int getRequestID() {
        return requestID;
    }

    public void setRequestID(int requestID) {
        this.requestID = requestID;
    }

    public int getClientID() {
        return clientID;
    }

    public void setClientID(int clientID) {
        this.clientID = clientID;
    }

    public int getCarVin() {
        return carVin;
    }

    public void setCarVin(int carVin) {
        this.carVin = carVin;
    }

    public int getBrandID() {
        return brandID;
    }

    public void setBrandID(int brandID) {
        this.brandID = brandID;
    }

    public int getModelID() {
        return modelID;
    }

    public void setModelID(int modelID) {
        this.modelID = modelID;
    }

    public String getDateLeave() {
        return dateLeave;
    }

    public void setDateLeave(String dateLeave) {
        this.dateLeave = dateLeave;
    }

    public String getDatePickUp() {
        return datePickUp;
    }

    public void setDatePickUp(String datePickUp) {
        this.datePickUp = datePickUp;
    }

    public int getService() {
        return service;
    }

    public void setService(int service) {
        this.service = service;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String toString(){
        Workers ws = new Workers();
        System.out.print("Request ID: "+this.requestID + " VIN number: " +this.carVin + " Brand: " + ws.GetBrandName(this.brandID)
                + " Model: " + ws.GetModelName(this.modelID) + " Date of leaving: "
                + this.dateLeave + " Date of taking: " + this.datePickUp
                + " Service: " + ws.GetService(this.service) + " Status: " + this.status + "\n");
        return "";
    }
}
