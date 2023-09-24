package com.andrey.lucky_job.models;

import jakarta.persistence.*;

import java.util.Date;

@Entity
public class CV {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private Long vacancyId;
    private String author;
    private String title;
    @Lob
    @Column(columnDefinition = "MEDIUMBLOB")
    private byte[] imageData;
    private Date dateOfPublication;


    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getAuthor() {
        return author;
    }
    public void setAuthor(String author) {
        this.author = author;
    }
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }


    public byte[] getImageData() {
        return imageData;
    }
    public void setImageData(byte[] imageData) {
        this.imageData = imageData;
    }


    public Date getDateOfPublication() {
        return dateOfPublication;
    }
    public void setDateOfPublication(Date dateOfPublication) {
        this.dateOfPublication = dateOfPublication;
    }
    public Long getVacancyId() {
        return vacancyId;
    }
    public void setVacancyId(Long vacancyId) {
        this.vacancyId = vacancyId;
    }


    public CV() {
    }
    public CV(String author, String title, byte [] imageData, Date dateOfPublication, Long vacancyId) {
        this.author = author;
        this.title = title;
        this.imageData = imageData;
        this.dateOfPublication = dateOfPublication;
        this.vacancyId = vacancyId;
    }
}
