package net.sf.anathema.hero.spells.parser;

import net.sf.anathema.lib.exception.PersistenceException;
import org.dom4j.Element;

import java.util.List;

public class ElementUtilities {

  public static int getRequiredIntAttrib(Element element, String name) throws PersistenceException {
    String value = getRequiredAttrib(element, name);
    try {
      return Integer.parseInt(value);
    } catch (NumberFormatException e) {
      String message = "Attribute " + name + " must be of value integer, but was: " + value;
      throw new PersistenceException(message);
    }
  }

  public static Integer getRequiredIntOrVariesAttrib(Element element, String name) throws PersistenceException {
    String value = getRequiredAttrib(element, name);
    try {
      return new Integer(value);
    } catch (NumberFormatException e) {
      if (value.equals("Varies")) {
        return null;
      } else {
        String message = "Attribute " + name + " must be of value \"Varies\" or integer, but was: " + value;
        throw new PersistenceException(message);
      }
    }
  }

  public static int getIntAttrib(Element element, String name, int nullValue) throws PersistenceException {
    String value = element.attributeValue(name);
    if (value == null) {
      return nullValue;
    }
    try {
      return Integer.parseInt(value);
    } catch (NumberFormatException e) {
      String message = "Attribute " + name + " must be of value integer, but was: " + value;
      throw new PersistenceException(message);
    }
  }

  public static String getRequiredAttrib(Element element, String name) throws PersistenceException {
    String value = element.attributeValue(name);
    if (value == null) {
      throw new PersistenceException("Required attribute not found " + name);
    }
    return value;
  }

  public static Element getRequiredElement(Element parent, String tagName) throws PersistenceException {
    Element element = parent.element(tagName);
    if (element == null) {
      throw new PersistenceException("Required element not found " + tagName);
    }
    return element;
  }

  public static boolean getBooleanAttribute(Element element, String name, boolean defaultValue) {
    String attributeValue = element.attributeValue(name);
    if (attributeValue == null) {
      return defaultValue;
    }
    return Boolean.parseBoolean(attributeValue);
  }

  @SuppressWarnings("unchecked")
  public static List<Element> elements(Element parent) {
    return parent.elements();
  }

  @SuppressWarnings("unchecked")
  public static List<Element> elements(Element parent, String tagName) {
    return parent.elements(tagName);
  }
}