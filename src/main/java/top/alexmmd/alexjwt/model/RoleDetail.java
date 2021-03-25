package top.alexmmd.alexjwt.model;

import lombok.*;

/**
 * @author 汪永晖
 * @creatTime 2021/3/25 10:19
 */
@Data
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class RoleDetail {

    private Integer roleId;
    private Integer privilegeId;
    private String roleName;
    private String privilegeName;
    private Integer userId;

    public RoleDetail(Integer roleId, String roleName) {
        this.roleId = roleId;
        this.roleName = roleName;
    }

    public RoleDetail(String roleName) {
        this.roleName = roleName;
    }
}
