package com.banana;


import com.banana.info.entity.Member;
import com.banana.info.entity.MemberCard;
import com.banana.info.mapper.*;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.List;

@SpringBootTest
class BCMSServerApplicationTests {

    @Resource
    private MemberMapper memberMapper;

    @Resource
    private CardMapper cardMapper;

    @Resource
    private CourseMapper courseMapper;

    @Resource
    private TrainerMapper trainerMapper;

    @Resource
    private EmployeeMapper employeeMapper;

    @Resource
    private EquipmentMapper equipmentMapper;

    @Resource
    private MemberCardMapper memberCardMapper;

    @Resource
    private MemberCourseMapper memberCourseMapper;

    @Resource
    private MemberTrainerMapper memberTrainerMapper;

    @Test
    void testMapper() {
//        Member member = new Member();
//        member.setName("王五");
//        member.setPassword("123456");
//        member.setGender("男");
//        member.setEmail("123.@qq.com");
//        member.setPhone("13022124431");
//        member.setAge(21);
//        member.setJoinDate(LocalDateTool.toLocalDate("2023-12-15"));
//
//        memberMapper.insert(member);


//        Card card = new Card();
//        card.setType("月");
//        card.setFee(165);
//        card.setDescription("月卡");
//
//        cardMapper.insert(card);


//        Trainer trainer = new Trainer();
//        trainer.setName("马斯克");
//        trainer.setPassword("123456");
//        trainer.setGender("男");
//        trainer.setAge(35);
//        trainer.setEmail("123.@qq.com");
//        trainer.setPhone("13022124431");
//        trainer.setHireDate(LocalDateTool.toLocalDate("2022-12-15"));
//        trainer.setSalary(8000);
//
//        trainerMapper.insert(trainer);


//        Course course = new Course();
//        course.setName("LES MILLS SPRINT");
//        course.setTrainers("马云、马斯克");
//        course.setDuration(30);
//        course.setFee(240);
//        course.setCreateDate(LocalDateTool.toLocalDate("2022-01-15"));
//        course.setDescription("短时间达到出色效果 精练肌肉，燃烧脂肪 训练后仍旧持续燃烧卡路里 挑战身体和意志的极限 快速集中，一切都在30分钟内完成");

//        courseMapper.insert(course);


//        Employee employee = new Employee();
//        employee.setName("杨幂");
//        employee.setPassword("123456");
//        employee.setGender("女");
//        employee.setAge(37);
//        employee.setEmail("123.@qq.com");
//        employee.setPhone("13022124431");
//        employee.setPosition("保洁");
//        employee.setSalary(4000);
//        employee.setHireDate(LocalDateTool.toLocalDate("2021-12-24"));
//
//        employeeMapper.insert(employee);


//        for (int i=0;i<6;++i) {
//            Equipment equipment = new Equipment();
//            equipment.setName("哑铃");
//            equipment.setFee(600);
//            equipment.setDescription("无氧训练");
//            equipment.setPurchaseDate(LocalDateTool.toLocalDate("2022-06-21"));
//            equipment.setWarrantyEndDate(LocalDateTool.toLocalDate("2024-06-21"));
//
//            equipmentMapper.insert(equipment);
//        }


//        MemberCard memberCard = new MemberCard();
//        memberCard.setMemberId(6);
//        memberCard.setCardId(2);
//        memberCard.setActualPay(165);
//        memberCard.setState(State.NORMAL.getIndex());
//        memberCard.setStartDate(LocalDateTool.toLocalDate("2023-09-21"));
//        memberCard.setEndDate(TimeCalculator.endDate(memberCard.getStartDate(),memberCard.getCardId()));
//        memberCard.setRegisterDate(LocalDateTool.toLocalDate("2023-06-21"));
//
        MemberCard memberCard = memberCardMapper.selectById(520321002);
        System.out.println(memberCard);


//        MemberCourse memberCourse = new MemberCourse();
//        memberCourse.setMemberId(3);
//        memberCourse.setCourseId(1);
//        memberCourse.setActualPay(3200);
//        memberCourse.setState(State.NORMAL.getIndex());
//        memberCourse.setRegisterDate(LocalDateTool.toLocalDate("2023-08-19"));
//
//        memberCourseMapper.insert(memberCourse);


//        MemberTrainer memberTrainer = new MemberTrainer();
//        memberTrainer.setMemberId(2);
//        memberTrainer.setTrainerId(2);
//        memberTrainer.setFee(300);
//        memberTrainer.setActualPay(280);
//        memberTrainer.setState(State.NORMAL.getIndex());
//        memberTrainer.setStartDate(LocalDateTool.toLocalDate("2023-07-21"));
//        memberTrainer.setEndDate(LocalDateTool.toLocalDate("2023-08-21"));
//        memberTrainer.setRegisterDate(LocalDateTool.toLocalDate("2023-06-19"));
//
//        memberTrainerMapper.insert(memberTrainer);

        // List<Member> members = memberMapper.selectList(null);
        // members.forEach(System.out::println);
    }

}
