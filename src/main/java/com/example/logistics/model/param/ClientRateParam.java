package com.example.logistics.model.param;

import lombok.Data;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotNull;

@Data
public class ClientRateParam {

    @NotNull
    Long orderId;

    @NotNull(message = "评价不能为空")
    @Range(min = 1, max = 5, message = "评分在1到5之间")
    Integer score;

    String comment;
}
