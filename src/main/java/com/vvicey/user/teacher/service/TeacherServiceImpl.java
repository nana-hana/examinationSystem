package com.vvicey.user.teacher.service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.vvicey.common.utils.ExamFileInputUtil;
import com.vvicey.examination.entity.ExaminationInternal;
import com.vvicey.itembank.dao.CheckingQuestionMapper;
import com.vvicey.itembank.dao.MultipleChoiceMapper;
import com.vvicey.itembank.dao.SingleChoiceMapper;
import com.vvicey.itembank.entity.CheckingQuestion;
import com.vvicey.itembank.entity.MultipleChoice;
import com.vvicey.itembank.entity.SingleChoice;
import com.vvicey.user.teacher.dao.TeacherMapper;
import com.vvicey.user.teacher.entity.Teacher;
import com.vvicey.user.tempentity.TeacherLoginer;

import net.sf.json.JSONObject;

/**
 * @Author nana
 * @Date 18-6-26 下午9:50
 * @Description 老师可执行的操作实现类
 */
@Service("TeacherServiceImpl")
public class TeacherServiceImpl implements TeacherService {

    private final TeacherMapper teacherMapper;
    private final CheckingQuestionMapper checkMapper;
    private final MultipleChoiceMapper multipleMapper;
    private final SingleChoiceMapper singleMapper;

    @Autowired
    public TeacherServiceImpl(TeacherMapper teacherMapper, CheckingQuestionMapper checkMapper, MultipleChoiceMapper multipleMapper, SingleChoiceMapper singleMapper) {
        this.teacherMapper = teacherMapper;
        this.checkMapper = checkMapper;
        this.multipleMapper = multipleMapper;
        this.singleMapper = singleMapper;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void createTeacherInfo(Teacher teacher) {
        teacherMapper.insertTeacherRole(teacher.getUid());
        teacherMapper.insertSelective(teacher);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int deleteTeacher(int uid) {
        return teacherMapper.deleteByUid(uid);
    }

    @Override
    public List<TeacherLoginer> queryAllTeacher() {
        return teacherMapper.selectAllTeacher();
    }

    @Override
    public TeacherLoginer queryTeacherSelf(int uid) {
        return teacherMapper.selectTeacherSelf(uid);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int updateTeacherInfoByTeacherNumber(Teacher teacher) {
        return teacherMapper.updateByTeacherNumberSelective(teacher);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateTeacherInfoByUid(Teacher teacher) {
        teacherMapper.updateByUidSelective(teacher);
    }

    @Override
    public List<String> examInput(String filePath, ExaminationInternal exam) {
        List<String> list = new ArrayList<>();
        // 判断文件中题目是否符合要求
        boolean checkTest = ExamFileInputUtil.checkTest(filePath, exam.getSingleNumber(), exam.getMultipleNumber(),
                exam.getCheckingNumber(), exam.getPaperLevel());
        if (!checkTest) {
            list.add("导入题目不符合要求");
            File file = new File(filePath);
            // 删除磁盘中的文件
            file.delete();
            return list;
        }

        // 文件中的题目符合要求，插入数据库
        int insertSelective = 0;
        int errorNum = 0;
        File file = new File(filePath);
        //StringBuilder result = new StringBuilder();
        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader(file));

            String content;
            // 使用readLine方法，一次读一行
            while ((content = br.readLine()) != null) {
                // 将json字符串转换为json对象
                JSONObject obj = JSONObject.fromObject(content);
                String kind = obj.getString("kind");
                // 通过题目种类判断该插入哪种题目
                if ("single_choice".equals(kind)) {
                    SingleChoice bean = (SingleChoice) JSONObject.toBean(obj, SingleChoice.class);
                    bean.setExamEiid(exam.getEiid());
                    insertSelective = singleMapper.insertSelective(bean);
                } else if ("multiple_choice".equals(kind)) {
                    MultipleChoice bean = (MultipleChoice) JSONObject.toBean(obj, MultipleChoice.class);
                    bean.setExamEiid(exam.getEiid());
                    insertSelective = multipleMapper.insertSelective(bean);
                } else if ("checking_question".equals(kind)) {
                    CheckingQuestion bean = (CheckingQuestion) JSONObject.toBean(obj, CheckingQuestion.class);
                    bean.setExamEiid(exam.getEiid());
                    insertSelective = checkMapper.insertSelective(bean);
                }
                if (insertSelective <= 0) {
                    errorNum++;
                }
            }
            if (errorNum != 0) {
                list.add("添加" + errorNum + "题失败");
            }
        } catch (Exception e) {
            list.add("导入失败");
            e.printStackTrace();
        } finally {
            try {
                br.close();
                if (file.exists()) {
                    // 删除磁盘中的文件
                    file.delete();
                }
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        return list;
    }

    @Override
    public List<TeacherLoginer> queryByInstitute(Integer institute) {
        return teacherMapper.selectByInstitute(institute);
    }
}
