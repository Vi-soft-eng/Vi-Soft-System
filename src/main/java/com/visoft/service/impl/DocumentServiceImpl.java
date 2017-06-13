package com.visoft.service.impl;

import com.visoft.entity.model.Document;
import com.visoft.repository.DocumentRepository;
import com.visoft.service.DocumentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DocumentServiceImpl implements DocumentService {

    private final Logger LOG = LoggerFactory.getLogger(this.getClass());

    @Autowired
    DocumentRepository documentRepository;

    @Override
    public Document getDocumentById(Long id) {
        try {
            return documentRepository.findOne(id);
        } catch (Exception e) {
            LOG.error("ERROR FINDING DATA: " + e.getMessage(), e);
            return null;
        }
    }

    @Override
    public Iterable<Document> getAllDocuments() {
        try {
            return documentRepository.findAll();
        } catch (Exception e) {
            LOG.error("ERROR FINDING DATA: " + e.getMessage(), e);
            return null;
        }
    }

    @Override
    public List<Document> searchDocuments() {
        return null;
    }

    @Override
    public boolean createOrUpdate(Document document) {
        try {
            documentRepository.save(document);
            return true;
        } catch (Exception e) {
            LOG.error("ERROR SAVING DATA: " + e.getMessage(), e);
            return false;
        }
    }

    @Override
    public boolean delete(Long id) {
        try {
            documentRepository.turnOnDeleteStatute(id);
            return true;
        } catch (Exception e) {
            LOG.error("ERROR DELETING DATA: " + e.getMessage(), e);
            return false;
        }
    }

    @Override
    public boolean deletePhysically(Long id) {
        try {
            documentRepository.delete(id);
            return true;
        } catch (Exception e) {
            LOG.error("ERROR DELETING DATA: " + e.getMessage(), e);
            return false;
        }
    }
}
