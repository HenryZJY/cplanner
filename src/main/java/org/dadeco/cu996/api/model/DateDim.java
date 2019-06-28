package org.dadeco.cu996.api.model;

import java.sql.Date;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "DATE_DIM")
@Setter
@Getter
@NoArgsConstructor
@RequiredArgsConstructor
public class DateDim {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@NonNull
	@Column(name = "YEAR_ID")
	private Integer yearId;

	@NonNull
	@Column(name = "YEAR_START_TIME")
	private Timestamp yearStartTime;

	@NonNull
	@Column(name = "YEAR_END_TIME")
	private Timestamp yearEndTime;

	@NonNull
	@Column(name = "YEAR_DAY_NUM")
	private Integer yearDayNum;

	@NonNull
	@Column(name = "QUARTER_ID")
	private Integer quarterId;

	@NonNull
	@Column(name = "QUARTER_CHS_NAME")
	private String quarterChsName;

	@NonNull
	@Column(name = "QUARTER_START_TIME")
	private Timestamp quarterStartTime;

	@NonNull
	@Column(name = "QUARTER_END_TIME")
	private Timestamp quarterEndTime;

	@NonNull
	@Column(name = "QUARTER_DAY_NUM")
	private Integer quarterDayNum;

	@NonNull
	@Column(name = "LAST_QUARTER_OF_YEAR")
	private Integer lastQuarterOfYear;

	@NonNull
	@Column(name = "MONTH_ID")
	private Integer monthId;

	@NonNull
	@Column(name = "MONTH_ENG_NAME")
	private String monthEngName;

	@NonNull
	@Column(name = "MONTH_ENG_SN")
	private String monthEngSn;

	@NonNull
	@Column(name = "MONTH_START_TIME")
	private Timestamp monthStartTime;

	@NonNull
	@Column(name = "MONTH_END_TIME")
	private Timestamp monthEndTime;

	@NonNull
	@Column(name = "MONTH_DAY_NUM")
	private Integer monthDayNum;

	@NonNull
	@Column(name = "YEARMONTH")
	private Integer yearMonth;

	@NonNull
	@Column(name = "LAST_MONTH_OF_YEAR")
	private Integer lastMonthOfYear;

	@NonNull
	@Column(name = "LAST_MONTH_OF_QUARTER")
	private Integer lastMonthOfQuarter;

	@NonNull
	@Column(name = "WEEK_IN_YEAR")
	private Integer weekInYear;

	@NonNull
	@Column(name = "WEEK_IN_MONTH")
	private Integer weekInMonth;

	@NonNull
	@Column(name = "WEEK_START_TIME")
	private Timestamp weekStartTime;

	@NonNull
	@Column(name = "WEEK_END_TIME")
	private Timestamp weekEndTime;

	@NonNull
	@Column(name = "WEEK_DAY_NUM")
	private Integer weekDayNum;

	@NonNull
	@Column(name = "DAY_ID", unique = true)
	private Date dayId;

	@NonNull
	@Column(name = "DAY_START_TIME")
	private Timestamp dayStartTime;

	@NonNull
	@Column(name = "DAY_END_TIME")
	private Timestamp dayEndTime;

	@NonNull
	@Column(name = "DAY_IN_YEAR")
	private Integer dayInYear;

	@NonNull
	@Column(name = "DAY_IN_WEEK_ID")
	private Integer dayInWeekId;

	@NonNull
	@Column(name = "DAY_IN_WEEK_CHS_NAME")
	private String dayInWeekChsName;

	@NonNull
	@Column(name = "DAY_IN_WEEK_ENG")
	private String dayInWeekEng;

	@NonNull
	@Column(name = "DAY_IN_WEEK_ENG_SN")
	private String dayInWeekEngSn;

	@NonNull
	@Column(name = "LAST_DAY_OF_YEAR")
	private Integer lastDayOfYear;

	@NonNull
	@Column(name = "LAST_DAY_OF_QUARTER")
	private Integer lastDayOfQuarter;

	@NonNull
	@Column(name = "LAST_DAY_OF_MONTH")
	private Integer lastDayOfMonth;

	@NonNull
	@Column(name = "IS_WORKING_DAY")
	private Integer isWorkingDay;

	@NonNull
	@Column(name = "LAST_UPDATE_USER")
	private String lastUpdateUser;

	@NonNull
	@Column(name = "LAST_UPDATE_TIME")
	private Timestamp lastUpdateTime;
}
