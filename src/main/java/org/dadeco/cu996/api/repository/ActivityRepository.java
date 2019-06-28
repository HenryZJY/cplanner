package org.dadeco.cu996.api.repository;

import java.util.List;

import org.dadeco.cu996.api.model.Activity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ActivityRepository extends JpaRepository<Activity, Integer> {
	Activity save(Activity productInfo);

	@Query(value = "select max(a.name) as name, max(a.role) as role, case when d.is_working_day = 0 then -1 else sum(a.daily_effort) end as daily_effort, "
			+ " d.week_in_year, d.day_in_week_eng_sn, d.day_id " + " from cplanner.date_dim d "
			+ " left join cplanner.activity a on d.day_id >= str_to_date(a.start,'%Y-%m-%d %H:%i:%s') and d.day_id <= str_to_date(a.end,'%Y-%m-%d %H:%i:%s') and a.userid = ?1 "
			+ " where d.day_id >= str_to_date(?2,'%Y-%m-%d %H:%i:%s') and d.day_id <= str_to_date(?3,'%Y-%m-%d %H:%i:%s') "
			+ " group by d.day_id, d.week_in_year, d.day_in_week_eng_sn order by d.day_id asc", nativeQuery = true)
	public List<List<Object>> findByUserIdAndStartAndEnd(String userId, String startDate, String endDate);

	@Query(value = "select a.name, a.role, a.daily_effort, str_to_date(a.start,'%Y-%m-%d %H:%i:%s') as start_date, str_to_date(a.end,'%Y-%m-%d %H:%i:%s') as end_date, a.userid, case when r.rgb is null then '#333333' else r.rgb end as rgb "
			+ " from cplanner.activity a "
			+ " left join cplanner.activity_role r on a.role = r.name "
			+ " where (str_to_date(a.start,'%Y-%m-%d %H:%i:%s') <= str_to_date(?1,'%Y-%m-%d %H:%i:%s') and str_to_date(a.end,'%Y-%m-%d %H:%i:%s') >= str_to_date(?1,'%Y-%m-%d %H:%i:%s')) "
			+ " or (str_to_date(a.start,'%Y-%m-%d %H:%i:%s') >= str_to_date(?1,'%Y-%m-%d %H:%i:%s') and str_to_date(a.end,'%Y-%m-%d %H:%i:%s') <= str_to_date(?2,'%Y-%m-%d %H:%i:%s')) "
			+ " or (str_to_date(a.start,'%Y-%m-%d %H:%i:%s') <= str_to_date(?2,'%Y-%m-%d %H:%i:%s') and str_to_date(a.end,'%Y-%m-%d %H:%i:%s') >= str_to_date(?2,'%Y-%m-%d %H:%i:%s')) "
			+ " order by a.userid, str_to_date(a.start,'%Y-%m-%d %H:%i:%s') asc", nativeQuery = true)
	public List<List<Object>> findByStartAndEndForTeam(String startDate, String endDate);
}
