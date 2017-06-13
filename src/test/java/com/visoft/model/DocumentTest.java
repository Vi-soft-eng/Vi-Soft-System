package com.visoft.model;

import com.visoft.AbstractTest;
import com.visoft.entity.model.Document;
import com.visoft.service.DocumentService;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class DocumentTest extends AbstractTest {

    @Autowired
    DocumentService documentService;

    @Test
    public void saveDocumentTest() {
        Document formDocument = new Document();
        boolean result = documentService.createOrUpdate(formDocument);

        Assert.assertTrue(result);
    }

    @Test
    public void loadDocumentTest() {
        Document formDocument = documentService.getDocumentById(1L);
        Assert.assertEquals(1L, formDocument == null ? 0 : formDocument.getDocumentId());
    }

    @Test
    public void deleteDocumentUpdateDeleteStatuteTest() {
        boolean result = documentService.delete(1L);
        Document formDocument = documentService.getDocumentById(1L);
        Assert.assertTrue(result);
        Assert.assertEquals(0, formDocument == null ? 0 : formDocument.getDocumentId());
    }

    @Test
    public void deleteDocumentPhysicallyTest() {
        boolean result = documentService.deletePhysically(1L);
        Assert.assertTrue(result);
    }
}
