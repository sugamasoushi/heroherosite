package com.heroherosite.Controller.Service;

import java.io.Serializable;
import java.util.List;

public interface DataCenter <T> extends Serializable{
	public List<T> getAll();
}
