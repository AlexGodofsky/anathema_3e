package net.sf.anathema.character.framework.xml.util;

import net.sf.anathema.hero.template.points.CurrentRatingCost;
import net.sf.anathema.hero.template.points.FixedValueRatingCost;
import net.sf.anathema.hero.template.points.MultiplyRatingCost;
import net.sf.anathema.hero.template.points.ThresholdRatingCost;
import net.sf.anathema.lib.exception.PersistenceException;
import net.sf.anathema.lib.xml.ElementUtilities;
import org.dom4j.Element;

import java.text.MessageFormat;

import static net.sf.anathema.lib.xml.ElementUtilities.getIntAttrib;
import static net.sf.anathema.lib.xml.ElementUtilities.getRequiredElement;
import static net.sf.anathema.lib.xml.ElementUtilities.getRequiredIntAttrib;

public class CostParser {

  private static final String TAG_FIXED_COST = "fixedCost";
  private static final String ATTRIB_COST = "cost";

  private static final String TAG_CURRENT_RATING_COSTS = "currentRating";
  private static final String ATTRIB_INITIALCOST = "initialCosts";
  private static final String ATTRIB_MULTIPLIER = "multiplier";
  private static final String ATTRIB_SUMMAND = "summand";

  private static final String TAG_THRESHOLD_COST = "thresholdCost";
  private static final String ATTRIB_LOW_COST = "lowCost";
  private static final String ATTRIB_HIGH_COST = "highCost";
  private static final String ATTRIB_THRESHOLD = "threshold";

  public int getFixedCostFromRequiredElement(Element element, String elementName) throws PersistenceException {
    Element parentElement = getRequiredElement(element, elementName);
    return getFixedCostValue(parentElement);
  }

  public int getFixedCostFromOptionalElement(Element element, String elementName, int defaultValue) throws PersistenceException {
    Element parentElement = element.element(elementName);
    if (parentElement == null) {
      return defaultValue;
    }
    return getFixedCostValue(parentElement);
  }

  public CurrentRatingCost getMultiplyRatingCostsFromRequiredElement(Element parentElement, String tagName) throws PersistenceException {
    Element element = getRequiredElement(parentElement, tagName);
    return getMultiplyRatingCosts(element);
  }

  public CurrentRatingCost getCosts(Element parentElement) {
    if (ElementUtilities.hasChild(parentElement, TAG_CURRENT_RATING_COSTS)) {
      return getMultiplyRatingCosts(parentElement);
    }
    if (ElementUtilities.hasChild(parentElement, TAG_THRESHOLD_COST)) {
      return getThresholdRatingCosts(parentElement);
    }
    if (ElementUtilities.hasChild(parentElement, TAG_FIXED_COST)) {
      return getFixedCost(parentElement);
    }
    String pattern = "Expected a cost-defining child below element {0}, but found none. Legal types are {1}, {2} and {3}.";
    String message = MessageFormat.format(pattern, parentElement.getName(), TAG_CURRENT_RATING_COSTS, TAG_THRESHOLD_COST, TAG_FIXED_COST);
    throw new IllegalArgumentException(message);
  }

  public CurrentRatingCost getMultiplyRatingCosts(Element parentElement) throws PersistenceException {
    Element element = getRequiredElement(parentElement, TAG_CURRENT_RATING_COSTS);
    int multiplier = getRequiredIntAttrib(element, ATTRIB_MULTIPLIER);
    int summand = getIntAttrib(element, ATTRIB_SUMMAND, 0);
    int initialCost = getIntAttrib(element, ATTRIB_INITIALCOST, Integer.MIN_VALUE);
    return new MultiplyRatingCost(multiplier, initialCost, summand);
  }

  public CurrentRatingCost getThresholdRatingCosts(Element parentElement) {
    Element costElement = getRequiredElement(parentElement, TAG_THRESHOLD_COST);
    int lowCost = getRequiredIntAttrib(costElement, ATTRIB_LOW_COST);
    int highCost = getRequiredIntAttrib(costElement, ATTRIB_HIGH_COST);
    int threshold = getRequiredIntAttrib(costElement, ATTRIB_THRESHOLD);
    return new ThresholdRatingCost(lowCost, highCost, threshold);
  }

  public CurrentRatingCost getFixedCost(Element parentElement) {
    int fixedCost = getFixedCostValue(parentElement);
    return new FixedValueRatingCost(fixedCost);
  }

  private int getFixedCostValue(Element parentElement) {
    Element fixedCostElement = getRequiredElement(parentElement, TAG_FIXED_COST);
    return getRequiredIntAttrib(fixedCostElement, ATTRIB_COST);
  }
}