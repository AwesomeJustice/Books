import lombok.Data;

@Data
public class SMSMessage {
    private String phoneNumber;
    private String message;
}