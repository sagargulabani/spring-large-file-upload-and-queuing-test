package com.example.demo;

import java.util.List;

public class DataList {
	
	List<Data> data;

	public DataList(List<Data> data) {
		super();
		this.data = data;
	}

	public List<Data> getData() {
		return data;
	}

	public void setData(List<Data> data) {
		this.data = data;
	}
}
