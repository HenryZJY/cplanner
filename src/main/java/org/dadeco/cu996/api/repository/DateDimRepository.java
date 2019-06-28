package org.dadeco.cu996.api.repository;

import java.util.List;

import org.dadeco.cu996.api.model.Activity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface DateDimRepository extends JpaRepository<Activity, Integer> {
	@Query(value = "   select d.day_in_week_eng_sn "
			     + "     from cplanner.date_dim d "
			     + " group by d.day_in_week_eng_sn,"
			     + "          d.day_in_week_id "
			     + " order by d.day_in_week_id", nativeQuery = true)
	public List<String> selectDayInWeekEngSn();

	@Query(value = "    select d.week_start_time, "
				 + "           d.week_end_time, "
				 + "           d.week_in_year "
				 + "      from cplanner.date_dim d "
				 + "     where d.yearmonth >= ?1 "
				 + "       and d.yearmonth <= ?2 "
				 + "  group by d.week_start_time, "
				 + "           d.week_end_time, "
				 + "           d.week_in_year "
				 + "  order by d.week_start_time", nativeQuery = true)
	public List<List<Object>> selectYearWeeks(int startYearMonth, int endYearMonth);

	@Query(value = "   select d.month_eng_sn, "
				 + "          d.month_start_time, "
				 + "          d.month_end_time "
				 + "     from cplanner.date_dim d "
				 + "    where d.yearmonth >= ?1 "
				 + "      and d.yearmonth <= ?2 "
				 + " group by d.month_eng_sn, "
				 + "          d.month_start_time, "
				 + "          d.month_end_time "
				 + " order by d.month_start_time", nativeQuery = true)
	public List<List<Object>> selectYearMonths(int startYearMonth, int endYearMonth);

	@Query(value = "select min(d.week_start_time) as week_start_time"
			     + "  from cplanner.date_dim d "
			     + " where d.yearmonth = ?1 ", nativeQuery = true)
	public Object selectWeekStartDate(String yearMonth);

	@Query(value = "select distinct d.week_end_time"
			     + "  from cplanner.date_dim d "
			     + " where d.week_start_time <= str_to_date((select max(d.week_start_time) as week_start_time "
			     + "                                           from cplanner.date_dim d "
			     + "                                          where d.yearmonth = ?1),'%Y-%m-%d %H:%i:%s') "
			     + "   and d.week_end_time >= str_to_date((select max(d.week_start_time) as week_start_time "
			     + "                                         from cplanner.date_dim d "
			     + "                                        where d.yearmonth = ?1),'%Y-%m-%d %H:%i:%s')", nativeQuery = true)
	public Object selectWeekEndDate(String yearMonth);
}
