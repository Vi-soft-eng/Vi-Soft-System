package com.visoft.entity.model;

import javax.persistence.*;

@Entity
@Table(name="FORM_DOCUMENTS")
public class Document implements DomainObject {

    private Long documentId;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seqGen")
    @SequenceGenerator(name="seqGen",sequenceName="DOCUMENT_SEQ", allocationSize=1)
    @Column(name = "DOCUMENT_ID")
    public Long getDocumentId() {
        return documentId;
    }

    public void setDocumentId(Long documentId) {
        this.documentId = documentId;
    }
}
