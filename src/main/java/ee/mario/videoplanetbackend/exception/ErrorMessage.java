package ee.mario.videoplanetbackend.exception;

import lombok.Data;

import java.util.Date;

@Data
public class ErrorMessage {
    private String message;
    private Date date;
    private int status;
}
