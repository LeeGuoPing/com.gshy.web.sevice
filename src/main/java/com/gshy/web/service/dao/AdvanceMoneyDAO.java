package com.gshy.web.service.dao;

import java.util.List;
import java.util.Objects;

import com.darengong.tools.dao.v2.DBQuery;
import com.gshy.web.service.dao.base.BaseDAO;
import com.gshy.web.service.entity.AdvanceMoney;
import com.gshy.web.service.query.AdvanceMoneyQuery;

public class AdvanceMoneyDAO extends BaseDAO<AdvanceMoney>{
	
	private static final AdvanceMoneyDAO advanceMoneyDAO = new AdvanceMoneyDAO();
	
	public static AdvanceMoneyDAO getInstance(){
		return advanceMoneyDAO;
	}
	
	private AdvanceMoneyDAO() {
		super(AdvanceMoney.class);
	}
	
	public int getCount(AdvanceMoneyQuery query) throws Exception{
		DBQuery dbQuery = toDBQuery(query,false);
		return daoHelper.count(dbQuery);
	}
	
	public List<AdvanceMoney> getByQuery(AdvanceMoneyQuery query) throws Exception{
		DBQuery dbQuery = toDBQuery(query,true);
		return daoHelper.selectByQuery(clazz, dbQuery);
	} 

	private DBQuery toDBQuery(AdvanceMoneyQuery query,boolean usePage) throws Exception {
		long auditEmp = query.getAuditEmp();
		long createEmp = query.getCreateEmp();
		
		DBQuery dbQuery = getUnitDBQuery(query,usePage);
		dbQuery.where();
		queryWithList(dbQuery, query.getAdvanceIds(), "id");
		queryWithList(dbQuery, query.getAuditStates(), "audit_state");
		if(auditEmp>0){
			dbQuery.and().column("audit_emp").equal(auditEmp);
		}
		if(createEmp>0){
			dbQuery.and().column("create_emp").equal(createEmp);
		}
		if (!Objects.isNull(query.getOrderBy())) {
			dbQuery.orderBy(query.getOrderBy());
		}

		if (usePage && query.getPage() >= 1 && query.getPageSize() >= 1) {
			int start = (query.getPage() - 1) * query.getPageSize();
			int size = query.getPageSize();
			dbQuery.limit(start, size);
		}
		return dbQuery;
	}
	
	private DBQuery getUnitDBQuery(AdvanceMoneyQuery query, boolean usePage) throws Exception {
		DBQuery dbQuery = new DBQuery();
		if (usePage) {
			dbQuery.distinct("id");
		} else {
			dbQuery.select("count(distinct id)");
		}

		dbQuery.from("tbl_advance_money");

		return dbQuery;
	}

}
