Feature: E2E some pages
Description: Check some errors

  Scenario: User is able to navigate to multimodal page with response code 200
    Given a chrome browser
    When user loads the multimodal page
    Then page loads without javascript errors
    Then response code is 200
    And the browser is closed

  Scenario: User is able to navigate to htmlcss page with response code 200
    Given a chrome browser
    When user loads the htmlcss page
    Then page loads without javascript errors
    Then response code is 200
    And the browser is closed

  Scenario: User is able to navigate to bad page with response code 404
    Given a chrome browser
    When user loads the bad page
    Then response code is 404
    Then page loads without javascript errors
    And the browser is closed

  Scenario: All links on multimodal page are working
    Given a chrome browser
    When user loads the multimodal page
    Then page loads without javascript errors
    When user clicks on all links
    Then all links load to a live page
    And the browser is closed

  Scenario: All links on htmlcss page are working
    Given a chrome browser
    When user loads the htmlcss page
    Then page loads without javascript errors
    When user clicks on all links
    Then all links load to a live page
    And the browser is closed

  Scenario: All links on bad page are working
    Given a chrome browser
    When user loads the bad page
    And user clicks on all links
    Then all links load to a live page
    And the browser is closed
