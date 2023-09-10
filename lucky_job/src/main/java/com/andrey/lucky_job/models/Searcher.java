package com.andrey.lucky_job.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Searcher {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    private String surname;
    private int age;
    private int jobExp;
    private int jobTarget;

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getSurname() {
        return surname;
    }
    public void setSurname(String surname) {
        this.surname = surname;
    }
    public int getAge() {
        return age;
    }
    public void setAge(int age) {
        this.age = age;
    }
    public int getJobExp() {
        return jobExp;
    }
    public void setJobExp(int jobExp) {
        this.jobExp = jobExp;
    }
    public int getJobTarget() {
        return jobTarget;
    }
    public void setJobTarget(int jobTarget) {
        this.jobTarget = jobTarget;
    }


    public Searcher() {
    }

    public Searcher(String name, String surname, int age, int jobExp, int jobTarget) {
        this.name = name;
        this.surname = surname;
        this.age = age;
        this.jobExp = jobExp;
        this.jobTarget = jobTarget;
    }
}
