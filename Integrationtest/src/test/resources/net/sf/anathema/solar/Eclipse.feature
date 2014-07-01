@Integration
Feature: Eclipse have the Caste ability to learn alien charms

  Scenario: Eclipse can learn alien charms if they meet the requirements
    Given any Solar with Caste Eclipse
    And I set her Essence to 3
    When she learns the Charm Mortal.TerrestrialCircleSorcery
    Then she knows the Charm Mortal.TerrestrialCircleSorcery

  Scenario: Eclipse forget alien charms on Caste change
    Given any Solar with Caste Eclipse
    And I set her Essence to 3
    When she learns the Charm Mortal.TerrestrialCircleSorcery
    And she changes her caste to Twilight
    Then she does not know the Charm Mortal.TerrestrialCircleSorcery
