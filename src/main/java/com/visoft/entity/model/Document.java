package com.visoft.entity.model;

import org.hibernate.annotations.Where;

import javax.persistence.*;
import javax.persistence.criteria.CriteriaBuilder;

@Entity
@Table(name="FORM_DOCUMENTS")
@Where(clause = "DELETE_STATUTE = 0")
public class Document implements DomainObject {

    private Long documentId;
    private Integer deleteStatute;

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

    @Column(name = "DELETE_STATUTE")
    public Integer getDeleteStatute() {
        return deleteStatute;
    }

    public void setDeleteStatute(Integer deleteStatute) {
        this.deleteStatute = deleteStatute;
    }
}
