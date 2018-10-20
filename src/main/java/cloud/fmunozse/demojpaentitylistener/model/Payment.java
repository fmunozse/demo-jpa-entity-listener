package cloud.fmunozse.demojpaentitylistener.model;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;

import javax.persistence.*;
import java.io.IOException;


@Entity
public class Payment {


    private final static ObjectMapper objectMapper = new ObjectMapper();

    @Id
    @GeneratedValue
    private Long id;

    private String name;

    private String payloadClazz;

    private String payloadRaw;

    @Transient
    private Object payload;

    public Payment() {
    }



    @PrePersist
    private void prePersist() throws JsonProcessingException {
        if (this.payload != null) {
            this.payloadRaw = objectMapper.writeValueAsString(this.payload);
        }
    }

    @PostLoad
    private void postLoad() throws ClassNotFoundException, IOException {
        if (this.payloadRaw != null && this.payloadClazz != null) {
            this.payload = objectMapper.readValue(this.payloadRaw, Class.forName(this.payloadClazz));
        }
    }

    public <T> T getPayload() {
        return (T) payload;
    }

    public void setPayload(Object payload) {
        this.payloadClazz = payload.getClass().getName();
        this.payload = payload;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
