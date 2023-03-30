package com.example.application.data.service;

import com.example.application.data.entity.Zakazi;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface ZakaziRepository extends JpaRepository<Zakazi, Long>, JpaSpecificationExecutor<Zakazi> {

}
