package com.example.logistics.model.param;

import com.example.logistics.model.enums.OrderStatus;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.persistence.Column;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;

@Data
public class CreateOrderParam {

    private Long id;

    @NotNull(message = "物品名称不能为空")
    @Length(max = 127, message = "物品名称长度需小于127个字符")
    private String itemName;

    @NotNull(message = "物品重量不能为空")
    @Positive(message = "物品重量只能是正数")
    private Double itemWeight;

    @Positive(message = "物品体积只能是正数")
    private Double itemVolume;

    @NotNull(message = "物品数量不能为空")
    @Positive(message = "物品数量只能是正数")
    private Integer itemCount;

    @NotBlank(message = "发货人地址不能为空")
    @Length(max = 1023, message = "发货地址需小于1023个字符之间")
    private String sendAddress;

    @NotBlank(message = "发货人姓名不能为空")
    @Length(max = 16, message = "发货人姓名需小于16个字符之间")
    private String senderName;

    @NotBlank(message = "收货人地址不能为空")
    @Length(max = 1023, message = "收货地址需小于1023个字符之间")
    private String receiveAddress;

    @NotBlank(message = "收货人手机号不能为空")
    @Pattern(regexp = "^[1]([3-9])[0-9]{9}$", message = "收货人手机号不合法")
    private String receiverPhone;

    @NotBlank(message = "收货人姓名不能为空")
    @Length(max = 16, message = "收货人姓名需小于16个字符之间")
    private String receiverName;

    @Length(max = 1023, message = "备注不能超过1023个字符")
    private String comment;
}
