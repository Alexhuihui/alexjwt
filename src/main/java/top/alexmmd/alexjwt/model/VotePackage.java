package top.alexmmd.alexjwt.model;

import lombok.*;

import java.util.List;

/**
 * @author 汪永晖
 */
@Data
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class VotePackage {

    private List<ApplicationUser> applicationUserList;
}
