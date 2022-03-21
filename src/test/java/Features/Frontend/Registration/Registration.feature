Feature:

  @registration
  Scenario: Registration
    Given I access frontend registration page https://dev.brasilbet.com/register
    When I fill out the fields
      | username   | password     | fullname   | email           | contactNo   |
      | junrillon2 | 123123       | jun rillon2 | rrr2@gmail.com | 09976201413 |
