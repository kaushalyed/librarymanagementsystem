package com.test.librarymanagementsystem.model;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name="book")
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column
    private String name;

    @Column
    private String title;

    @Column
    private Integer pages;

    @Column(name="publish_date")
    private Date publishDate;

    @ManyToOne
    @JoinColumn(name="author")
    private Author author;

}
