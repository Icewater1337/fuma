package ch.fuma.forms;

import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.Date;

/**
 * Created by Icewater on 24/12/2017.
 */
public class EventForm {

    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    private Date from;

    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    private Date until;


    private String description;


    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getFrom() {
        return from;
    }

    public void setFrom(Date from) {
        this.from = from;
    }

    public Date getUntil() {
        return until;
    }

    public void setUntil(Date until) {
        this.until = until;
    }
}
