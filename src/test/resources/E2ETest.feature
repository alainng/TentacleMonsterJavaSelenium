Feature: E2E some pages
Description: Check some errors

  Scenario: User is able to navigate to home page with response code 200
    Given a chrome browser
    When user loads the home page
    Then page loads without javascript errors
    Then response code is 200
    And the browser is closed

  Scenario: User is able to navigate to prices page with response code 200
    Given a chrome browser
    When user loads the prices page
    Then page loads without javascript errors
    Then response code is 200
    And the browser is closed

  Scenario: User is able to navigate to doesntexist page with response code 404
    Given a chrome browser
    When user loads the doesntexist page
    Then page loads without javascript errors
    Then response code is 404
    And the browser is closed

  Scenario: User is able to navigate to pgp page with response code 200
    Given a chrome browser
    When user loads the pgp page
    Then page loads without javascript errors
    Then response code is 200
    And the browser is closed

  Scenario: All links on home page are working
    Given a chrome browser
    When user loads the home page
    Then page loads without javascript errors
    When user clicks on all links
    Then all links load to a live page
    And the browser is closed

  Scenario: All links on prices page are working
    Given a chrome browser
    When user loads the prices page
    Then page loads without javascript errors
    When user clicks on all links
    Then all links load to a live page
    And the browser is closed

  Scenario: All links on doesntexist page are working
    Given a chrome browser
    When user loads the doesntexist page
    Then page loads without javascript errors
    When user clicks on all links
    Then all links load to a live page
    And the browser is closed

  Scenario: All links on pgp page are working
    Given a chrome browser
    When user loads the pgp page
    Then page loads without javascript errors
    When user clicks on all links
    Then all links load to a live page
    And the browser is closed