package com.vvicey.user.teacher.service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.vvicey.common.utils.ExamFileInputUtil;
import com.vvicey.examination.entity.ExaminationInternal;
import com.vvicey.testPaper.dao.CheckingQuestionMapper;
import com.vvicey.testPaper.dao.MultipleChoiceMapper;
import com.vvicey.testPaper.dao.SingleChoiceMapper;
import com.vvicey.testPaper.entity.CheckingQuestion;
import com.vvicey.testPaper.entity.MultipleChoice;
import com.vvicey.testPaper.entity.SingleChoice;
import com.vvicey.user.teacher.dao.TeacherMapper;
import com.vvicey.user.teacher.entity.Teacher;
import com.vvicey.user.tempEntity.TeacherLoginer;

import net.sf.json.JSONObject;

/**
 * @Author nana
 * @Date 18-6-26 下午9:50
 * @Description 老师可执行的操作实现类
 */
@Service("TeacherServiceImpl")
public class TeacherServiceImpl implements TeacherService {

	@Autowired
	private TeacherMapper teacherMapper;
	@Autowired
	private CheckingQuestionMapper checkMapper;
	@Autowired
	private MultipleChoiceMapper multipleMapper;
	@Autowired
	private SingleChoiceMapper singleMapper;

	/**
	 * 创建教师个人信息(包含创建教师身份)
	 *
	 * @param teacher
	 *            需要创建的教师信息
	 */
	@Override
	@Transactional
	public void createTeacherInfo(Teacher teacher) {
		teacherMapper.insertTeacherRole(teacher.getUid());
		teacherMapper.insertSelective(teacher);
	}

	/**
	 * 删除教师个人信息
	 *
	 * @param uid
	 *            根据教师uid进行删除
	 * @return 返回删除成功与否
	 */
	@Override
	@Transactional
	public int deleteTeacher(int uid) {
		return teacherMapper.deleteByUid(uid);
	}

	/**
	 * 查询所有教师
	 *
	 * @return 返回查询的教师数据
	 */
	@Override
	public List<TeacherLoginer> queryAllTeacher() {
		return teacherMapper.selectAllTeacher();
	}

	/**
	 * 查询登陆教师自己
	 *
	 * @param uid
	 *            传入uid
	 * @return 返回教师数据
	 */
	@Override
	public TeacherLoginer queryTeacherSelf(int uid) {
		return teacherMapper.selectTeacherSelf(uid);
	}

	/**
	 * 更新教师个人信息(根据编号)
	 *
	 * @param teacher
	 *            传入需要更新的教师信息
	 * @return 返回更新成功与否
	 */
	@Override
	@Transactional
	public int updateTeacherInfoByTeacherNumber(Teacher teacher) {
		return teacherMapper.updateByTeacherNumberSelective(teacher);
	}

	/**
	 * 更新教师个人信息(根据账号id)
	 *
	 * @param teacher
	 *            教师数据
	 */
	@Override
	@Transactional
	public void updateTeacherInfoByUid(Teacher teacher) {
		teacherMapper.updateByUidSelective(teacher);
	}

	/**
	 * 导入题目，必须保证导入的文本文件中，题目数量和创建的考试中的题目数量一致， 并且题目难度必须和创建的考试中的题目难度一致
	 * 
	 * @param filePath
	 *            文本文件存储路径
	 * @param exam
	 *            当前考试信息
	 * @return list集合存放导入失败信息
	 * @throws IOException
	 */
	@SuppressWarnings("finally")
	@Override
	public List<String> eaxmInput(String filePath, ExaminationInternal exam) {
		List<String> list = new ArrayList<String>();
		// 判断文件中题目是否符合要求
		boolean checkTest = ExamFileInputUtil.checkTest(filePath, exam.getSingleNumber(), exam.getMultipleNumber(),
				exam.getCheckingNumber(), exam.getPaperLevel());
		if (checkTest == false) {
			list.add("导入题目不符合要求");
			File file = new File(filePath);
			file.delete();// 删除磁盘中的文件
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

			String content = null;
			while ((content = br.readLine()) != null) {// 使用readLine方法，一次读一行
				System.out.println(content);
				JSONObject obj = new JSONObject().fromObject(content);// 将json字符串转换为json对象
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
		}finally {
			try {
				br.close();
				if(file.exists()){
					file.delete();// 删除磁盘中的文件
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return list;
			
		}
		
	}

	/**
	 * 根据学院查询老师
	 * @param institute
	 * @return
	 */
	@Override
	public List<TeacherLoginer> queryByInstitute(Integer institute) {
		return teacherMapper.selectByInstitute(institute);
	}
}
