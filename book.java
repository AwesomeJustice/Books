import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Data
public class Book {
    private String title;
    private String author;
    private int year;
    private String isbn;
    private String publisher;
}