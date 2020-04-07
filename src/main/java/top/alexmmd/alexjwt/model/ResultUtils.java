package top.alexmmd.alexjwt.model;

import lombok.*;

/**
 * 后端返回对象
 *
 * @author 汪永晖
 */
@Data
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class ResultUtils {

    private int code; //返回的状态码
    private String message; //返回的信息详情
    private Object data; //返回的对象数据

    public ResultUtils(int code, String message) {
        this.code = code;
        this.message = message;
    }
}
