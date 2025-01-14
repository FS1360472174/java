package com.structurizr.model;

import com.structurizr.AbstractWorkspaceTestBase;
import com.structurizr.Workspace;
import org.junit.Assert;
import org.junit.Test;

import static junit.framework.Assert.assertSame;
import static junit.framework.TestCase.assertNull;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class ElementTests extends AbstractWorkspaceTestBase {

    @Test
    public void test_getTags_WhenThereAreNoTags() {
        Element element = model.addSoftwareSystem(Location.Internal, "Name", "Description");
        assertEquals("Element,Software System", element.getTags());
    }

    @Test
    public void test_getTags_ReturnsTheListOfTags_WhenThereAreSomeTags() {
        Element element = model.addSoftwareSystem(Location.Internal, "Name", "Description");
        element.addTags("tag1", "tag2", "tag3");
        assertEquals("Element,Software System,tag1,tag2,tag3", element.getTags());
    }

    @Test
    public void test_setTags_DoesNotDoAnything_WhenPassedNull() {
        Element element = model.addSoftwareSystem(Location.Internal, "Name", "Description");
        element.setTags(null);
        assertEquals("Element,Software System", element.getTags());
    }

    @Test
    public void test_addTags_DoesNotDoAnything_WhenPassedNull() {
        Element element = model.addSoftwareSystem(Location.Internal, "Name", "Description");
        element.addTags((String)null);
        assertEquals("Element,Software System", element.getTags());

        element.addTags(null, null, null);
        assertEquals("Element,Software System", element.getTags());
    }

    @Test
    public void test_addTags_AddsTags_WhenPassedSomeTags() {
        Element element = model.addSoftwareSystem(Location.Internal, "Name", "Description");
        element.addTags(null, "tag1", null, "tag2");
        assertEquals("Element,Software System,tag1,tag2", element.getTags());
    }

    @Test
    public void test_addTags_AddsTags_WhenPassedSomeTagsAndThereAreDuplicateTags() {
        Element element = model.addSoftwareSystem(Location.Internal, "Name", "Description");
        element.addTags(null, "tag1", null, "tag2", "tag2");
        assertEquals("Element,Software System,tag1,tag2", element.getTags());
    }

    @Test
    public void test_equals_ReturnsTrue_WhenTheSameObjectIsPassed() {
        SoftwareSystem softwareSystem = model.addSoftwareSystem(Location.Internal, "SystemA", "");
        assertTrue(softwareSystem.equals(softwareSystem));
    }

    @Test
    public void test_equals_ReturnsTrue_WhenTheAnObjectWithTheSameCanonicalNameIsPassed() {
        SoftwareSystem softwareSystem1 = new Workspace("", "").getModel().addSoftwareSystem(Location.Internal, "SystemA", "");
        SoftwareSystem softwareSystem2 = new Workspace("", "").getModel().addSoftwareSystem(Location.Internal, "SystemA", "");
        assertTrue(softwareSystem1.equals(softwareSystem2));
        assertTrue(softwareSystem2.equals(softwareSystem1));
    }

    @Test
    public void test_hasEfferentRelationshipWith_ReturnsFalse_WhenANullElementIsSpecified() {
        SoftwareSystem softwareSystem1 = new Workspace("", "").getModel().addSoftwareSystem(Location.Internal, "SystemA", "");
        assertFalse(softwareSystem1.hasEfferentRelationshipWith(null));
    }

    @Test
    public void test_hasEfferentRelationshipWith_ReturnsFalse_WhenTheSameElementIsSpecifiedAndNoCyclicRelationshipExists() {
        SoftwareSystem softwareSystem1 = new Workspace("", "").getModel().addSoftwareSystem(Location.Internal, "SystemA", "");
        assertFalse(softwareSystem1.hasEfferentRelationshipWith(softwareSystem1));
    }

    @Test
    public void test_hasEfferentRelationshipWith_ReturnsTrue_WhenTheSameElementIsSpecifiedAndACyclicRelationshipExists() {
        SoftwareSystem softwareSystem1 = new Workspace("", "").getModel().addSoftwareSystem(Location.Internal, "SystemA", "");
        softwareSystem1.uses(softwareSystem1, "uses");
        assertTrue(softwareSystem1.hasEfferentRelationshipWith(softwareSystem1));
    }

    @Test
    public void test_hasEfferentRelationshipWith_ReturnsTrue_WhenThereIsARelationship() {
        SoftwareSystem softwareSystem1 = new Workspace("", "").getModel().addSoftwareSystem(Location.Internal, "SystemA", "");
        SoftwareSystem softwareSystem2 = new Workspace("", "").getModel().addSoftwareSystem(Location.Internal, "SystemA", "");
        softwareSystem1.uses(softwareSystem2, "uses");
        assertTrue(softwareSystem1.hasEfferentRelationshipWith(softwareSystem2));
    }

    @Test
    public void test_getEfferentRelationshipWith_ReturnsNull_WhenANullElementIsSpecified() {
        SoftwareSystem softwareSystem1 = new Workspace("", "").getModel().addSoftwareSystem(Location.Internal, "SystemA", "");
        assertNull(softwareSystem1.getEfferentRelationshipWith(null));
    }

    @Test
    public void test_getEfferentRelationshipWith_ReturnsNull_WhenTheSameElementIsSpecifiedAndNoCyclicRelationshipExists() {
        SoftwareSystem softwareSystem1 = new Workspace("", "").getModel().addSoftwareSystem(Location.Internal, "SystemA", "");
        assertNull(softwareSystem1.getEfferentRelationshipWith(softwareSystem1));
    }

    @Test
    public void test_getEfferentRelationshipWith_ReturnsCyclicRelationship_WhenTheSameElementIsSpecifiedAndACyclicRelationshipExists() {
        SoftwareSystem softwareSystem1 = new Workspace("", "").getModel().addSoftwareSystem(Location.Internal, "SystemA", "");
        softwareSystem1.uses(softwareSystem1, "uses");

        Relationship relationship = softwareSystem1.getEfferentRelationshipWith(softwareSystem1);
        assertSame(softwareSystem1, relationship.getSource());
        assertEquals("uses", relationship.getDescription());
        assertSame(softwareSystem1, relationship.getDestination());
    }

    @Test
    public void test_getEfferentRelationshipWith_ReturnsTheRelationship_WhenThereIsARelationship() {
        SoftwareSystem softwareSystem1 = new Workspace("", "").getModel().addSoftwareSystem(Location.Internal, "SystemA", "");
        SoftwareSystem softwareSystem2 = new Workspace("", "").getModel().addSoftwareSystem(Location.Internal, "SystemA", "");
        softwareSystem1.uses(softwareSystem2, "uses");

        Relationship relationship = softwareSystem1.getEfferentRelationshipWith(softwareSystem1);
        assertSame(softwareSystem1, relationship.getSource());
        assertEquals("uses", relationship.getDescription());
        assertSame(softwareSystem2, relationship.getDestination());
    }

}