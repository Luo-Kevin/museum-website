package ca.mcgill.ecse321.museum.service;

import ca.mcgill.ecse321.museum.dao.ScheduleOfTimePeriodRepository;
import ca.mcgill.ecse321.museum.dao.TimePeriodRepository;
import ca.mcgill.ecse321.museum.model.Schedule;
import ca.mcgill.ecse321.museum.model.ScheduleOfTimePeriod;
import ca.mcgill.ecse321.museum.model.TimePeriod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

/**
 * Business logic for timePeriodController
 *
 * @author Victor
 */

@Service
public class TimePeriodService {
  @Autowired
  private TimePeriodRepository timePeriodRepository;
  @Autowired
  private ScheduleOfTimePeriodRepository scheduleOfTimePeriodRepository;

  // GET

  /**
   * Method to get a time period from database
   *
   * @param timePeriodId - id of time period
   * @return
   * @author VZ
   */

  @Transactional
  public TimePeriod getTimePeriod(long timePeriodId) {
    return timePeriodRepository.findTimePeriodByTimePeriodId(timePeriodId);
  }

  // CREATE

  /**
   * Create a TimePeriod and save to database
   *
   * @param startDate
   * @param endDate
   * @return
   * @author VZ
   */

  @Transactional
  public TimePeriod createTimePeriod(Timestamp startDate, Timestamp endDate) {

    // input validation
    if (startDate == null || endDate == null) {
      throw new IllegalArgumentException("Start date and end date cannot be null");
    }
    if (startDate.after(endDate)) {
      throw new IllegalArgumentException("Start date cannot be after end date");
    }
    // create TimePeriod
    TimePeriod timePeriod = new TimePeriod();

    timePeriod.setStartDate(startDate);
    timePeriod.setEndDate(endDate);
    timePeriodRepository.save(timePeriod);
    return timePeriod;
  }

  // DELETE

  /**
   * Delete a TimePeriod from database by ID
   *
   * @param timePeriodId
   * @author VZ
   */

  @Transactional
  public void deleteTimePeriod(long timePeriodId) {

    timePeriodRepository.deleteById(timePeriodId);
  }

  // EDIT

  /**
   * Edit a TimePeriod by ID and save to database
   *
   * @param timePeriodId
   * @param startDate
   * @param endDate
   * @return
   * @author VZ
   */

  @Transactional
  public TimePeriod editTimePeriod(long timePeriodId, Timestamp startDate, Timestamp endDate) {
    // input validation
    if (startDate == null || endDate == null) {
      throw new IllegalArgumentException("Start date and end date cannot be null");
    }
    if (startDate.after(endDate)) {
      throw new IllegalArgumentException("Start date cannot be after end date");
    }
    // find TimePeriod
    TimePeriod timePeriod = timePeriodRepository.findTimePeriodByTimePeriodId(timePeriodId);
    if (timePeriod == null) {
      throw new IllegalArgumentException("TimePeriod does not exist");
    }
    // edit TimePeriod
    timePeriod.setStartDate(startDate);
    timePeriod.setEndDate(endDate);
    timePeriodRepository.save(timePeriod);
    return timePeriod;
  }

  /**
   * Helper method to get the time periods of a schedule
   *
   * @param schedule
   * @return
   * @author VZ
   */

  public List<TimePeriod> getTimePeriodsOfSchedule(Schedule schedule) {

    List<ScheduleOfTimePeriod> scheduleOfTimePeriods = scheduleOfTimePeriodRepository
        .findScheduleOfTimePeriodBySchedule(schedule);

    List<TimePeriod> timePeriods = new ArrayList<TimePeriod>();
    for (ScheduleOfTimePeriod scheduleOfTimePeriod : scheduleOfTimePeriods) {
      timePeriods.add(scheduleOfTimePeriod.getTimePeriod());
    }

    return timePeriods;
  }
}
