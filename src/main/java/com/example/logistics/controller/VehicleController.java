package com.example.logistics.controller;

import cn.hutool.crypto.digest.BCrypt;
import com.example.logistics.annotation.Permission;
import com.example.logistics.model.dto.PageDTO;
import com.example.logistics.model.dto.UserDTO;
import com.example.logistics.model.dto.VehicleDTO;
import com.example.logistics.model.entity.Employee;
import com.example.logistics.model.entity.Vehicle;
import com.example.logistics.model.enums.Role;
import com.example.logistics.model.enums.VehicleStatus;
import com.example.logistics.model.query.VehicleQuery;
import com.example.logistics.model.support.BaseResponse;
import com.example.logistics.service.VehicleService;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.util.Assert;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

@RestController
@RequestMapping("/api/vehicle")
@RequiredArgsConstructor
public class VehicleController {

    private final VehicleService vehicleService;

    @ApiOperation(value = "查询车辆", notes = "仅管理员可调用")
    @Permission(allowRoles = Role.ADMIN)
    @GetMapping
    public BaseResponse<PageDTO<VehicleDTO>> listVehicle(VehicleQuery query,
                                                         @PageableDefault(sort = {"createTime"},
                                               direction = Sort.Direction.DESC) Pageable pageable){
        Page<Vehicle> vehicles = vehicleService.queryBy(query, pageable);
        return BaseResponse.ok("ok", new PageDTO<>(vehicles.map(vehicleService::toDto)));
    }

    @ApiOperation(value = "添加车辆", notes = "仅管理员可调用")
    @Permission(allowRoles = Role.ADMIN)
    @PostMapping
    public BaseResponse<Long> addVehicle(@RequestBody @Validated Vehicle vehicle){
        return BaseResponse.ok("新增车辆成功", vehicleService.create(vehicle).getId());
    }

    @ApiOperation(value = "更新车辆", notes = "仅管理员可调用")
    @Permission(allowRoles = Role.ADMIN)
    @PutMapping
    public BaseResponse<?> updateVehicle(@RequestBody @Validated Vehicle vehicle){
        vehicleService.update(vehicle);
        return BaseResponse.ok("更新车辆信息成功");
    }

    @ApiOperation(value = "删除车辆", notes = "仅管理员可调用")
    @Permission(allowRoles = Role.ADMIN)
    @DeleteMapping
    public BaseResponse<?> deleteVehicle(@RequestBody Long id){
        Vehicle vehicle = vehicleService.getNotNullById(id);
        Assert.isTrue(vehicle.getStatus().equals(VehicleStatus.IDLE), "只能删除闲置的车辆信息");
        vehicleService.deleteById(id);
        return BaseResponse.ok();
    }
}
