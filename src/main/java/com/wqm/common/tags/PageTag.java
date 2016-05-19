package com.wqm.common.tags;

import java.util.List;
import java.util.Map;

public class PageTag  {
	
	private Integer total;
	
	private List<Map<String,String>> rows;

	public Integer getTotal() {
		return total;
	}

	public void setTotal(Integer total) {
		this.total = total;
	}

	public List<Map<String, String>> getRows() {
		return rows;
	}

	public void setRows(List<Map<String, String>> rows) {
		this.rows = rows;
	}	 
}
