package com.vvicey.testPaper.service;

import com.vvicey.testPaper.entity.SingleChoice;

import java.util.List;

/**
 * @Author nana
 * @Date 18-7-9 上午10:43
 * @Description 单选题增删改查抽象类
 */
public interface SingleChoiceService {
    List<SingleChoice> querySingleChoiceBySubjectId(Integer subjectId, Integer singleNumber);
}
