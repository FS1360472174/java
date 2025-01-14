package com.structurizr.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * A software system, which itself can be made up of a number of containers.
 */
public class SoftwareSystem extends Element {

    private Location location = Location.Unspecified;

    private Set<Container> containers = new LinkedHashSet<>();

    @Override
    @JsonIgnore
    public Element getParent() {
        return null;
    }

    SoftwareSystem() {
    }

    /**
     * Gets the location of this softare system.
     *
     * @return  a Location
     */
    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        if (location != null) {
            this.location = location;
        } else {
            this.location = Location.Unspecified;
        }
    }

    void add(Container container) {
        containers.add(container);
    }

    /**
     * Gets the set of containers within this software system.
     *
     * @return  a Set of Container objects
     */
    public Set<Container> getContainers() {
        return new HashSet<>(containers);
    }

    /**
     * Adds a container with the specified name, description and technology
     * (unless one exists with the same name already).
     *
     * @param name              the name of the container (e.g. "Web Application")
     * @param description       a short description/list of responsibilities
     * @param technology        the technoogy choice (e.g. "Spring MVC", "Java EE", etc)
     * @return      the newly created Container instance added to the model (or null)
     */
    public Container addContainer(String name, String description, String technology) {
        return getModel().addContainer(this, name, description, technology);
    }

    /**
     * Gets the container with the specified name
     * (or null if it doesn't exist).
     */
    public Container getContainerWithName(String name) {
         for (Container container : getContainers()) {
             if (container.getName().equals(name)) {
                 return container;
             }
         }

         return null;
     }

    /**
     * Gets the container with the specified ID
     * (or null if it doesn't exist).
     */
    public Container getContainerWithId(String id) {
         for (Container container : getContainers()) {
             if (container.getId().equals(id)) {
                 return container;
             }
         }

         return null;
     }

    /**
     * Adds a unidirectional relationship between this software system and a person.
     *
     * @param destination   the target of the relationship
     * @param description   a description of the relationship (e.g. "sends e-mail to")
     */
    public Relationship delivers(Person destination, String description) {
        Relationship relationship = new Relationship(this, destination, description);
        getModel().addRelationship(relationship);

        return relationship;
    }

    /**
     * Adds a unidirectional relationship between this software system and a person.
     *
     * @param destination   the target of the relationship
     * @param description   a description of the relationship (e.g. "sends e-mail to")
     * @param technology    the technology details (e.g. JSON/HTTPS)
     */
    public Relationship delivers(Person destination, String description, String technology) {
        Relationship relationship = new Relationship(this, destination, description, technology);
        getModel().addRelationship(relationship);

        return relationship;
    }

    /**
     * Adds a unidirectional relationship between this software system and a person.
     *
     * @param destination       the target of the relationship
     * @param description       a description of the relationship (e.g. "sends e-mail to")
     * @param technology        the technology details (e.g. JSON/HTTPS)
     * @param interactionStyle  the interaction style (sync vs async)
     */
    public Relationship delivers(Person destination, String description, String technology, InteractionStyle interactionStyle) {
        Relationship relationship = new Relationship(this, destination, description, technology, interactionStyle);
        getModel().addRelationship(relationship);

        return relationship;
    }

    @Override
    public String getCanonicalName() {
        return CANONICAL_NAME_SEPARATOR + formatForCanonicalName(getName());
    }

    @Override
    protected Set<String> getRequiredTags() {
        return new LinkedHashSet<>(Arrays.asList(Tags.ELEMENT, Tags.SOFTWARE_SYSTEM));
    }

}
