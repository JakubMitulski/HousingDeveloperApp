package capgemini.entities.listeners;

import capgemini.entities.AbstractEntity;

import javax.persistence.PrePersist;
import java.sql.Timestamp;
import java.util.Date;

public class CreateListener {

    @PrePersist
    public void onCreation(final AbstractEntity abstractEntity) {
        Date createDate = new Date();
        abstractEntity.setCreated(new Timestamp(createDate.getTime()));
    }
}
