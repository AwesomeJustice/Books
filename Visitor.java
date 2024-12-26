import lombok.Data;
import java.util.List;

@Data
public class Visitor {
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private List<Book> favoriteBooks;
    private boolean subscribed;
}