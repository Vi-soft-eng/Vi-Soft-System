package com.visoft.entity.model;

import org.hibernate.annotations.Where;

import javax.persistence.*;

@Entity
@Table(name="FORM_DOCUMENTS")
@Where(clause = "delete_statute = 0")
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
