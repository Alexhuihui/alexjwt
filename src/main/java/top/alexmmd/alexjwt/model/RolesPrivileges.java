package top.alexmmd.alexjwt.model;

import lombok.*;

import java.io.Serializable;

/**
 * (RolesPrivileges)实体类
 *
 * @author makejava
 * @since 2020-04-07 16:16:01
 */
@Data
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class RolesPrivileges implements Serializable {
    private static final long serialVersionUID = 992063769139519155L;
    
    private Integer rolesPrivilegesId;
    
    private Long roleId;
    
    private Long privilegeId;

}