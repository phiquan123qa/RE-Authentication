package com.vn.reauthentication.repository;

import com.vn.reauthentication.entity.Wiki;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface WikiRepository extends JpaRepository<Wiki, Long>, JpaSpecificationExecutor<Wiki> {
}
