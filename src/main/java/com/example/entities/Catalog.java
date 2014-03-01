package com.example.entities;

import java.util.ArrayList;
import java.util.Collection;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 * Created by ron on 3/1/14.
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Catalog {

    private String id;
    private String name;
    @XmlElement(nillable = true, required = true)
    private Collection<Catalog> children = new ArrayList<>();
    @XmlTransient
    private Catalog parent;

    public Catalog() {
    }

    public Catalog(String id, String catalogname) {
	this.id = id;
	this.name = catalogname;
    }

    public String getId() {
	return id;
    }

    public void setId(String id) {
	this.id = id;
    }

    public String getName() {
	return name;
    }

    public void setName(String name) {
	this.name = name;
    }

    public Collection<Catalog> getChildren() {
	return children;
    }

    public void setChildren(Collection<Catalog> children) {
	this.children = children;
    }

    public Catalog getParent() {
	return parent;
    }

    public void setParent(Catalog parent) {
	this.parent = parent;
    }

    public void addChild(Catalog catalog) {
	if (children == null) {
	    children = new ArrayList<>();
	}
	catalog.setParent(this);
	children.add(catalog);
    }
}
