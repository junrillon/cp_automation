
Feature: Upload Game Image

  Background:
    Given I access b2c backoffice https://admin.brasilplay.com
    When I login on b2c backoffice
      | username | password  |
      | shey.admin | Sheyadmin123 |
    Then I should access the dashboard

  Scenario: Upload Game Image
    Given I go to gamelist page
    When I upload the image per game
      | provider | fileType | location                           | csvLocation        |
      | EVOLUTION    | .png     | D:\CP Files\Casino Provider\Evolution\evolution\evolution\live\ | D:\CP Files\Casino Provider\Evolution\Evolution.csv |
#    Then all active games should have an image.

        #adminjun, P@ssw0rd shey.admin, Sheyadmin123 #testing upload the image per game

