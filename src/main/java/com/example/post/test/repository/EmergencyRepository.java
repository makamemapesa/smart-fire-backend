package com.example.post.test.repository;
import com.example.post.test.entity.Emergency;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EmergencyRepository extends JpaRepository<Emergency, Long> {
    List<Emergency> findByStatus(String status);
    List<Emergency> findByReporterId(Long reporterId);
    List<Emergency> findByDriverId(Long driverId);

}
