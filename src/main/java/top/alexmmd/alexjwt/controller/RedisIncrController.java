package top.alexmmd.alexjwt.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;
import top.alexmmd.alexjwt.annotation.AutoIdempotent;
import top.alexmmd.alexjwt.dao.ApplicationUserDao;
import top.alexmmd.alexjwt.dao.RoleDao;
import top.alexmmd.alexjwt.model.*;
import top.alexmmd.alexjwt.service.RedisIncrService;

import java.text.NumberFormat;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author 汪永晖
 */
@RestController
@RequestMapping("/redis")
@Slf4j
public class RedisIncrController {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    private RedisIncrService redisIncrService;

    @Autowired
    private ApplicationUserDao applicationUserDao;

    @Autowired
    private RoleDao roleDao;

    /**
     * 投票接口
     *
     * @param votePackage
     * @return
     */
    @PostMapping("/vote")
    public ResultUtils incr(@RequestBody VotePackage votePackage) {
        // 投票
        List<ApplicationUser> applicationUserList = votePackage.getApplicationUserList();
        for (ApplicationUser user : applicationUserList) {
            redisIncrService.incr(user.getStaffCode());
        }

        return new ResultUtils(101, "成功投票");
    }

    /**
     * 查询分区成员
     *
     * @param partition
     * @return
     */
    @GetMapping("/queryCurrentPartition/{partition}")
    public ResultUtils queryCurrentPartition(@PathVariable Integer partition) {
        List<ApplicationUser> applicationUserList = applicationUserDao.queryAll(ApplicationUser.builder()
                .voteArea(partition)
                .status(1)
                .isManager(0)
                .build());
        Collections.shuffle(applicationUserList);
        return new ResultUtils(100, "成功查询当前分区成员", applicationUserList);
    }

    /**
     * 查询管理层成员
     *
     * @return
     */
    @GetMapping("/queryManagePartition")
    public ResultUtils queryManagePartition() {
        List<ApplicationUser> applicationUserList = applicationUserDao.queryAll(ApplicationUser.builder()
                .isManager(1)
                .status(1)
                .build());
        Collections.shuffle(applicationUserList);
        return new ResultUtils(100, "成功查询管理层成员", applicationUserList);
    }

    /**
     * 查询所有投票结果
     *
     * @return
     */
    @GetMapping("/queryAllResult")
    public ResultUtils queryAllResult() {
        List<ApplicationUser> applicationUserList = applicationUserDao.queryAll(ApplicationUser.builder()
                .status(1)
                .isPitch(1)
                .build());
        return new ResultUtils(100, "成功查询所有投票结果", applicationUserList);
    }

    /**
     * 查询各个分区的投票结果
     *
     * @return
     */
    @GetMapping("/statisticalResults")
    public ResultUtils statisticalResults() {
        applicationUserDao.updateCache();
        String pre = "vote";
        // 计算每个人的得票数和投票率
        List<ApplicationUser> applicationUserList = applicationUserDao.queryAll(ApplicationUser.builder().status(1).build());
        for (ApplicationUser applicationUser : applicationUserList) {
            String staffCode = applicationUser.getStaffCode();
            String key = pre + staffCode;
            String voteString = stringRedisTemplate.opsForValue().get(key);
            if (voteString != null) {
                Integer vote = Integer.valueOf(voteString);
                log.info("{} 拥有选票 {}", staffCode, vote);
                applicationUser.setVoteNumber(vote);
                // 计算投票率
                String percentage = this.calculation(applicationUser);
                applicationUser.setPercentage(percentage);
                applicationUserDao.update(applicationUser);
            }
        }

        // 拆分成5个区
        List<ApplicationUser> managerList = applicationUserList.stream()
                .filter(applicationUser -> {
                    if (applicationUser.getIsManager().equals(1)) {
                        return true;
                    }
                    return false;
                })
                .collect(Collectors.toList());
        this.calAndSort(managerList);

        List<ApplicationUser> firstList = applicationUserList.stream()
                .filter(applicationUser -> {
                    if (applicationUser.getIsManager().equals(0) && applicationUser.getVoteArea().equals(1)) {
                        return true;
                    }
                    return false;
                })
                .collect(Collectors.toList());
        this.calAndSort(firstList);

        List<ApplicationUser> secondList = applicationUserList.stream()
                .filter(applicationUser -> {
                    if (applicationUser.getIsManager().equals(0) && applicationUser.getVoteArea().equals(2)) {
                        return true;
                    }
                    return false;
                })
                .collect(Collectors.toList());
        this.calAndSort(secondList);

        List<ApplicationUser> thirdList = applicationUserList.stream()
                .filter(applicationUser -> {
                    if (applicationUser.getIsManager().equals(0) && applicationUser.getVoteArea().equals(3)) {
                        return true;
                    }
                    return false;
                })
                .collect(Collectors.toList());
        this.calAndSort(thirdList);

        List<ApplicationUser> fourthList = applicationUserList.stream()
                .filter(applicationUser -> {
                    if (applicationUser.getIsManager().equals(0) && applicationUser.getVoteArea().equals(4)) {
                        return true;
                    }
                    return false;
                })
                .collect(Collectors.toList());
        this.calAndSort(fourthList);
        return new ResultUtils(100, "成功查询投票结果", Result.builder()
                .managerList(managerList)
                .firstList(firstList)
                .secondList(secondList)
                .thirdList(thirdList)
                .fourthList(fourthList)
                .build());
    }

    /**
     * 对某个分区的得票进行统计和排序
     *
     * @param managerList
     */
    private void calAndSort(List<ApplicationUser> managerList) {
        // 选举人数
        Integer cardinalNumber = managerList.get(0).getCardinalNumber();
        // 选出人数
        Integer electNumber = managerList.get(0).getElectNumber();
        // 排序
        managerList.sort(new VoteComparator());

        // 统计
        Set<Integer> voteNumberSet = managerList.stream().map(ApplicationUser::getVoteNumber).collect(Collectors.toSet());
        List<Integer> sortVoteNumber = new ArrayList<>(voteNumberSet);
        Collections.sort(sortVoteNumber, Collections.reverseOrder());
        Map<Integer, List<ApplicationUser>> map = new HashMap<>();
        for (Integer voteNumber : sortVoteNumber) {
            List<ApplicationUser> applicationUserList = managerList.stream()
                    .filter(applicationUser -> {
                        if (applicationUser.getVoteNumber().equals(voteNumber)) {
                            return true;
                        }
                        return false;
                    })
                    .collect(Collectors.toList());
            map.put(voteNumber, applicationUserList);
        }

        List<String> pitchStaffCode = new ArrayList<>();
        for (Integer voteNumber : sortVoteNumber) {
            List<ApplicationUser> applicationUserList = map.get(voteNumber);
            log.info("key -> {} , value -> {}", voteNumber, applicationUserList);
            List<String> staffCodeList = applicationUserList.stream()
                    .map(ApplicationUser::getStaffCode)
                    .collect(Collectors.toList());
            pitchStaffCode.addAll(staffCodeList);
            // 选出人数大于等于设定名额跳出循环
            if (pitchStaffCode.size() >= electNumber) {
                break;
            }
        }

        // 设置被选中人员
        for (String staffCode : pitchStaffCode) {
            for (ApplicationUser applicationUser : managerList) {
                if (staffCode.equals(applicationUser.getStaffCode())) {
                    applicationUser.setIsPitch(1);
                    applicationUserDao.update(applicationUser);
                }
            }
        }

    }

    /**
     * 计算得票率
     *
     * @param applicationUser
     * @return
     */
    private String calculation(ApplicationUser applicationUser) {
        // 选举人数
        Integer cardinalNumber = applicationUser.getCardinalNumber();
        log.info("cardinalNumber -> {}", cardinalNumber);
        // 选出人数
        Integer electNumber = applicationUser.getElectNumber();
        Integer vote = applicationUser.getVoteNumber();
        Integer voteArea = applicationUser.getVoteArea();
        if (!applicationUser.getIsManager().equals(1)) {
            List<ApplicationUser> applicationUserList = applicationUserDao.queryAll(ApplicationUser.builder()
                    .isManager(1)
                    .voteArea(voteArea)
                    .status(1)
                    .build());
            Integer number = applicationUserList.size();
            cardinalNumber += number;
            log.info("加上管理层人数cardinalNumber -> {}", cardinalNumber);
        }

        double one = Double.parseDouble(vote + "");
        double two = Double.parseDouble(cardinalNumber + "");
        double percent = one / two;
        //获取格式化对象
        NumberFormat nt = NumberFormat.getPercentInstance();
        //设置百分数精确度2即保留两位小数
        nt.setMinimumFractionDigits(2);
        //最后格式化并输出
        log.info("百分数：{}", nt.format(percent));
        return nt.format(percent);
    }

//    @PostMapping("/delete")
//    public ResultUtils delete() {
//        log.info("——————————clear start at {} ——————————", new Date());
//        String pre = "vote*";
//        Set<String> keys = stringRedisTemplate.keys(pre);
//        if (!CollectionUtils.isEmpty(keys)) {
//            stringRedisTemplate.delete(keys);
//        }
//        applicationUserDao.updateCache();
//        log.info("——————————clear end at {} ——————————", new Date());
//        return new ResultUtils(101, "成功删除投票结果");
//    }
//
//    @PostMapping("/testVoteManager")
//    public ResultUtils testVoteManager() {
//        List<ApplicationUser> applicationUserList = applicationUserDao.queryAll(ApplicationUser.builder()
//                .isManager(1)
//                .status(1)
//                .build());
//        for (int i = 0; i < 148; i++) {
//            Collections.shuffle(applicationUserList);
//            List<ApplicationUser> testApplicationUserList = applicationUserList.subList(0, 13);
//            this.incr(VotePackage.builder().applicationUserList(testApplicationUserList).build());
//        }
//        return new ResultUtils(101, "测试成功");
//    }
//
//    @PostMapping("/testVote/{partition}")
//    public ResultUtils testVote(@PathVariable Integer partition) {
//        List<ApplicationUser> applicationUserList = applicationUserDao.queryAll(ApplicationUser.builder()
//                .voteArea(partition)
//                .status(1)
//                .isManager(0)
//                .build());
//        List<ApplicationUser> managerApplicationUserList = applicationUserDao.queryAll(ApplicationUser.builder()
//                .isManager(1)
//                .voteArea(partition)
//                .status(1)
//                .build());
//        Integer number = managerApplicationUserList.size();
//        Integer voteNumber = applicationUserList.size() + number;
//        Integer voteTimes = applicationUserList.size() / 2;
//        switch (partition) {
//            case 1:
//                voteTimes = 14;
//                break;
//            case 2:
//                voteTimes = 12;
//                break;
//            case 3:
//                voteTimes = 24;
//                break;
//            case 4:
//                voteTimes = 14;
//                break;
//        }
//        log.info("{}分区投票次数{}", partition, voteTimes);
//        log.info("{}分区投票人数{}", partition, voteNumber);
//
//        for (int i = 0; i < voteNumber; i++) {
//            Collections.shuffle(applicationUserList);
//            List<ApplicationUser> testApplicationUserList = applicationUserList.subList(0, voteTimes);
//            this.incr(VotePackage.builder().applicationUserList(testApplicationUserList).build());
//        }
//        return new ResultUtils(101, "测试成功");
//    }
}
