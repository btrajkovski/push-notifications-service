package com.btrajkovski.push.notifications.repository;

import com.btrajkovski.push.notifications.model.AppleCertificate;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by bojan on 27.3.16.
 */
public interface AppleCertificateRepository extends JpaRepository<AppleCertificate, Long> {
}
