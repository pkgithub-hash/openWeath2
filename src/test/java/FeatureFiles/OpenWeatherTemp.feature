Feature: Validating Place API's

  @verifyTemp
  Scenario Outline: A happy holidaymaker
    Given I like to holiday in Sydney with "<Lat>"  "<Lon>" only on Thursday
    When I look up weather forecast
    Then I make holiday plan if Temperature is warmer than 10 degree
    Examples:
      | Location | Lat   | Lon    |
      | Sydney   | 33.86 | 151.20 |
