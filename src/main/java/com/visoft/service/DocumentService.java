package com.visoft.service;

import com.visoft.entity.model.Document;
import com.visoft.repository.DocumentRepository;

import java.util.List;

public interface DocumentService {

    Document getDocumentById(Long id);

    Iterable<Document> getAllDocuments();

    List<Document> searchDocuments();

    boolean createOrUpdate(Document document);

    boolean delete(Long id);

    boolean deletePhysically(Long id);

}
