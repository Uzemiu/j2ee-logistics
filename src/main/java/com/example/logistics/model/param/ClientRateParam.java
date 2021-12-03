package com.example.logistics.model.param;

import lombok.Data;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;

import javax.persistence.Column;
import javax.validation.constraints.NotNull;

@Data
public class ClientRateParam {

    @NotNull
    Long orderId;

    @Range(min = 1, max = 5, message = "货物满意度分数只能在1到5之间")
    @NotNull(message = "货物评分不能为空")
    private Integer itemScore;

    @Range(min = 1, max = 5, message = "服务满意度分数只能在1到5之间")
    @NotNull(message = "服务满意度评分不能为空")
    private Integer serviceScore;

    @Range(min = 1, max = 5, message = "物流满意度分数只能在1到5之间")
    @NotNull(message = "物流满意度评分不能为空")
    private Integer logisticsScore;

    @Length(max = 1023, message = "改进建议不能超过1023个字符")
    private String advice;

    @Length(max = 1023, message = "物流问题反馈不能超过1023个字符")
    private String issue;
}
