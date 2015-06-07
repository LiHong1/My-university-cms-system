package com.cms.daoImpl;

import java.util.Date;
import java.util.List;

import com.cms.dao.StudentDao;
import com.cms.entity.Student;
import com.cms.entity.StudentBuffer;

public class StudentDaoImpl extends BaseDaoImpl<Student> implements StudentDao{

    public List<StudentBuffer> getLatestUpdate(Date date) {
     List<StudentBuffer> list=this.findAllBySql("select id,name,password from student_buffer  where date > ? ", StudentBuffer.class, date);
        return list;
    }

}
