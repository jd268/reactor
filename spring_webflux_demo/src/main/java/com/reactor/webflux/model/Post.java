package com.reactor.webflux.model;

import java.util.Date;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;


/**
 * @author jdeshmukh
 * @date 08-Feb-2020
 * 
 */
@Document(collection = "posts")
public class Post {
	
	
	@Id
    private String id;

    @NotBlank
    @Size(max = 140)
    private String text;

    @NotNull
    private Date createdAt = new Date();

    public Post() {

    }

    public Post(String text) {
        this.text = text;
    }


    public String getid() {
		return id;
	}

	public void set_id(String id) {
		this.id = id;
	}

	public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }
}