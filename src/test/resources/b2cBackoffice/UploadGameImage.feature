
Feature: Upload Game Image

  Background:
    Given I access b2c backoffice https://dev-admin.clickplaysports.net
    When I login on b2c backoffice
      | username | password  |
      | adminjun | P@ssw0rd |
    Then I should access the dashboard

  Scenario: Upload Game Image
    Given I go to gamelist page
    When I upload the image per game
      | provider | fileType | location                           | csvLocation        |
      | MARIO    | .png     | D:\CP Files\Casino Provider\Mario\ | D:\MarioImages.csv |
#    Then all active games should have an image.

        #adminjun, P@ssw0rd shey.admin, Sheyadmin123 #testing upload the image per game

