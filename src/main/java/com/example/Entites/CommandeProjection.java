package com.example.Entites;

import org.springframework.data.rest.core.config.Projection;

import java.util.Collection;
import java.util.Date;

@Projection(name="p4",types = {Commande.class})
public interface CommandeProjection {
    Long getId();
    Date getDate();
    Float getTotal();
    String getCmdDescription();
    Collection<ProductItem> getProductItems();
    Userr getUserr();

}
