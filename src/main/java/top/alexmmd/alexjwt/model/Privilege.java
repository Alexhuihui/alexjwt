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
public class Privilege {

    private Long id;

    private String name;

    public Privilege(String name) {
        this.name = name;
    }
}
