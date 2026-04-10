package com.ume.studentsystem.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ume.studentsystem.helper.SpringContext;
import com.ume.studentsystem.model.AuditLog;
import com.ume.studentsystem.repository.AuditLogRepository;
import jakarta.persistence.PostPersist;
import jakarta.persistence.PostRemove;
import jakarta.persistence.PostUpdate;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.lang.reflect.Field;
import java.time.LocalDateTime;

public class EntityAuditListener {

    @PostPersist
    public void onPostPersist(Object entity) {
        saveAudit(entity, "CREATE", null, entity);
    }

    @PostUpdate
    public void onPostUpdate(Object entity) {
        saveAudit(entity, "UPDATE", null, entity);
    }

    @PostRemove
    public void onPostRemove(Object entity) {
        saveAudit(entity, "DELETE", entity, null);
    }

    private void saveAudit(Object entity, String action, Object oldObj, Object newObj) {

        AuditLogRepository repo = SpringContext.getBean(AuditLogRepository.class);

        AuditLog log = new AuditLog();
        log.setEntityName(entity.getClass().getSimpleName());
        log.setAction(action);

        Long id = extractId(entity);
        log.setEntityId(id);

        log.setTimestamp(LocalDateTime.now());
        log.setUsername(getCurrentUser());

        if (oldObj != null)
            log.setOldValues(toJson(oldObj));

        if (newObj != null)
            log.setNewValues(toJson(newObj));

        repo.save(log);
    }

    private Long extractId(Object entity) {
        try {
            Field id = entity.getClass().getDeclaredField("id");
            id.setAccessible(true);
            return (Long) id.get(entity);
        } catch (Exception e) {
            return null;
        }
    }

    private String getCurrentUser() {
        try {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            return (auth == null) ? "SYSTEM" : auth.getName();
        } catch (Exception e) {
            return "SYSTEM";
        }
    }

    private String toJson(Object object) {
        try {
            return new ObjectMapper().writeValueAsString(object);
        } catch (Exception e) {
            return object.toString();
        }
    }
}