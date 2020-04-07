package top.alexmmd.alexjwt.model;


import lombok.*;

import java.util.Collection;

/**
 * @author 汪永晖
 */
@Data
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Role {

    private Long id;

    private String name;

    public Role(String name) {
        this.name = name;
    }
}
