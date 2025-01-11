package com.backlogged.univercity;

/**
 * Represents the timer used for the game.
 *
 * @author @Josh-White14 @nk-t14
 * @version %I%, %G%
 */
public class InGameTimer {

  private String[] months = { "January", "February", "March", "April", "May", "June", "July",
      "August", "September", "October", "November", "December" };
  private int monthIndex = 7;
  private float currentTimeRemaining;
  private int year;
  private int timeInMinutes;
  private String semester;
  private boolean userInvokedPause = true;
  private boolean systemInvokedPause = false;
  private float timeElapsed = 0f;
  private float timeElapsedSinceLastMonth = 0f;
  /**
   * Constructor for the class which assigns the length of the game in seconds
   * to the currentTimeRemaining attribute.
   *
   * @param timeInMinutes the length of the game specified in minutes.
   */
  public InGameTimer(int timeInMinutes) {
    currentTimeRemaining = (float) timeInMinutes * 60.f;
    this.timeInMinutes = timeInMinutes;
  }

  /**
   * An alternative constructor for the class that defaults the
   * timeInMinutes value to 5 if no input is received.
   */
  public InGameTimer() {
    this.timeInMinutes = 5;
    currentTimeRemaining = (float) timeInMinutes * 60.f;
  }

  /**
   * userStartTime changes value of the userInvokedPause flag to false to indicate
   * the beginning of the game.
   */
  public void userStartTime() {
    userInvokedPause = false;
  }

  /**
   * Compliment of the userStartTime() method. In this method the
   * flag indicating the user invoked pausing of the game is set to true.
   */
  public void userStopTime() {
    userInvokedPause = true;
  }

  /**
   * systemStartTime changes value of the systemInvokedPause flag to false to
   * indicate
   * the end of the system invoked pause.
   */
  public void systemStartTime() {
    systemInvokedPause = false;
  }

  /**
   * Compliment of the systemStartTime() method. In this method the
   * flag indicating the system invoked pausing of the game is set to true.
   */
  public void systemStopTime() {
    systemInvokedPause = true;
  }

  /**
   * Returns the current state of the game.
   *
   * @return the boolean flag stating whether the game is user paused or not.
   */
  public boolean isUserStopped() {
    return userInvokedPause;
  }

  /**
   * This method resets the time values at the beginning of the game and
   * assigns the currentTimeRemaining attribute the time (in seconds) left
   * in the game.
   */
  public void resetTime() {
    userStopTime();
    currentTimeRemaining = (float) timeInMinutes * 60.f;
    monthIndex = 8; // Game begins from September
  }

  /**
   * Updates the currentTimeRemaining value every time the render() method
   * is called in the Screen class. If the game is paused, the current value
   * is returned, otherwise it is updated by subtracting the delta value.
   *
   * @param delta the time elapsed since the last render.
   * @return the value in seconds of the time left in the game.
   */
  public float updateTime(float delta) {
    if (userInvokedPause || systemInvokedPause) {
      return currentTimeRemaining;
    }
    currentTimeRemaining = currentTimeRemaining - delta <= 0.f ? 0.f : currentTimeRemaining - delta;

    return currentTimeRemaining;
  }

  /**
   * Resets the elapsed time to 0 each time the month is updated in-game.
   */
  public void resetElapse() {
    timeElapsed = 0;
  }

  /**
   * Updates the timeElapse attribute with delta time since the last
   * render. This method is used to update the value of the month at
   * a set interval.
   *
   * @param delta the time in seconds since the last render.
   * @return the time since the last month update.
   */
  public float getTimeElapsed(float delta) {
      if (userInvokedPause || systemInvokedPause) {
          return timeElapsed;
      }
      timeElapsed = timeElapsed + delta;
      timeElapsedSinceLastMonth = timeElapsedSinceLastMonth + delta;

      if (timeElapsedSinceLastMonth > Constants.ONE_MONTH){
          updateTimerValues();
          timeElapsedSinceLastMonth = 0;
      }
      return timeElapsed;
  }





  /**
   * Resets the value of the year at the beginning of the game.
   */
  public void resetYear() {
    year = 1;
  }

  /**
   * Updates the year in-game each September in correspondence
   * with a standard academic timetable. If the game is paused
   * the current value is returned.
   */
  public Integer updateYear() {
    if (userInvokedPause || systemInvokedPause) {
      return year;
    }
    if (monthIndex == 8) {
      year += 1; // Academic year increase every September
    }

    return year;
  }

  /**
   * Sets the semester values dependant on the month. September - December
   * is Semester 1, January - May is Semester 2 and Summer Holidays June
   * - August. If the game is paused the current value is returned.
   *
   * @return the current semester associated with the in-game time.
   */
  public String updateSemester() {
    if (userInvokedPause || systemInvokedPause) {
      return semester;
    }
    if (monthIndex == 8) {
      semester = "Semester 1";
    } else if (monthIndex > 4 & monthIndex < 8) {
      semester = "Summer Holiday";
    } else if (monthIndex == 0) {
      semester = "Semester 2";
    }

    return semester;
  }

  /**
   * Resets the value of the semester at the start of the game.
   */
  public void resetSemester() {
    semester = "Semester 1";
  }

  /**
   * Updates the index value of the months array when called. If the
   * game is paused, the current month is returned.
   *
   * @return the index of the current month.
   */
  public Integer updateMonth() {
    if (userInvokedPause || systemInvokedPause) {
      return monthIndex;
    }
    if (monthIndex == 11) {
      monthIndex = 0;
    } else {
      monthIndex += 1;
    }

    return monthIndex;
  }

  /**
   * Updates all values associated with the timer display used in the
   * MapScreen class.
   */
  public void updateTimerValues() {
    // one source for all updates when method is called
    updateMonth();
    updateYear();
    updateSemester();
  }

  /**
   * Initialises all timer values at the start of the game.
   */
  public void initialiseTimerValues() {
    resetTime();
    resetElapse();
    resetYear();
    resetSemester();
    userStartTime();
  }

  /**
   * Formats the class attributes to be displayed on the timer.
   *
   * @return the formatted string displaying the semester, month and year in-game.
   */
  public String output() {
    return String.format("%s:%s\n\nYear %d", semester, months[monthIndex], year);
  }

//    @Override
//    public String toString() {
//        return ""
//    }
}
