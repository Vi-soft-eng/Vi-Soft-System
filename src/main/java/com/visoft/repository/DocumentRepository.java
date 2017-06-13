package com.visoft.repository;

import com.visoft.entity.model.Document;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
public interface DocumentRepository extends JpaRepository<Document, Long> {

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query("update Document doc set doc.deleteStatute = 1 where doc.documentId = :documentId")
    void turnOnDeleteStatute(@Param("documentId") Long id);
}
