package com.cht.spring.security.mapper;

import com.cht.spring.security.model.Role;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoleMapper {

    void batchInsetRole(@Param("roles") List<Role> list);
}
