Feature: How's the weather not in The Netherlands

  Scenario: the weather is better over there
    Given that i'm in a city in The Netherlands
    When I check the current weather
    Then the weather in a southern European country should be better