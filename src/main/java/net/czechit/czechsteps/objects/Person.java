package net.czechit.czechsteps.objects;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.google.cloud.datastore.Entity;


public class Person
{
    private final String id;
    private final String name;

    public Person(Entity entity) {
        this.id = entity.getKey().getName();
        this.name = entity.getString("Name");
    }

}