package com.vvicey.itemBank.service;

import com.vvicey.itemBank.entity.SingleChoice;

import java.util.List;

/**
 * @Author nana
 * @Date 18-7-9 上午10:43
 * @Description 单选题增删改查抽象类
 */
public interface SingleChoiceService {
    List<SingleChoice> querySingleChoiceByEiid(Integer examEiid);
    
    List<SingleChoice> querySingleChoiceAllBySubjectId(Integer subjectId);

    List<SingleChoice> querySingleChoiceAll();

    void deleteSingleChoiceById(int id);
}
