@tag1
Feature: community login shani

  @tag1
  Scenario: login to community
    Given I open browser to webpage "https://community.perfectomobile.com/"
    When I go to login page
    Then "login.user" must exist
    When I fill user "beton.yatsuk@gmail.com" and password "ABC123"
    And I click on "button.submit"
    Then I wait for "5" seconds
    And I click on "button.menu"
    And I should see text "Hi, Beton!"
    And I take a screenshot

  @tag1
  Scenario: search community
    Given I open browser to webpage "https://community.perfectomobile.com/"
    When I click on "button.search"
    And I enter "Appium" to "community.search"
    Then I must see text "Popular"

