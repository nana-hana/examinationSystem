package com.vvicey.itemBank.service;

import com.vvicey.itemBank.entity.MultipleChoice;

import java.util.List;

/**
 * @Author nana
 * @Date 18-7-9 上午10:56
 * @Description 多选题抽象类
 */
public interface MultipleChoiceService {
    List<MultipleChoice> queryMultipleChoiceByEiid(Integer eiid);

    List<MultipleChoice> queryMultipleChoiceAllBySubjectId(Integer subjectId);

    List<MultipleChoice> queryMultipleChoiceAll();

    void deleteMultipleChoiceById(int id);
}
