package yaruliy.lgaming;

public class Request{
    private String data;
    private final int pointNumber = 327;
    private final int serviceNumber = 4390;
    public Request(){}

    public String XMLRequestAsString(){
        this.data =
                "<request point=\"" + pointNumber + "\">" +
                "<verify service=\"" + serviceNumber + "\" account=\"12345\"/>" +
                "</request>";
        return this.data;
    }
}