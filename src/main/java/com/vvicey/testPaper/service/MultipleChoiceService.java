package com.vvicey.testPaper.service;

import com.vvicey.testPaper.entity.MultipleChoice;

import java.util.List;

/**
 * @Author nana
 * @Date 18-7-9 上午10:56
 * @Description 多选题抽象类
 */
public interface MultipleChoiceService {
    List<MultipleChoice> queryMultipleChoiceByEiid(Integer eiid);

    List<MultipleChoice> queryMultipleChoiceAll(Integer subjectId);
}
