package top.alexmmd.alexjwt.model;

import lombok.*;

import javax.validation.constraints.NotBlank;

/**
 * @author 汪永晖
 */
@Data
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class ApplicationUser {

    private long id;

    @NotBlank(message = "用户名不能为空")
    private String username;

    @NotBlank(message = "密码不能为空")
    private String password;
}
