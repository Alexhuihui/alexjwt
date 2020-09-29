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
public class Result {

    List<ApplicationUser> managerList;

    List<ApplicationUser> firstList;

    List<ApplicationUser> secondList;

    List<ApplicationUser> thirdList;

    List<ApplicationUser> fourthList;
}
