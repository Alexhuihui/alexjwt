package top.alexmmd.alexjwt.model;

import lombok.*;

/**
 * @author 汪永晖
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
}
