package top.alexmmd.alexjwt.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.List;

/**
 * 投票用户信息
(UserInfo)实体类
 *
 * @author makejava
 * @since 2020-08-28 16:40:12
 */
@Data
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class ApplicationUser implements Serializable {
    private static final long serialVersionUID = 957996059096145425L;
    /**
    * 用戶编号id
    */
    private long id;
    /**
    * 用戶工号
    */
    private String staffCode;
    /**
    * 用户姓名
    */
    @NotBlank(message = "用户名不能为空")
    private String username;
    /**
    * 用户密码
    */
//    @JsonIgnore
    private String password;
    /**
    * 用戶邮箱地址
    */
    private String emailAddress;
    /**
    * 选举分区：0无区域，即管理员；1、2、3、4分别代表四个区域，默认为0
    */
    private Integer voteArea;
    /**
    * 选举人数
    */
    private Integer cardinalNumber;
    /**
    * 选出人数
    */
    private Integer electNumber;
    /**
    * 得票数，默认为0
    */
    private Integer voteNumber;
    /**
    * 是否管理职，1是；0否，默认为0
    */
    private Integer isManager;
    /**
    * 是否投票：1是；0否，默认为0
    */
    private Integer isVote;
    /**
    * 得票率，默认为0
    */
    private String percentage;
    /**
    * 用戶狀態值：0無效/1有效，默认1
    */
    private Integer status;

    private Integer isPitch;

    private List<RoleDetail> roleDetailList;

}