package digiovannialessandro.u5d9.payloads;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class NewBlogPostPayload {
    private int authorId;
    @NotEmpty
    @Size(min = 2,max = 15,message = "La categoria deve essere compresa tra2 e 15 caratteri")
    private String category;
    @NotEmpty
    @Size(min = 10,max = 200,message = "Il contenuto deve essere di almeno 10 caratteri e non deve superari i 200")
    private String content;
    @Positive
    private double readingTime;
    @NotEmpty
    @Size(min = 2,max = 30,message = "Il titolo deve essere compreso tra i 2 e i 30 caratteri")
    private String title;
}
