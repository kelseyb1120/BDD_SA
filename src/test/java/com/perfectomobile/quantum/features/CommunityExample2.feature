@tag2
Feature: community login 2
  DATAFILE=src/test/resources/data/testData.csv
  #DATAFILE=src/test/resources/data/testData.xls

  @tag2
  Scenario: open community 2
    Given I open browser to webpage "https://community.perfectomobile.com/"
    When I go to login page
    #Then "bad" should exist
    #Then "login.user" must exist

  @tag2
  Scenario: login 2
    Given I fill user "<username>" and password "<password>"
    When I click on "button.submit"
    Then I take a screenshot
		
 	  
		