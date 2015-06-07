package com.cms.dao;

import java.util.Date;
import java.util.List;

import com.cms.entity.Student;
import com.cms.entity.StudentBuffer;

public interface StudentDao extends BaseDao<Student>{
    public List<StudentBuffer> getLatestUpdate(Date date);
    
}
